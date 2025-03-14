package cr.co.ctpcit.citsacbackend.logic.services.exams;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.exams.ExamRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.StudentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.QuestionOptionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.ExamMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

  /**
   * Get the academic exam for the student
   *
   * @param id The student idNumber (Cédula)
   * @return The academic exam
   * @throws ResponseStatusException If the student does not have an exam for today or the nearest
   *                                 exam date or if the student does not have active inscriptions
   */
  @Override
  public ExamAcaDto getAcademicExam(String id) {
    EnrollmentEntity enrollmentInUse = getEnrollmentInUse(id, ExamType.ACA);

    //Get the questions
    List<QuestionEntity> questions =
        questionRepository.findRandomQuestionsByGradeAndType(enrollmentInUse.getGradeToEnroll(),
            QuestionType.ACA, getQuestionQuantity(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY));

    //Asociate the exam to the enrollment
    ExamEntity examEntity =
        ExamEntity.builder().enrollment(enrollmentInUse).examType(ExamType.ACA).build();

    //Save the exam
    enrollmentInUse.addExam(examEntity);
    examEntity = examRepository.save(examEntity);

    //Return Exam with the enrollment id
    return ExamMapper.examToExamAcaDto(examEntity, questions);
  }

  /**
   * Save the academic exam
   *
   * @param examDto The exam to save
   * @throws ResponseStatusException If the exam is not found, the exam is already answered or the
   *                                 exam is not academic
   */
  @Override
  public void saveAcademicExam(ExamAcaDto examDto) {
    ExamEntity exam = verifyExam(examDto.id(), examDto.examType());

    //Create the academic exam
    AcademicExamEntity academicExam = new AcademicExamEntity();
    academicExam.setGrade(obtainAcademicExamGrade(
        examDto.responses())); //Set the grade based on the correct responses

    //Add the academic exam to the exam
    exam.addAcademicExam(academicExam);

    //Add the responses to the exam
    exam.getResponses().put("exam", examDto.responses());

    //Save the exam
    examRepository.save(exam);
  }

  @Override
  public ExamDaiDto getDaiExam(String id) {
    EnrollmentEntity enrollmentInUse = getEnrollmentInUse(id, ExamType.DAI);

    //Get the questions
    List<QuestionEntity> questions = questionRepository.findQuestionsByType(QuestionType.DAI,
        getQuestionQuantity(Configurations.DAI_EXAM_QUESTIONS_QUANTITY));

    //Asociate the exam to the enrollment
    ExamEntity examEntity =
        ExamEntity.builder().enrollment(enrollmentInUse).examType(ExamType.DAI).build();

    //Save the exam
    enrollmentInUse.addExam(examEntity);
    examEntity = examRepository.save(examEntity);

    //Return Exam with the enrollment id
    return ExamMapper.examToExamDaiDto(examEntity, questions);
  }

  @Override
  public void saveDaiExam(ExamDaiDto examDto) {
    ExamEntity exam = verifyExam(examDto.id(), examDto.examType());

    //Create the dai exam
    DaiExamEntity daiExam = new DaiExamEntity();

    //Add the academic exam to the exam
    exam.addDaiExam(daiExam);

    //Add the responses to the exam
    exam.getResponses().put("exam", examDto.responses());

    //Save the exam
    examRepository.save(exam);
  }

  /**
   * Obtain the academic exam grade based on the responses of the student
   *
   * @param responses The responses of the student
   * @return The grade of the exam
   */
  private @NotNull BigDecimal obtainAcademicExamGrade(List<QuestionAcaDto> responses) {
    //Calculate the grade
    int questionsQuantity = responses.size();
    double questionsCorrect = 0;

    for (QuestionAcaDto question : responses) {
      if (question.selectionType().equals(SelectionType.SINGLE)) {
        for (QuestionOptionAcaDto option : question.questionOptions()) {
          if (option.isCorrect() && option.selected()) {
            questionsCorrect++;
            break;
          }
        }
      } else { //Multiple selection
        int posibleOptions = question.questionOptions().size();
        for (QuestionOptionAcaDto option : question.questionOptions()) {
          if (option.isCorrect() && option.selected()) {
            questionsCorrect += 1.0 / posibleOptions;
          }
        }
      }
    }

    return new BigDecimal(100 * questionsCorrect / questionsQuantity);
  }

  private EnrollmentEntity getEnrollmentInUse(String id, ExamType examType) {
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
          if (exam.getExamType().equals(examType)) {
            enrollmentInUse = null;
            break;
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

    return enrollmentInUse;
  }

  private int getQuestionQuantity(Configurations configurations) {
    if (!configurations.equals(
        Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY) && !configurations.equals(
        Configurations.DAI_EXAM_QUESTIONS_QUANTITY)) {
      return 0;
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

    return Integer.parseInt(config.getConfigValue());
  }

  private ExamEntity verifyExam(Long id, ExamType examType) {
    //Look for the examEntity
    ExamEntity exam = examRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Examen no encontrado."));

    //Validate the exam is not already answered
    if (exam.getResponses().containsKey("exam")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El examen ya fue respondido.");
    }

    //Validates if the exam is not Academic
    if (!exam.getExamType().equals(examType)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "El examen no es del tipo correspondiente.");
    }

    return exam;
  }
}
