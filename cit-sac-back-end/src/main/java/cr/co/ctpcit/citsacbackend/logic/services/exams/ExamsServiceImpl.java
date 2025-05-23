package cr.co.ctpcit.citsacbackend.logic.services.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionOptionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.ExamMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions.StudentMapper;
import cr.co.ctpcit.citsacbackend.logic.services.logs.LogsScoreService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Service implementation for exam operations.
 * Handles creation, retrieval, and processing of academic, DAI, and English exams.
 */
@Service
@RequiredArgsConstructor
public class ExamsServiceImpl implements ExamsService {
  private final StudentRepository studentRepository;
  private final EnrollmentRepository enrollmentRepository;
  private final QuestionRepository questionRepository;
  private final SystemConfigRepository systemConfigRepository;
  private final ExamRepository examRepository;
  private final ObjectMapper mapper;
  private final LogsScoreService logsScoreService;
  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public void saveAcademicExam(ExamAcaDto examDto) throws JsonProcessingException {
    ExamEntity exam = verifyExam(examDto.id(), examDto.examType());

    //Create the academic exam
    AcademicExamEntity academicExam = new AcademicExamEntity();
    academicExam.setGrade(obtainAcademicExamGrade(
        examDto.responses())); //Set the grade based on the correct responses

    //Add the academic exam to the exam
    exam.addAcademicExam(academicExam);

    //Add the responses to the exam
    exam.getResponses().put("exam", mapper.writeValueAsString(examDto.responses()));

    //Save the exam
    examRepository.save(exam);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public ExamDaiDto getDaiExam(String id) {
    EnrollmentEntity enrollmentInUse = getEnrollmentInUse(id, ExamType.DAI);

    //Get the questions
    List<QuestionEntity> questions = questionRepository.findRandomQuestionsByGradeAndType(enrollmentInUse.getGradeToEnroll(),QuestionType.DAI,
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
  /**
   * {@inheritDoc}
   */
  @Override
  public void saveDaiExam(ExamDaiDto examDto) throws JsonProcessingException {
    ExamEntity exam = verifyExam(examDto.id(), examDto.examType());

    //Create the dai exam
    DaiExamEntity daiExam = new DaiExamEntity();

    //Add the academic exam to the exam
    exam.addDaiExam(daiExam);

    //Add the responses to the exam
    exam.getResponses().put("exam", mapper.writeValueAsString(examDto.responses()));

    //Save the exam
    examRepository.save(exam);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AcademicExamDetailsDto> getExistingAcademicExams(String idNumber) {
    List<EnrollmentEntity> enrollments = getEnrollmentEntities(idNumber);

    //Create the list of AcademicExamEntities
    List<ExamEntity> academicExams = new ArrayList<>();
    for (EnrollmentEntity enrollmentInUse : enrollments) {
      for (ExamEntity exam : enrollmentInUse.getExams()) {
        if (exam.getExamType().equals(ExamType.ACA)) {
          academicExams.add(exam);
        }
      }
    }

    return ExamMapper.academicExamsToAcademicExamDetailsDto(academicExams);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DaiExamDetailsDto> getExistingDaiExams(String idNumber) {
    List<EnrollmentEntity> enrollments = getEnrollmentEntities(idNumber);

    //Create the list of AcademicExamEntities
    List<ExamEntity> daiExams = new ArrayList<>();
    getExamsFromEnrollmentsByExamType(enrollments, ExamType.DAI, daiExams);

    return ExamMapper.daiExamsToDaiExamDetailsDto(daiExams);
  }
  /**
   * Helper method to filter exams by type from enrollments.
   *
   * @param enrollments list of enrollments to search
   * @param dai the exam type to filter
   * @param daiExams the list to populate with matching exams
   */
  private static void getExamsFromEnrollmentsByExamType(List<EnrollmentEntity> enrollments,
      ExamType dai, List<ExamEntity> daiExams) {
    for (EnrollmentEntity enrollmentInUse : enrollments) {
      for (ExamEntity exam : enrollmentInUse.getExams()) {
        if (exam.getExamType().equals(dai)) {
          daiExams.add(exam);
        }
      }
    }
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<StudentExamsDto> getStudentsByExamType(ExamType examType, Pageable pageable) {
    List<StudentEntity> students = studentRepository
            .findStudentsWithExamType(examType, pageable)
            .toList();

    if (students.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron estudiantes.");
    }

    return getStudentExamsDto(students, examType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateDaiExam(DaiExamDetailsDto daiExamDetailsDto) {
    ExamEntity exam = examRepository.findById(daiExamDetailsDto.id()).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El examen no fue encontrado."));

    DaiExamEntity daiExam = exam.getDaiExam();
    if (daiExam == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El examen no fue encontrado.");
    }

    //Update the exam
    daiExam.setComment(daiExamDetailsDto.comment());
    daiExam.setRecommendation(daiExamDetailsDto.recommendation());
    daiExam.setReviewed(true);

    //Save the exam
    examRepository.save(exam);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores) {
    Instant now = Instant.now(); // process id for the stored procedure (tbl_logs_score)
    Integer processId = now.getNano();

    for (EnglishScoreEntryDTO score : englishScores) {
      // Normalizar nombres y apellidos
      String normalizedNames = normalizeString(score.names());
      String normalizedLastNames = normalizeString(score.lastNames());

      // usp_process_english_exam_and_log
      // procedure to update the score of an english exam and log the change in the database
      examRepository.usp_process_english_exam_and_log(normalizedNames, normalizedLastNames,
          score.lastTest(), score.id(),
          BigDecimal.valueOf(Double.parseDouble(score.core().replace("%", ""))),
          score.level().toString(), processId);
    }

    return logsScoreService.getLogsScoresByProcessId(processId);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<EnglishExamDetailsDto> getExistingEnglishExams(String idNumber) {
    //Find students enrollments
    List<EnrollmentEntity> enrollments = getEnrollmentEntities(idNumber);

    //Create a list with Exams of ENG examType
    List<ExamEntity> englishExams = new ArrayList<>();
    for (EnrollmentEntity enrollment : enrollments) {
      for (ExamEntity exam : enrollment.getExams()) {
        if (exam.getExamType().equals(ExamType.ENG)) {
          englishExams.add(exam);
        }
      }
    }

    //Map the list to EnglishExamDetailsDto
    return ExamMapper.englishExamsToEnglishExamDetailsDto(englishExams);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<StudentExamsDto> searchStudentExams(String value, ExamType examType,
      Pageable pageable) {
    //Validate if the value is a number
    List<StudentEntity> students;
    if (value.matches("\\d+")) {
      students = studentRepository.findAllByValueWithExamsInProcess(value, pageable);
    } else {
      //Search for persons by value
      students = studentRepository.findAllByValueWithExamsInProcess(value, pageable);
    }

    if (students.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron exámenes.");
    }

    return getStudentExamsDto(students, examType);
  }
  /**
   * Converts student entities to exam DTOs filtered by exam type.
   *
   * @param students list of student entities
   * @param examType type of exam to filter
   * @return list of student exam DTOs
   */
  private List<StudentExamsDto> getStudentExamsDto(List<StudentEntity> students, ExamType examType) {
    //Create the list of StudentExamsDto
    List<StudentExamsDto> studentExams = new ArrayList<>();
    for (StudentEntity student : students) {
      //Get the exams by type
      List<ExamEntity> exams = findExamsByType(student, examType);

      if (exams.isEmpty()) {
        continue;
      }

      studentExams.add(StudentMapper.studentToStudentExamsDto(student, exams, examType));
    }

    return studentExams;
  }
  /**
   * Normalizes a string by converting to lowercase and removing accents.
   *
   * @param input the string to normalize
   * @return normalized string or null if input is null
   */
  // normalize the string to lowercase and remove accents
  private String normalizeString(String input) {
    if (input == null)
      return null;
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{M}", "").toLowerCase();
  }
  /**
   * Finds exams of specified type for a student.
   *
   * @param student the student entity
   * @param examType type of exam to find
   * @return list of matching exams
   */
  private List<ExamEntity> findExamsByType(StudentEntity student, ExamType examType) {
    List<ExamEntity> exams = new ArrayList<>();
    for (EnrollmentEntity enrollment : student.getEnrollments()) {
      for (ExamEntity exam : enrollment.getExams()) {
        if (exam.getExamType().equals(examType)) {
          exams.add(exam);
        }
      }
    }

    return exams;
  }
  /**
   * Retrieves enrollments for a student by ID number.
   *
   * @param id the student ID number
   * @return list of enrollment entities
   * @throws ResponseStatusException if no enrollments found
   */
  private List<EnrollmentEntity> getEnrollmentEntities(String id) {
    List<EnrollmentEntity> enrollments =
        enrollmentRepository.findAllByStudent_StudentPerson_IdNumber(id);

    if (enrollments.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron exámenes.");
    }
    return enrollments;
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
  /**
   * Gets the active enrollment for exam taking.
   *
   * @param id the student ID
   * @param examType type of exam
   * @return the active enrollment entity
   * @throws ResponseStatusException if no valid enrollment found
   */
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
  /**
   * Gets the configured quantity of questions for an exam type.
   *
   * @param configurations the exam configuration type
   * @return the number of questions configured
   */
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
  /**
   * Verifies an exam exists and is valid for operations.
   *
   * @param id the exam ID
   * @param examType the expected exam type
   * @return the verified exam entity
   * @throws ResponseStatusException if exam is invalid or not found
   */
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

  /**
   * Retrieves the number of students who have taken academic (ACA) exams.
   *
   * @return the count of students with academic exams
   */
  @Override
  public Long getStudentsCountWithAcademicExams() {
    return examRepository.countStudentsWithAcademicExams();
  }


  /**
   * Retrieves the number of students who have taken DAI exams.
   *
   * @return the count of students with DAI exams
   */

  @Override
  public Long getStudentsCountWithDAIExams() {
    return examRepository.countStudentsWithDAIExams();
  }

  /**
   * Retrieves the number of students matching a search value who have taken academic (ACA) exams.
   *
   * @param value the search term used to filter students by ID, name, or surname
   * @return the count of matched students with academic exams
   */

  @Override
  public Long getSearchCountByAcademicExam(String value) {
    return examRepository.countStudentsWithAcademicExamsBySearch(value);
  }

  /**
   * Retrieves the number of students matching a search value who have taken DAI exams.
   *
   * @param value the search term used to filter students by ID, name, or surname
   * @return the count of matched students with DAI exams
   */

  @Override
  public Long getSearchCountByDAIExam(String value) {
    return examRepository.countStudentsWithDAIExamsBySearch(value);
  }

}
