package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Implementation of the {@link InscriptionsService} This class is used to manage the inscriptions
 * of the students
 */
@Service
public class InscriptionsServiceImpl implements InscriptionsService {

  private final PersonRepository personRepository;
  private final StorageService storageService;
  private final EnrollmentRepository enrollmentRepository;
  private final DocumentRepository documentRepository;
  private final StudentRepository studentRepository;

  @Value("${storage.location}")
  private String rootLocation;


  @Autowired
  public InscriptionsServiceImpl(PersonRepository personRepository, StorageService storageService,
      EnrollmentRepository enrollmentRepository, DocumentRepository documentRepository,
      StudentRepository studentRepository) {
    this.personRepository = personRepository;
    this.storageService = storageService;
    this.enrollmentRepository = enrollmentRepository;
    this.documentRepository = documentRepository;
    this.studentRepository = studentRepository;
  }

  /**
   * Get all inscriptions
   *
   * @param pageable the pageable object
   * @return a list of all inscriptions
   */
  @Override
  public List<EnrollmentDto> getAllInscriptions(Pageable pageable) {
    // Find all enrollments
    Page<EnrollmentEntity> students = enrollmentRepository.findAll(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "student.studentPerson.idNumber"))));

    // Convert enrollments to DTOs
    return EnrollmentMapper.convertToDtoList(students.getContent());
  }

  /**
   * Get an inscription by value
   *
   * @param value of the idNumber, the name of the student or first surname or previous school
   * @return a list of inscriptions that match the value
   */
  @Override
  public List<EnrollmentDto> findStudentByValue(String value) {
    List<EnrollmentEntity> enrollments = new ArrayList<>();
    // Validate if the value is a number
    if (value.matches("\\d+")) {
      List<StudentEntity> student =
          studentRepository.findStudentByStudentPerson_IdNumberContaining(value);
      if(!student.isEmpty()) {
        enrollments = enrollmentRepository.findAllByStudentIn(student);
      }
    }

    if (enrollments.isEmpty()) {
      // Find persons by first name, first surname or second surname
      List<PersonEntity> findings =
          personRepository.findByFirstNameContainingOrFirstSurnameContainingOrSecondSurnameContaining(
              value, value, value);

      // Find students by persons
      List<StudentEntity> students = studentRepository.findAllByStudentPersonIn(findings);

      // Find enrollments by students
      enrollments = enrollmentRepository.findAllByStudentIn(students);
    }

    return EnrollmentMapper.convertToDtoList(enrollments);
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

  private void verifyEnrollmentInput(EnrollmentDto inscription) {
    // Verify if the student is already enrolled
    // Get the student
    StudentEntity student = studentRepository.findStudentEntityByStudentPerson_IdNumber(
        inscription.student().person().idNumber()).orElse(null);

    if (student != null) {
      List<EnrollmentEntity> studentEnrollments = enrollmentRepository.findAllByStudent(student);
      if (studentEnrollments.stream()
          .anyMatch(e -> e.getExamDate().equals(inscription.examDate()))) {
        throw new EnrollmentException("El estudiante ya está inscrito para la fecha seleccionada");
      }
    }

    // TODO: Verify examDate is between any period

  }

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
    enrollmentEntity.setStudent(student);

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
        enrollmentUpdate.processStatus());
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
    enrollmentRepository.usp_update_enrollment_and_log(Long.parseLong(id),
        enrollmentUpdate.processStatus().toString(), Date.valueOf(enrollmentUpdate.examDate()),
        enrollmentUpdate.whatsappPermission(), enrollmentUpdate.comment(),
        enrollmentUpdate.changedBy());
  }

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
  public DocumentDto saveDocument(String documentType, Long enrollmentId, MultipartFile file) {
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
              .getIdNumber() + "_" + timestamp + ".pdf", DocType.fromString(documentType));
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
      if (d.getDocType() == document.getDocType() && !d.equals(document)) {
        storageService.deleteDocumentByUrl(d.getDocumentUrl());
        enrollment.removeDocument(d);
        documentRepository.delete(d);
      }
    }

    return DocumentMapper.convertToDto(document);
  }

  private void verifyPutParameters(String id) {
    // Validate if the id is a number
    if (!id.matches("\\d+")) {
      throw new EnrollmentException("El id no es un número válido");
    }
  }

  @Override
  public List<EnrollmentDto> findEnrollmentsByStudentId(String idNumber) {
    Optional<StudentEntity> student =
        studentRepository.findStudentEntityByStudentPerson_IdNumber(idNumber);
    if (student.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No hay un estudiante con el id " + idNumber);
    }
    List<EnrollmentEntity> enrollments = enrollmentRepository.findAllByStudent(student.get());

    return EnrollmentMapper.convertToDtoList(enrollments);
  }
}
