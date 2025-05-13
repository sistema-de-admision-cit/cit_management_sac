package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.QuestionDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishExamDetailsDto;

import java.util.List;

/**
 * A utility class for mapping between {@link ExamEntity} and various types of exam DTOs.
 * This class provides methods for converting exam-related entities to DTOs for different exam types,
 * such as academic exams (ACA), DAI exams, and English exams.
 */
public class ExamMapper {

  /**
   * A utility class for mapping between {@link ExamEntity} and various types of exam DTOs.
   * This class provides methods for converting exam-related entities to DTOs for different exam types,
   * such as academic exams (ACA), DAI exams, and English exams.
   */
  public static ExamAcaDto examToExamAcaDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamAcaDto.builder().id(examEntity.getId())
        .enrollment(String.valueOf(examEntity.getEnrollment().getId())).examDate(examEntity.getExamDate())
        .examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsAcaDto(questions)).build();
  }

  /**
   * Converts an {@link ExamEntity} and a list of {@link QuestionEntity} objects to an {@link ExamAcaDto}.
   *
   * @param examEntity the {@link ExamEntity} object to be converted
   * @param questions  a list of {@link QuestionEntity} objects associated with the exam
   * @return an {@link ExamAcaDto} representing the academic exam
   */
  public static ExamDaiDto examToExamDaiDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamDaiDto.builder().id(examEntity.getId())
        .enrollment(String.valueOf(examEntity.getEnrollment().getId())).examDate(examEntity.getExamDate())
        .examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsDaiDto(questions)).build();
  }

  /**
   * Converts an {@link ExamEntity} to an {@link AcademicExamDetailsDto} by deserializing the exam responses.
   *
   * @param examEntity the {@link ExamEntity} object to be converted
   * @return an {@link AcademicExamDetailsDto} representing the academic exam details
   * @throws JsonProcessingException if there's an error during JSON deserialization
   */
  public static AcademicExamDetailsDto academicExamToAcademicExamDetailsDto(ExamEntity examEntity)
      throws JsonProcessingException {
    if (verifyExamType(examEntity, ExamType.ACA))
      return null;

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    String responses = (String) examEntity.getResponses().get("exam");
    List<QuestionAcaDto> questions = mapper.readValue(responses,
        mapper.getTypeFactory().constructCollectionLikeType(List.class, QuestionAcaDto.class));

    ExamAcaDto exam = ExamAcaDto.builder().id(examEntity.getAcademicExam().getId())
        .enrollment(String.valueOf(examEntity.getEnrollment().getId())).examType(examEntity.getExamType())
        .examDate(examEntity.getExamDate()).responses(questions).build();

    return AcademicExamDetailsDto.builder().id(examEntity.getId())
        .grade(examEntity.getAcademicExam().getGrade()).exam(exam).build();
  }

  /**
   * Converts a list of {@link ExamEntity} objects to a list of {@link AcademicExamDetailsDto} objects.
   *
   * @param exams a list of {@link ExamEntity} objects to be converted
   * @return a list of {@link AcademicExamDetailsDto} objects representing the academic exams
   */
  public static List<AcademicExamDetailsDto> academicExamsToAcademicExamDetailsDto(
      List<ExamEntity> exams) {
    return exams.stream().map(e -> {
      try {
        return academicExamToAcademicExamDetailsDto(e);
      } catch (JsonProcessingException e1) {
        e1.printStackTrace();
        return null;
      }
    }).toList();
  }

  /**
   * Converts a list of {@link ExamEntity} objects to a list of {@link AcademicExamDetailsDto} objects.
   *
   * @param exams a list of {@link ExamEntity} objects to be converted
   * @return a list of {@link AcademicExamDetailsDto} objects representing the academic exams
   */
  public static List<DaiExamDetailsDto> daiExamsToDaiExamDetailsDto(List<ExamEntity> exams) {
    return exams.stream().map(e -> {
      try {
        return daiExamToDaiExamDetailsDto(e);
      } catch (JsonProcessingException e1) {
        return null;
      }
    }).toList();
  }

  /**
   * Converts an {@link ExamEntity} to a {@link DaiExamDetailsDto} by deserializing the exam responses.
   *
   * @param examEntity the {@link ExamEntity} object to be converted
   * @return a {@link DaiExamDetailsDto} representing the DAI exam details
   * @throws JsonProcessingException if there's an error during JSON deserialization
   */
  private static DaiExamDetailsDto daiExamToDaiExamDetailsDto(ExamEntity examEntity)
      throws JsonProcessingException {
    if (verifyExamType(examEntity, ExamType.DAI))
      return null;

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    String responses = (String) examEntity.getResponses().get("exam");
    List<QuestionDaiDto> questions = mapper.readValue(responses,
        mapper.getTypeFactory().constructCollectionLikeType(List.class, QuestionDaiDto.class));

    ExamDaiDto exam = ExamDaiDto.builder().id(examEntity.getId())
        .enrollment(String.valueOf(examEntity.getEnrollment().getId())).examType(examEntity.getExamType())
        .examDate(examEntity.getExamDate()).responses(questions).build();

    return DaiExamDetailsDto.builder().id(examEntity.getId())
        .comment(examEntity.getDaiExam().getComment())
        .recommendation(examEntity.getDaiExam().getRecommendation())
        .reviewed(examEntity.getDaiExam().getReviewed()).exam(exam).build();

  }

  /**
   * Verifies whether the given {@link ExamEntity} matches the specified {@link ExamType}.
   *
   * @param examEntity the {@link ExamEntity} to be checked
   * @param examType   the {@link ExamType} to be verified
   * @return true if the exam type matches, false otherwise
   */
  private static boolean verifyExamType(ExamEntity examEntity, ExamType examType) {
    return switch (examType) {
      case ACA -> examEntity.getAcademicExam() == null || examEntity.getExamType() != ExamType.ACA;
      case DAI -> examEntity.getDaiExam() == null || examEntity.getExamType() != ExamType.DAI;
      case ENG -> examEntity.getEnrollment() == null || examEntity.getExamType() != ExamType.ENG;
      default -> true;
    };
  }

  /**
   * Converts an {@link ExamEntity} to an {@link EnglishExamDetailsDto}.
   *
   * @param examEntity the {@link ExamEntity} to be converted
   * @return an {@link EnglishExamDetailsDto} representing the English exam details
   */
  public static EnglishExamDetailsDto englishExamToEnglishExamDetailsDto(ExamEntity examEntity) {
    if (verifyExamType(examEntity, ExamType.ENG))
      return null;

    return EnglishExamDetailsDto.builder().examId(examEntity.getId())
        .enrollmentId(examEntity.getEnrollment().getId()).examDate(examEntity.getExamDate())
        .trackTestId(examEntity.getEnglishExam().getTrackTestId())
        .level(examEntity.getEnglishExam().getLevel()).core(examEntity.getEnglishExam().getCore())
        .build();
  }

  /**
   * Converts a list of {@link ExamEntity} objects to a list of {@link EnglishExamDetailsDto} objects.
   *
   * @param englishExams a list of {@link ExamEntity} objects to be converted
   * @return a list of {@link EnglishExamDetailsDto} objects representing the English exams
   */
  public static List<EnglishExamDetailsDto> englishExamsToEnglishExamDetailsDto(
      List<ExamEntity> englishExams) {
    return englishExams.stream().map(ExamMapper::englishExamToEnglishExamDetailsDto).toList();
  }
}
