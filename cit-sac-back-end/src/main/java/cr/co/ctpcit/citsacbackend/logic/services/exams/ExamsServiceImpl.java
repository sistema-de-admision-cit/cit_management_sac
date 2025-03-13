package cr.co.ctpcit.citsacbackend.logic.services.exams;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.exams.ExamRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.StudentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.ExamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
  private final StudentRepository studentRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final QuestionRepository questionRepository;
  private final SystemConfigRepository systemConfigRepository;
  private final ExamRepository examRepository;

  @Override
  public ExamDto getAcademicExam(String id) {
    //Validate Student Exists By ID
    StudentEntity student = studentRepository.findStudentEntityByStudentPerson_IdNumber(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Verifique su número de cédula."));

    List<EnrollmentEntity> enrollments = enrollmentRepository.findAllByStudent(student);
    EnrollmentEntity enrollmentInUse = null;
    //Validate Student Inscriptions has an exam for today
    for (EnrollmentEntity enrollment : enrollments) {
      //If there is an exam for today, return the exam
      if (enrollment.getExamDate().equals(LocalDate.now())) {
        enrollmentInUse = enrollment;
      }
    }

    //If there is no exam for today, validate the nearest exam date enrollment
    if (enrollmentInUse == null) {
      for (EnrollmentEntity enrollment : enrollments) {
        //Validate if a ACA exam already exists
        enrollmentInUse = enrollment;
        for (ExamEntity exam : enrollment.getExams()) {
          if (exam.getExamType().equals(ExamType.ACA)) {
            enrollmentInUse = null;
          }
        }
        if (enrollmentInUse != null) {
          break;
        }
      }
    }

    if (enrollmentInUse == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "El estudiante no tiene inscripciones activas para examen.");
    }

    //Get the exam based on Grade of the enrollment and the quantity of questions
    SystemConfigEntity config =
        systemConfigRepository.findByConfigName(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY)
            .orElse(null);

    if (config == null) {
      config = new SystemConfigEntity();
      config.setConfigName(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY);
      config.setConfigValue("40");
      systemConfigRepository.save(config);
    }

    //Get the questions
    List<QuestionEntity> questions =
        questionRepository.findRandomQuestionsByGradeAndType(enrollmentInUse.getGradeToEnroll(),
            QuestionType.ACA, Integer.parseInt(config.getConfigValue()));

    //Asociate the exam to the enrollment
    ExamEntity examEntity =
        ExamEntity.builder().enrollment(enrollmentInUse).examType(ExamType.ACA).build();

    //Save the exam
    enrollmentInUse.addExam(examEntity);
    examEntity = examRepository.save(examEntity);

    //Return Exam with the enrollment id
    return ExamMapper.examToExamDto(examEntity, questions);
  }
}
