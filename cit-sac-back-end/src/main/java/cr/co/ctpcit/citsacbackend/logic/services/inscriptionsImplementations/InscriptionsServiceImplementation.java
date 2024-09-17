package cr.co.ctpcit.citsacbackend.logic.services.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.*;
import cr.co.ctpcit.citsacbackend.data.repositories.ParentGuardianStudentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.ParentsGuardianRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.SameDateEnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.mappers.AddressMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.EnrollmentMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.ParentGuardianMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.StudentMapper;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionsServiceImplementation implements InscriptionsService {

    private final StudentRepository studentRepository;
    private final ParentsGuardianRepository parentsGuardianRepository;
    private final ParentGuardianStudentRepository parentGuardianStudentRepository;

    @Autowired
    public InscriptionsServiceImplementation(StudentRepository studentRepository,
                                             ParentsGuardianRepository parentsGuardianRepository,
                                             ParentGuardianStudentRepository parentGuardianStudentRepository) {
        this.studentRepository = studentRepository;
        this.parentsGuardianRepository = parentsGuardianRepository;
        this.parentGuardianStudentRepository = parentGuardianStudentRepository;
    }

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

    @Override
    public StudentDto findStudentByIdNumber(String id) {
        //Find student by id
        Optional<StudentEntity> student = studentRepository.findStudentByIdNumber(id);

        //Convert student to DTO or null if not present
        return student.map(StudentMapper::convertToDto).orElse(null);
    }

    @Override
    public List<StudentDto> findStudentByValue(String value) {
        //Find all by name or first surname or previous school
        List<StudentEntity> student = studentRepository.findAllByFirstName(value);
        student.addAll(studentRepository.findAllByFirstSurname(value));
        student.addAll(studentRepository.findAllByPreviousSchool(value));

        //Convert student to DTO or null if not present
        return StudentMapper.convertToDtoList(student);
    }

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

        //Verify if the student is already enrolled
        if (student.isPresent()) {
            if (student.get().getEnrollments().stream().anyMatch(enrollment ->
                    enrollment.getExamDate().equals(enrollmentEntity.getExamDate()))) {
                throw new SameDateEnrollmentException();
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

    private void createParentGuardianStudentRelation(StudentEntity student, ParentsGuardianEntity parent) {
        //Create ParentGuardianStudentEntity
        ParentGuardianStudentEntity parentGuardianStudentEntity = new ParentGuardianStudentEntity(student, parent);

        //Add the relation to the student
        student.addParentGuardian(parentGuardianStudentEntity);

        //Add the relation to the parent/guardian
        parent.addStudent(parentGuardianStudentEntity);
    }
}