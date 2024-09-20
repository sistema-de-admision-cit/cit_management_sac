package cr.co.ctpcit.citsacbackend.logic.services.inscriptions.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.*;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.ParentGuardianStudentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.ParentsGuardianRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.mappers.AddressMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.EnrollmentMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.ParentGuardianMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.StudentMapper;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link InscriptionsService}
 * This class is used to manage the inscriptions of the students
 */
@Service
public class InscriptionsServiceImplementation implements InscriptionsService {

    private final StudentRepository studentRepository;
    private final ParentsGuardianRepository parentsGuardianRepository;
    private final ParentGuardianStudentRepository parentGuardianStudentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public InscriptionsServiceImplementation(StudentRepository studentRepository,
                                             ParentsGuardianRepository parentsGuardianRepository,
                                             ParentGuardianStudentRepository parentGuardianStudentRepository,
                                             EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.parentsGuardianRepository = parentsGuardianRepository;
        this.parentGuardianStudentRepository = parentGuardianStudentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Get all inscriptions
     * @param pageable the pageable object
     * @return a list of all inscriptions
     */
    @Override
    public List<StudentDto> getAllInscriptions(Pageable pageable) {
        //Find all students
        Page<StudentEntity> students = studentRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "idNumber")))
        );

        //Convert students to DTOs
        return StudentMapper.convertToDtoList(students.getContent());
    }

    /**
     * Get an inscription by id
     * @param id the id of the student
     * @return the inscription with the given id
     */
    @Override
    public StudentDto findStudentById(Long id) {
        //Find student by id
        Optional<StudentEntity> student = studentRepository.findStudentById(id);

        //Convert student to DTO or null if not present
        return student.map(StudentMapper::convertToDto).orElse(null);
    }

    /**
     * Get an inscription by value
     * @param value of the idNumber, the name of the student or first surname or previous school
     * @return a list of inscriptions that match the value
     */
    @Override
    public List<StudentDto> findStudentByValue(String value) {
        //Validate if the value is a number
        if (value.matches("\\d+")) {
            Optional<StudentEntity> student = studentRepository.findStudentByIdNumber(value);
            if (student.isPresent()) {
                return List.of(StudentMapper.convertToDto(student.get()));
            }
        }

        //Find all by name or first surname or previous school
        List<StudentEntity> student = new ArrayList<>();
        student.addAll(studentRepository.findAllByFirstName(value));
        student.addAll(studentRepository.findAllByFirstSurname(value));
        student.addAll(studentRepository.findAllByPreviousSchool(value));

        //Convert student to DTO or null if not present
        return StudentMapper.convertToDtoList(student);
    }

    /**
     * Add an inscription
     * @param inscriptionDto the inscription to add
     * @return the added inscription
     * @throws EnrollmentException if the student is already enrolled for the selected date
     */
    @Override
    public StudentDto addInscription(StudentDto inscriptionDto) {
        //Validate if the student already exists
        Optional<StudentEntity> student = studentRepository.findStudentByIdNumber(inscriptionDto.idNumber());

        //Get the student from optional or create a new one
        StudentEntity studentEntity = student
                .orElseGet(() -> StudentMapper.convertToEntity(inscriptionDto));

        //Get the Enrollment
        EnrollmentEntity enrollmentEntity = EnrollmentMapper
                .convertToEntity(inscriptionDto.enrollments().getFirst());

        //Set pending status to the enrollment
        enrollmentEntity.setStatus(ProcessStatus.P);

        //Verify if the student is already enrolled
        if (student.isPresent()) {
            if (student.get().getEnrollments().stream().anyMatch(enrollment ->
                    enrollment.getExamDate().equals(enrollmentEntity.getExamDate()))) {
                throw new EnrollmentException(
                        "El estudiante ya tiene una inscripción para la fecha seleccionada. " +
                        "Debe seleccionar otra fecha o comunicarse con el área de Servicio al Cliente."
                );
            }

        }

        //Save the enrollment
        studentEntity.addEnrollment(enrollmentEntity);

        //Verify if parent/guardian exists
        Optional<ParentsGuardianEntity> parentGuardian = parentsGuardianRepository
                .findParentsGuardianByIdNumber(inscriptionDto.parents().getFirst().idNumber());

        //Get the parent/guardian from optional or create a new one
        ParentsGuardianEntity parent = parentGuardian
                .orElseGet(() -> ParentGuardianMapper.convertToEntity(inscriptionDto.parents().getFirst()));

        //Get the address
        AddressEntity address = AddressMapper
                .convertToEntity(inscriptionDto.parents().getFirst().addresses().getFirst());

        //Save the address
        parent.addAddress(address);

        if(parentGuardian.isPresent()) {
            //Update the parent/guardian information
            parent.setEmail(inscriptionDto.parents().getFirst().email());
            parent.setPhoneNumber(inscriptionDto.parents().getFirst().phoneNumber());
        }

        //Save the parent/guardian
        parent = parentsGuardianRepository.save(parent);

        //If the student exists, add the enrollment and update/add parent/guardian information
        if (student.isEmpty()) {
            //Create the parent/guardian/student relation
            createParentGuardianStudentRelation(studentEntity, parent);
        } else {
            //Verify if the parent/guardian is already related to the student
            Optional<ParentGuardianStudentEntity> parentGuardianStudent = parentGuardianStudentRepository
                    .findParentGuardianStudentEntityByStudentAndParentGuardian(studentEntity, parent);
            if (parentGuardianStudent.isEmpty()) {
                //Create the parent/guardian/student relation
                createParentGuardianStudentRelation(studentEntity, parent);
            }
        }
        //Save the student
        studentEntity = studentRepository.save(studentEntity);

        //Return
        return StudentMapper.convertToDto(studentEntity);
    }

    /**
     * Update the exam date of the student
     * @param id the id of the enrollment
     * @param date the new exam date
     * @return the updated student
     */
    @Override
    public StudentDto updateExamDate(String id, String date) {
        //Validate if the id is a number
        if (!id.matches("\\d+")) {
            throw new EnrollmentException("El id no es un número válido");
        }

        //Find enrollment by id
        Optional<EnrollmentEntity> enrollment = enrollmentRepository.findById(Long.parseLong(id));

        //If the enrollment is not present, return null
        if (enrollment.isEmpty()) {
            //Return Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay una inscripción con el id " + id);
        }

        //Get the enrollment
        EnrollmentEntity enrollmentEntity = enrollment.get();

        //Update the exam date
        enrollmentEntity.setExamDate(LocalDate.parse(date));

        //Save the enrollment
        enrollmentEntity = enrollmentRepository.save(enrollmentEntity);

        //Return
        return StudentMapper.convertToDto(enrollmentEntity.getStudent());
    }

    /**
     * Create the relation between the student and the parent/guardian
     * @param student the student
     * @param parent the parent/guardian
     */
    private void createParentGuardianStudentRelation(StudentEntity student, ParentsGuardianEntity parent) {
        //Create ParentGuardianStudentEntity
        ParentGuardianStudentEntity parentGuardianStudentEntity = new ParentGuardianStudentEntity(student, parent);

        //Add the relation to the student
        student.addParentGuardian(parentGuardianStudentEntity);

        //Add the relation to the parent/guardian
        parent.addStudent(parentGuardianStudentEntity);
    }
}