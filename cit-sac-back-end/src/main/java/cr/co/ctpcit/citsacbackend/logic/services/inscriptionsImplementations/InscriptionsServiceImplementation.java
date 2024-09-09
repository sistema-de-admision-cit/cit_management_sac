package cr.co.ctpcit.citsacbackend.logic.services.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.StudentMapper;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscriptionsServiceImplementation implements InscriptionsService {

    private final StudentRepository studentRepository;

    @Autowired
    public InscriptionsServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDto findByStudentIdNumber(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<StudentDto> getAllInscriptions() {
        //Find all students
        List<StudentEntity> students = studentRepository.findAll();

        //Convert students to DTOs
        return StudentMapper.convertToDtoList(students);
    }

    @Override
    public StudentDto addInscription(StudentDto inscriptionDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}