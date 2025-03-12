package cr.co.ctpcit.citsacbackend.logic.services.exams;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.StudentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
  private final StudentRepository studentRepository;

  @Override
  public ExamDto getAcademicExam(String id) {
    //Validate Student Exists By ID
    StudentEntity student = studentRepository.findStudentEntityByStudentPerson_IdNumber(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    //Validate Student Inscriptions has an exam for today
    //If there is no exam for today, validate the nearest exam date enrollment

    //Asociate the exam with the enrollment id

    //Get the exam based on Grade of the enrollment and the quantity of questions


    //Return Exam with the enrollment id
    return ExamDto.builder().id(1L).build();
  }
}
