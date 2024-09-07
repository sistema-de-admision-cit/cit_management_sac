package cr.co.ctpcit.citsacbackend.logic.services.inscriptionsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.ParentGuardianStudentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.InscriptionMapper;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionsServiceImplementation implements InscriptionsService {

    private final EnrollmentRepository enrollmentRepository;
    private final ParentGuardianStudentRepository parentGuardianStudentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public InscriptionsServiceImplementation(EnrollmentRepository enrollmentRepository,
                                             ParentGuardianStudentRepository parentGuardianStudentRepository,
                                             StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.parentGuardianStudentRepository = parentGuardianStudentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public InscriptionDto findById(Integer id) {
        //Find student
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        if (studentEntity.isEmpty()) return null;

        //Find enrollment
        List<EnrollmentEntity> enrollmentEntity = enrollmentRepository.findAllByStudentId(studentEntity.get().getId());
        if(enrollmentEntity.isEmpty()) return null;

        //Find parents
        List<ParentGuardianStudentEntity> parentGuardianStudentEntity =
                parentGuardianStudentRepository.findAllByStudentId(studentEntity.get().getId());
        if(parentGuardianStudentEntity.isEmpty()) return null;

        //Filter parents
        List<ParentsGuardianEntity> parents = parentGuardianStudentEntity.stream()
                .map(ParentGuardianStudentEntity::getParentGuardian)
                .toList();
        if(parents.isEmpty()) return null;

        return InscriptionMapper
                .convertToDto(studentEntity.get(), enrollmentEntity, parents);
    }

    @Override
    public List<InscriptionDto> findAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public InscriptionDto addInscription(InscriptionDto inscriptionDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}