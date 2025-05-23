package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.ExamPeriodRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of {@link InscriptionsService} for managing student enrollments and related operations.
 * This service handles enrollment creation, updates, document management, and status tracking.
 */
@Service
public class InscriptionsServiceImpl implements InscriptionsService {

  private final PersonRepository personRepository;
  private final StorageService storageService;
  private final EnrollmentRepository enrollmentRepository;
  private final DocumentRepository documentRepository;
  private final StudentRepository studentRepository;
  private final ExamPeriodRepository examPeriodRepository;

  @Value("${storage.inscriptions.documents.location}")
  private String rootLocation;
  /**
   * Constructs a new InscriptionsServiceImpl with required repositories and services.
   *
   * @param personRepository repository for person entities
   * @param storageService service for file storage operations
   * @param enrollmentRepository repository for enrollment entities
   * @param documentRepository repository for document entities
   * @param studentRepository repository for student entities
   * @param examPeriodRepository repository for exam period entities
   */
  @Autowired
  public InscriptionsServiceImpl(PersonRepository personRepository, StorageService storageService,
      EnrollmentRepository enrollmentRepository, DocumentRepository documentRepository,
      StudentRepository studentRepository, ExamPeriodRepository examPeriodRepository) {
    this.personRepository = personRepository;
    this.storageService = storageService;
    this.enrollmentRepository = enrollmentRepository;
    this.documentRepository = documentRepository;
    this.studentRepository = studentRepository;
    this.examPeriodRepository = examPeriodRepository;
  }

