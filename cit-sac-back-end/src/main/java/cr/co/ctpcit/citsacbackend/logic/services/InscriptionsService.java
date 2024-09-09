package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;

import java.util.List;

public interface InscriptionsService {
    StudentDto findByStudentIdNumber(String id);
    List<StudentDto> getAllInscriptions();
    StudentDto addInscription(StudentDto inscriptionDto);
}
