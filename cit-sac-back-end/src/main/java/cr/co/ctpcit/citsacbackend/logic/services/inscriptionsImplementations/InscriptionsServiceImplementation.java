package cr.co.ctpcit.citsacbackend.logic.services.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
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

    @Autowired
    public InscriptionsServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public StudentDto addInscription(StudentDto inscriptionDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}