  /**
   * Get all inscriptions
   *
   * @param pageable the pageable object
   * @return a list of all inscriptions
   */
  @Override
  public List<StudentDto> getAllInscriptions(Pageable pageable) {
    // Find all enrollments
    Page<StudentEntity> enrollments = studentRepository.findAllWithEnrollmentsInProcess(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "studentPerson.idNumber"))));

    // Convert enrollments to DTOs
    return StudentMapper.convertToDtoList(enrollments.getContent());
  }

  /**
   * Get an inscription by value
   *
   * @param value    of the idNumber, the name of the student or first surname or previous school
   * @param pageable  the pageable object
   * @return a list of inscriptions that match the value
   */
  @Override
  public List<StudentDto> findStudentByValue(String value, Pageable pageable) {
    List<StudentEntity> students = new ArrayList<>();
    // Validate if the value is a number
    if (value.matches("\\d+")) {
      students =
          studentRepository.findStudentByLikeIdNumberWithEnrollmentInProcess(value, pageable);
    }

    if (students.isEmpty()) {
      // Find students by value
      students = studentRepository.findAllByValueWithEnrollmentInProcess(value, pageable);
    }

    return StudentMapper.convertToDtoList(students);
  }

  /**
   * Add an inscription
   *
   * @param inscription the inscription to add
   * @param grades      the grades document
   * @param letter      the letter document
   * @return the added inscription
   * @throws EnrollmentException if the student is already enrolled for the selected date
   */
  @Override
  //@Transactional(rollbackFor = {EnrollmentException.class, NoSuchElementException.class})
  public EnrollmentDto addInscription(EnrollmentDto inscription, MultipartFile grades,
      MultipartFile letter) {
    verifyEnrollmentInput(inscription);

    CompletableFuture<DocumentDto> gradesDocument = CompletableFuture.completedFuture(null);
    CompletableFuture<DocumentDto> letterDocument = CompletableFuture.completedFuture(null);

    long timestamp = System.currentTimeMillis();

    //Save inscription
    if (grades != null && !grades.isEmpty()) {
      gradesDocument = storageService.store(grades,
          "grades_" + inscription.student().person().idNumber() + "_" + timestamp + ".pdf",
          DocType.OT);
    }
    if (letter != null && !letter.isEmpty()) {
      letterDocument = storageService.store(letter,
          "letter_" + inscription.student().person().idNumber() + "_" + timestamp + ".pdf",
          DocType.OT);
    }

    //Wait for the documents to be saved
    CompletableFuture.allOf(gradesDocument, letterDocument).join();

    //Add the documents to a list
    List<DocumentDto> documents = new ArrayList<>();
    try {
      DocumentDto gradesFutureDoc = gradesDocument.get();
      DocumentDto letterFutureDoc = letterDocument.get();
      if (gradesFutureDoc != null) {
        documents.add(gradesFutureDoc);
      }
      if (letterFutureDoc != null) {
        documents.add(letterFutureDoc);
      }
    } catch (InterruptedException | ExecutionException e) {
      throw new EnrollmentException("Error while saving the documents");
    }

    //Save inscription
    return createInscription(inscription, documents);
  }
  /**
   * Verifies enrollment input parameters including student existence and exam date validity.
   *
   * @param inscription the enrollment data to verify
   * @throws EnrollmentException if validation fails
   */
  private void verifyEnrollmentInput(EnrollmentDto inscription) {
    // Verify if the student is already enrolled
    // Get the student
    StudentEntity student = studentRepository.findStudentEntityByStudentPerson_IdNumber(
        inscription.student().person().idNumber()).orElse(null);

    if (student != null) {
      List<EnrollmentEntity> studentEnrollments =
          enrollmentRepository.findAllByStudentPerson_IdNumber_ThatAreInProcess(student);
      if (studentEnrollments.stream()
          .anyMatch(e -> e.getExamDate().equals(inscription.examDate()))) {
        throw new EnrollmentException("El estudiante ya está inscrito para la fecha seleccionada");
      }
    }

    List<ExamPeriodEntity> examPeriods = examPeriodRepository.findByDateBetween(inscription.examDate());
    if (examPeriods.isEmpty()) {
      throw new EnrollmentException("No hay un periodo de exámenes para la fecha seleccionada");
    }
  }
  /**
   * Creates a new enrollment with associated documents.
   *
   * @param inscription the enrollment data
   * @param documents list of associated documents
   * @return the created enrollment DTO
   */
  private EnrollmentDto createInscription(EnrollmentDto inscription, List<DocumentDto> documents) {
    //Validate if the student's parent already exists
    ParentDto inscriptionParent = inscription.student().parents().getFirst();
    Optional<PersonEntity> parentPersonOptional =
        personRepository.findByIdNumber(inscriptionParent.person().idNumber());

    //Verify if the parent exists
    ParentEntity parent = parentPersonOptional.map(PersonEntity::getParent).orElse(null);
    if (parentPersonOptional.isEmpty()) {
      //Create the parent
      PersonEntity parentPerson = PersonMapper.convertToEntity(inscriptionParent.person());
      parent = ParentMapper.convertToEntity(inscriptionParent);
      parentPerson.addParent(parent);

      //Get the parent's address
      AddressEntity address =
          AddressMapper.convertToEntity(inscriptionParent.addresses().getFirst());

      //Save the parent's address
      parent.addAddress(address);

      //Save the parent
      personRepository.save(parentPerson);
    }

    // Validate if the student already exists
    StudentDto inscriptionStudent = inscription.student();
    Optional<PersonEntity> studentPersonOptional =
        personRepository.findByIdNumber(inscriptionStudent.person().idNumber());

    // Verify if the student exists
    StudentEntity student = studentPersonOptional.map(PersonEntity::getStudent).orElse(null);
    if (studentPersonOptional.isEmpty()) {
      // Create the student
      PersonEntity studentPerson = PersonMapper.convertToEntity(inscriptionStudent.person());
      student = StudentMapper.convertToEntity(inscriptionStudent);
      studentPerson.addStudent(student);

      // Set previousGrades to 0.00
      student.setPreviousGrades(BigDecimal.valueOf(0.00));

      //Add student to the parent
      parent.addStudent(student);



      // Save the student
      studentRepository.save(student);
    }

    //
    //Verify parent-student relation
    StudentEntity finalStudent = student;
    if (parent.getStudents().stream().noneMatch(s -> s.getStudent().equals(finalStudent))) {
      parent.addStudent(student);
      studentRepository.save(student);
    }

    // Get the Enrollment
    EnrollmentEntity enrollmentEntity = EnrollmentMapper.convertToEntity(inscription);

    // Set student to the enrollment
    student.addEnrollment(enrollmentEntity);

    // Set pending status to the enrollment
    enrollmentEntity.setStatus(ProcessStatus.PENDING);

    // Save the enrollment
    enrollmentEntity = enrollmentRepository.save(enrollmentEntity);

    // Add enrollment to each document
    for (DocumentDto documentDto : documents) {
      DocumentEntity document = DocumentMapper.convertToEntity(documentDto);
      setDocumentName(document, documentDto.documentType());
      document.setDocumentUrl(rootLocation + documentDto.documentUrlPostfix());

      // Save the document
      enrollmentEntity.addDocument(document);
      documentRepository.save(document);
    }

    // Return
    return EnrollmentMapper.convertToDto(enrollmentEntity);
  }

  /**
   * Set the document name
   *
   * @param document the document entity
   * @param docType  the document type
   */
  private void setDocumentName(DocumentEntity document, DocType docType) {
    String documentName;
    if (docType == DocType.OT) {
      documentName = "Documento de Notas";
    } else {
      documentName = "Documento de Adecuaciones";
    }
    document.setDocumentName(documentName);
  }

  /**
   * Update the exam date of the student
   *
   * @param id the id of the enrollment
   */
  @Override
  public void updateExamDate(String id, EnrollmentUpdateDto enrollmentUpdate) {
    // Verify put parameters
    verifyPutParameters(id);

    // Save the enrollment
    enrollmentRepository.updateEnrollmentExamDate(Long.parseLong(id), enrollmentUpdate.examDate());
  }

  /**
   * Change the status of the enrollment
   *
   * @param id the id of the enrollment
   */
  @Override
  public void updateProcessStatus(String id, EnrollmentUpdateDto enrollmentUpdate) {
    // Verify put parameters
    verifyPutParameters(id);

    // Save the enrollment
    enrollmentRepository.updateEnrollmentStatus(Long.parseLong(id),
        enrollmentUpdate.status());
  }

  /**
   * Change the whatsapp notification permission
   *
   * @param id               the id of the student
   * @param enrollmentUpdate the enrollment update
   */
  @Override
  public void updateWhatsappPermission(String id, EnrollmentUpdateDto enrollmentUpdate) {
    // Verify put parameters
    verifyPutParameters(id);

    // Save the enrollment
    enrollmentRepository.updateEnrollmentWhatsappPermission(Long.parseLong(id),
        enrollmentUpdate.whatsappPermission());
  }


  /**
   * Update the enrollment
   *
   * @param id the id of the enrollment
   */
  @Override
  public void updateEnrollment(String id, EnrollmentUpdateDto enrollmentUpdate) {
    // Verify put parameters
    verifyPutParameters(id);

    // Save the enrollment
    enrollmentRepository.usp_update_enrollment_and_log(
            Long.parseLong(id),
            enrollmentUpdate.status().toString(),
            Date.valueOf(enrollmentUpdate.examDate()),
            enrollmentUpdate.whatsappPermission(),
            enrollmentUpdate.previousGrades(),
            enrollmentUpdate.comment(),
            enrollmentUpdate.changedBy()
    );
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteDocument(Long documentId) {

    // If the document is not present, return Not Found
    DocumentEntity document = documentRepository.findById(documentId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Documento no encontrado con el id " + documentId));
    EnrollmentEntity enrollment = document.getEnrollment();

    //Delete actual file
    storageService.deleteDocumentByUrl(document.getDocumentUrl());

    // Delete the document
    enrollment.removeDocument(document);
    documentRepository.deleteById(documentId);
  }


  /**
   * Save a document
   *
   * @param documentType the type of the document
   * @param enrollmentId the id of the enrollment to save the document
   * @return the saved document
   */
  @Override
  public DocumentDto saveDocument(DocType documentType, Long enrollmentId, MultipartFile file) {
    EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);

    if (enrollment == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No hay una inscripción con el id " + enrollmentId);
    }

    CompletableFuture<DocumentDto> fileDocument = CompletableFuture.completedFuture(null);

    // Save the document
    long timestamp = System.currentTimeMillis();

    //Save inscription
    if (file != null && !file.isEmpty()) {
      fileDocument = storageService.store(file,
          "grades_" + enrollment.getStudent().getStudentPerson()
              .getIdNumber() + "_" + timestamp + ".pdf", documentType);
    }

    //Wait for the documents to be saved
    CompletableFuture.completedFuture(fileDocument).join();

    //Save the document in the database
    DocumentDto documentDto = fileDocument.join();
    DocumentEntity document = DocumentMapper.convertToEntity(documentDto);
    setDocumentName(document, documentDto.documentType());
    document.setDocumentUrl(rootLocation + documentDto.documentUrlPostfix());

    enrollment.addDocument(document);
    document = documentRepository.save(document);

    //Delete the last document with the same DocType
    List<DocumentEntity> documents = enrollment.getDocuments();
    for (DocumentEntity d : documents) {
      if (d.getDocumentType() == document.getDocumentType() && !d.equals(document)) {
        storageService.deleteDocumentByUrl(d.getDocumentUrl());
        enrollment.removeDocument(d);
        documentRepository.delete(d);
      }
    }

    return DocumentMapper.convertToDto(document);
  }
  /**
   * Verifies that the provided ID parameter is valid.
   *
   * @param id the ID to verify
   * @throws EnrollmentException if the ID is not a valid number
   */
  private void verifyPutParameters(String id) {
    // Validate if the id is a number
    if (!id.matches("\\d+")) {
      throw new EnrollmentException("El id no es un número válido");
    }
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<EnrollmentDto> findPendingEnrollmentsByStudentId(String idNumber) {
    StudentEntity student = studentRepository.findStudentEntityByStudentPerson_IdNumber(idNumber)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "No hay un estudiante con el id " + idNumber));

    List<EnrollmentEntity> enrollments =
        enrollmentRepository.findAllByStudentPerson_IdNumber_ThatAreInProcess(student);

    return EnrollmentMapper.convertToDtoList(enrollments);
  }

  @Override
  public Long getEnrollmentsCount() {
    // Get the count of enrollments
    return enrollmentRepository.countEnrollmentsInProcess();
  }

  @Override
  public Long getSearchCount(String value) {
    return studentRepository.countStudentsWithEnrollmentsInProcessByValue(value);
  }
}
