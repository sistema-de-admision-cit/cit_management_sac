package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
  /*@Override
  public List<EnrollmentDto> getAllInscriptions(Pageable pageable) {
    // Find all students
    Page<StudentEntity> students = studentRepository.findAll(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "idNumber"))));

    // Convert students to DTOs
    return StudentMapper.convertToDtoList(students.getContent());
  }*/

  /**
   * Get an inscription by id
   *
   * @param id the id of the student
   * @return the inscription with the given id
   */
  /*@Override
  public StudentDto findStudentById(Long id) {
    // Find student by id
    Optional<StudentEntity> student = studentRepository.findStudentById(id);

    // Convert student to DTO or null if not present
    return student.map(StudentMapper::convertToDto).orElse(null);
  }*/

  /**
   * Get an inscription by value
   *
   * @param value of the idNumber, the name of the student or first surname or previous school
   * @return a list of inscriptions that match the value
   */
  /*@Override
  public List<StudentDto> findStudentByValue(String value) {
    // Validate if the value is a number
    if (value.matches("\\d+")) {
      List<StudentEntity> student = studentRepository.findStudentByIdNumberContaining(value);
      return StudentMapper.convertToDtoList(student);
    }

    // Find students by first name, first surname or second surname
    List<StudentEntity> students =
        studentRepository.findByFirstNameContainingOrFirstSurnameContainingOrSecondSurnameContaining(
            value, value, value);

    return StudentMapper.convertToDtoList(students);
  }*/

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
    CompletableFuture<DocumentDto> gradesDocument = CompletableFuture.completedFuture(null);
    CompletableFuture<DocumentDto> letterDocument = CompletableFuture.completedFuture(null);

    //Save inscription
    if (grades != null && !grades.isEmpty()) {
      gradesDocument = storageService.store(grades,
          "grades_" + inscription.student().person().idNumber() + ".pdf", DocType.OT);
    }
    if (letter != null && !letter.isEmpty()) {
      letterDocument = storageService.store(letter,
          "letter_" + inscription.student().person().idNumber() + ".pdf", DocType.AC);
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
      personRepository.save(studentPerson);
    }

    //
    //Verify parent-student relation
    StudentEntity finalStudent = student;
    if (parent.getStudents().stream().noneMatch(s -> s.getStudent().equals(finalStudent))) {
      parent.addStudent(student);
      studentRepository.save(student);
    }

    // Verify if the student is already enrolled
    List<EnrollmentEntity> studentEnrollments = enrollmentRepository.getAllByStudent(student);
    if (studentEnrollments.stream().anyMatch(e -> e.getExamDate().equals(inscription.examDate()))) {
      //Falta eliminar los documentos de la inscripción
      for (DocumentDto d : documents) {
        storageService.deleteDocument(d.documentName());
      }
      throw new EnrollmentException("El estudiante ya está inscrito para la fecha seleccionada");
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
    for (DocumentDto d : documents) {
      DocumentEntity document = DocumentMapper.convertToEntity(d);
      document.setDocumentUrl(rootLocation + d.documentName());

      // Save the document
      enrollmentEntity.addDocument(document);
      documentRepository.save(document);
    }

    // Return
    return EnrollmentMapper.convertToDto(enrollmentEntity);
  }

  /**
   * Update the exam date of the student
   *
   * @param id   the id of the enrollment
   * @param date the new exam date
   * @return the updated student
   */
  /*@Override
  public StudentDto updateExamDate(String id, String date) {
    // Verify put parameters
    EnrollmentEntity enrollmentEntity = verifyPutParameters(id);

    // Update the exam date
    enrollmentEntity.setExamDate(LocalDate.parse(date));

    // Save the enrollment
    enrollmentEntity = enrollmentRepository.save(enrollmentEntity);

    // Return
    return StudentMapper.convertToDto(enrollmentEntity.getStudent());
  }*/

  /**
   * Change the status of the enrollment
   *
   * @param id
   * @param status
   * @return true if the status was updated
   */
  /*@Override
  public boolean changeStatus(Long id, ProcessStatus status) {
    try {
      this.enrollmentRepository.updateStatusById(id, status);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error al actualizar el estado de la inscripción");
    }

    return true;
  }*/

  /**
   * Change the whatsapp notification permission
   *
   * @param id         the id of the student
   * @param permission the new permission
   * @return true if the permission was updated
   */
  /*@Override
  public boolean changeWhatsappPermission(Long id, Boolean permission) {
    try {
      this.enrollmentRepository.updateWhatsappNotificationById(id, permission);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error al actualizar la notificación de whatsapp");
    }

    return true;
  }*/


  /**
   * Update the enrollment
   *
   * @param enrollmentId       the id of the enrollment
   * @param status             the new status
   * @param examDate           the new exam date
   * @param whatsappPermission the new whatsapp permission
   * @param comment            the comment
   * @param changedBy          the user that changed the enrollment
   * @return true if the enrollment was updated
   */
  /*@Override
  public Boolean updateEnrollment(Long enrollmentId, ProcessStatus status, String examDate,
      String whatsappPermission, String comment, Integer changedBy) {
    try {
      // verify if the enrollment exists
      if (!existsEnrollment(enrollmentId)) {
        System.out.println("No hay una inscripción con el id " + enrollmentId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
            "No hay una inscripción con el id " + enrollmentId);
      }

      // Update the enrollment
      enrollmentRepository.usp_update_enrollment_and_log(enrollmentId, status.name(),
          Date.valueOf(examDate), Boolean.parseBoolean(whatsappPermission), comment, changedBy);

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error al actualizar la inscripción");
    }
  }*/

  /*@Override
  public boolean deleteDocument(Long documentId) {

    // If the document is not present, return Not Found
    if (!documentRepository.existsById(documentId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No hay un documento con el id " + documentId);
    }

    // Delete the document
    documentRepository.deleteById(documentId);

    return true;
  }*/


  /**
   * Save a document
   *
   * @param documentName the name of the document
   * @param documentType the type of the document
   * @param enrollmentId the id of the enrollment to save the document
   * @return the saved document
   */
  /*@Override
  public DocumentDto saveDocument(String documentName, String documentType, Long enrollmentId) {
    EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId).get();


    // Create the document
    DocumentEntity document = DocumentEntity.builder().documentName(documentName)
        .documentType(DocType.valueOf(documentType)).documentUrl(documentName)
        .enrollment(enrollment).build();

    // Save the document
    return DocumentMapper.convertToDto(documentRepository.save(document));
  }*/

  /*private boolean existsEnrollment(Long enrollmentId) {
    return enrollmentRepository.existsById(enrollmentId);
  }*/

  /*private EnrollmentEntity verifyPutParameters(String id) {
    // Validate if the id is a number
    if (!id.matches("\\d+")) {
      throw new EnrollmentException("El id no es un número válido");
    }

    // Find enrollment by id
    Optional<EnrollmentEntity> enrollment = enrollmentRepository.findById(Long.parseLong(id));

    // If the enrollment is not present, return null
    if (enrollment.isEmpty()) {
      // Return Not Found
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No hay una inscripción con el id " + id);
    }

    // Get the enrollment
    return enrollment.get();
  }*/

  /**
   * Create the relation between the student and the parent/guardian
   *
   * @param student the student
   * @param parent  the parent/guardian
   */
  /*private void createParentGuardianStudentRelation(StudentEntity student,
      ParentsGuardianEntity parent) {
    // Create ParentGuardianStudentEntity
    ParentGuardianStudentEntity parentGuardianStudentEntity =
        new ParentGuardianStudentEntity(student, parent);

    // Add the relation to the student
    student.addParentGuardian(parentGuardianStudentEntity);

    // Add the relation to the parent/guardian
    parent.addStudent(parentGuardianStudentEntity);
  }*/
  @Override
  public List<EnrollmentDto> getAllInscriptions(Pageable pageable) {
    return List.of();
  }

  @Override
  public StudentDto findStudentById(Long id) {
    return null;
  }

  @Override
  public List<StudentDto> findStudentByValue(String value) {
    return List.of();
  }

  @Override
  public StudentDto updateExamDate(String id, String date) {
    return null;
  }

  @Override
  public boolean changeStatus(Long id, ProcessStatus status) {
    return false;
  }

  @Override
  public boolean changeWhatsappPermission(Long id, Boolean permission) {
    return false;
  }

  @Override
  public Boolean updateEnrollment(Long enrollmentId, ProcessStatus status, String examDate,
      String whatsappPermission, String comment, Integer changedBy) {
    return null;
  }

  @Override
  public boolean deleteDocument(Long documentId) {
    return false;
  }

  @Override
  public DocumentDto saveDocument(String documentName, String documentType, Long enrollmentId) {
    return null;
  }
}
