package cr.co.ctpcit.citsacbackend.logic.services.inscriptions.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.*;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageProperties;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementation of the {@link InscriptionsService} This class is used to manage the inscriptions
 * of the students
 */
@Service
public class InscriptionsServiceImplementation implements InscriptionsService {

  private final StudentRepository studentRepository;
  private final ParentsGuardianRepository parentsGuardianRepository;
  private final ParentGuardianStudentRepository parentGuardianStudentRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final DocumentRepository documentRepository;
  private final StorageService storageService;
  private final String rootLocation;


  @Autowired
  public InscriptionsServiceImplementation(StudentRepository studentRepository,
      ParentsGuardianRepository parentsGuardianRepository,
      ParentGuardianStudentRepository parentGuardianStudentRepository,
      EnrollmentRepository enrollmentRepository, DocumentRepository documentRepository,
      StorageProperties properties, StorageService storageService) {
    this.studentRepository = studentRepository;
    this.parentsGuardianRepository = parentsGuardianRepository;
    this.parentGuardianStudentRepository = parentGuardianStudentRepository;
    this.enrollmentRepository = enrollmentRepository;
    this.documentRepository = documentRepository;
    this.storageService = storageService;
    this.rootLocation = properties.getLocation();
  }

  /**
   * Get all inscriptions
   *
   * @param pageable the pageable object
   * @return a list of all inscriptions
   */
  @Override
  public List<StudentDto> getAllInscriptions(Pageable pageable) {
    // Find all students
    Page<StudentEntity> students = studentRepository.findAll(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "idNumber"))));

    // Convert students to DTOs
    return StudentMapper.convertToDtoList(students.getContent());
  }

  /**
   * Get an inscription by id
   *
   * @param id the id of the student
   * @return the inscription with the given id
   */
  @Override
  public StudentDto findStudentById(Long id) {
    // Find student by id
    Optional<StudentEntity> student = studentRepository.findStudentById(id);

    // Convert student to DTO or null if not present
    return student.map(StudentMapper::convertToDto).orElse(null);
  }

  /**
   * Get an inscription by value
   *
   * @param value of the idNumber, the name of the student or first surname or previous school
   * @return a list of inscriptions that match the value
   */
  @Override
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
  }

  /**
   * Add an inscription
   *
   * @param inscriptionDto the inscription to add
   * @param grades         the grades document
   * @param letter         the letter document
   * @return the added inscription
   * @throws EnrollmentException if the student is already enrolled for the selected date
   */
  @Override
  @Transactional(rollbackFor = {EnrollmentException.class, NoSuchElementException.class})
  public EnrollmentDto addInscription(StudentDto inscriptionDto, MultipartFile grades,
      MultipartFile letter) {

    //Save inscription
    EnrollmentDto enrollment = createInscription(inscriptionDto);
    String documentName = "notas_" + enrollment.id() + "_" + inscriptionDto.idNumber() + ".pdf";

    // Save the grades document related to the enrollment
    saveDocument(documentName, "OT", enrollment.id());

    //Save the letter document related to the enrollment
    if (letter != null) {
      documentName = "carta_" + enrollment.id() + "_" + inscriptionDto.idNumber() + ".pdf";
      saveDocument(documentName, "HC", enrollment.id());
      storageService.store(letter, documentName);
    }

    // Save the grades document
    storageService.store(grades, documentName);

    return enrollment;
  }

  private EnrollmentDto createInscription(StudentDto inscriptionDto) {
    // Validate if the student already exists
    Optional<StudentEntity> student =
        studentRepository.findStudentByIdNumber(inscriptionDto.idNumber());

    // Get the student from optional or create a new one
    StudentEntity studentEntity =
        student.orElseGet(() -> StudentMapper.convertToEntity(inscriptionDto));

    // Get the Enrollment
    EnrollmentEntity enrollmentEntity =
        EnrollmentMapper.convertToEntity(inscriptionDto.enrollments().getFirst());

    // Verify if the student is already enrolled
    if (student.isPresent()) {
      if (student.get().getEnrollments().stream().anyMatch(
          enrollment -> enrollment.getExamDate().equals(enrollmentEntity.getExamDate()))) {
        throw new EnrollmentException(
            "El estudiante ya tiene una inscripción para la fecha seleccionada. " + "Debe seleccionar otra fecha o comunicarse con el área de Servicio al Cliente.");
      }

    }

    // Set pending status to the enrollment
    enrollmentEntity.setStatus(ProcessStatus.P);

    // Save the enrollment
    studentEntity.addEnrollment(enrollmentEntity);

    // Verify if parent/guardian exists
    Optional<ParentsGuardianEntity> parentGuardian =
        parentsGuardianRepository.findParentsGuardianByIdNumber(
            inscriptionDto.parents().getFirst().idNumber());

    // Get the parent/guardian from optional or create a new one
    ParentsGuardianEntity parent = parentGuardian.orElseGet(
        () -> ParentGuardianMapper.convertToEntity(inscriptionDto.parents().getFirst()));

    // Get the address
    AddressEntity address =
        AddressMapper.convertToEntity(inscriptionDto.parents().getFirst().addresses().getFirst());

    // Save the address
    parent.addAddress(address);

    if (parentGuardian.isPresent()) {
      // Update the parent/guardian information
      parent.setEmail(inscriptionDto.parents().getFirst().email());
      parent.setPhoneNumber(inscriptionDto.parents().getFirst().phoneNumber());
    }

    // Save the parent/guardian
    parent = parentsGuardianRepository.save(parent);

    // If the student exists, add the enrollment and update/add parent/guardian information
    if (student.isEmpty()) {
      // Create the parent/guardian/student relation
      createParentGuardianStudentRelation(studentEntity, parent);
    } else {
      // Verify if the parent/guardian is already related to the student
      Optional<ParentGuardianStudentEntity> parentGuardianStudent =
          parentGuardianStudentRepository.findParentGuardianStudentEntityByStudentAndParentGuardian(
              studentEntity, parent);
      if (parentGuardianStudent.isEmpty()) {
        // Create the parent/guardian/student relation
        createParentGuardianStudentRelation(studentEntity, parent);
      }
    }
    // Save the student
    studentRepository.save(studentEntity);

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
  @Override
  public StudentDto updateExamDate(String id, String date) {
    // Verify put parameters
    EnrollmentEntity enrollmentEntity = verifyPutParameters(id);

    // Update the exam date
    enrollmentEntity.setExamDate(LocalDate.parse(date));

    // Save the enrollment
    enrollmentEntity = enrollmentRepository.save(enrollmentEntity);

    // Return
    return StudentMapper.convertToDto(enrollmentEntity.getStudent());
  }

  /**
   * Change the status of the enrollment
   *
   * @param id
   * @param status
   * @return true if the status was updated
   */
  @Override
  public boolean changeStatus(Long id, ProcessStatus status) {
    try {
      this.enrollmentRepository.updateStatusById(id, status);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error al actualizar el estado de la inscripción");
    }

    return true;
  }

  /**
   * Change the whatsapp notification permission
   *
   * @param id         the id of the student
   * @param permission the new permission
   * @return true if the permission was updated
   */
  @Override
  public boolean changeWhatsappPermission(Long id, Boolean permission) {
    try {
      this.enrollmentRepository.updateWhatsappNotificationById(id, permission);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Error al actualizar la notificación de whatsapp");
    }

    return true;
  }


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
  @Override
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
  }

  @Override
  public boolean deleteDocument(Long documentId) {

    // If the document is not present, return Not Found
    if (!documentRepository.existsById(documentId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "No hay un documento con el id " + documentId);
    }

    // Delete the document
    documentRepository.deleteById(documentId);

    return true;
  }


  /**
   * Save a document
   *
   * @param documentName the name of the document
   * @param documentType the type of the document
   * @param enrollmentId the id of the enrollment to save the document
   * @return the saved document
   */
  @Override
  public DocumentDto saveDocument(String documentName, String documentType, Long enrollmentId) {
    EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId).get();


    // Create the document
    DocumentEntity document = DocumentEntity.builder().documentName(documentName)
        .documentType(DocType.valueOf(documentType)).documentUrl(documentName)
        .enrollment(enrollment).build();

    // Save the document
    return DocumentMapper.convertToDto(documentRepository.save(document));
  }

  private boolean existsEnrollment(Long enrollmentId) {
    return enrollmentRepository.existsById(enrollmentId);
  }

  private EnrollmentEntity verifyPutParameters(String id) {
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
  }

  /**
   * Create the relation between the student and the parent/guardian
   *
   * @param student the student
   * @param parent  the parent/guardian
   */
  private void createParentGuardianStudentRelation(StudentEntity student,
      ParentsGuardianEntity parent) {
    // Create ParentGuardianStudentEntity
    ParentGuardianStudentEntity parentGuardianStudentEntity =
        new ParentGuardianStudentEntity(student, parent);

    // Add the relation to the student
    student.addParentGuardian(parentGuardianStudentEntity);

    // Add the relation to the parent/guardian
    parent.addStudent(parentGuardianStudentEntity);
  }
}
