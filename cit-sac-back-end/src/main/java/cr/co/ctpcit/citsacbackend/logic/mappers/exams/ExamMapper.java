package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.QuestionAcaDto;
import lombok.SneakyThrows;

import java.util.List;

public class ExamMapper {

  public static ExamAcaDto examToExamAcaDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamAcaDto.builder().id(examEntity.getId())
        .enrollment(examEntity.getEnrollment().getId()).examDate(examEntity.getExamDate())
        .examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsAcaDto(questions)).build();
  }

  public static ExamDaiDto examToExamDaiDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamDaiDto.builder().id(examEntity.getId())
        .enrollment(examEntity.getEnrollment().getId()).examDate(examEntity.getExamDate())
        .examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsDaiDto(questions)).build();
  }

  public static AcademicExamDto academicExamToExamAcaDto(ExamEntity examEntity)
      throws JsonProcessingException {
    if (examEntity.getAcademicExam() == null || !examEntity.getExamType().equals(ExamType.ACA)) {
      return null;
    }

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    String responses = (String) examEntity.getResponses().get("exam");
    List<QuestionAcaDto> questions = mapper.readValue(responses,
        mapper.getTypeFactory().constructCollectionLikeType(List.class, QuestionAcaDto.class));

    ExamAcaDto exam = ExamAcaDto.builder().id(examEntity.getAcademicExam().getId())
        .enrollment(examEntity.getEnrollment().getId()).examType(examEntity.getExamType())
        .examDate(examEntity.getExamDate()).responses(questions).build();

    return AcademicExamDto.builder().id(examEntity.getId())
        .grade(examEntity.getAcademicExam().getGrade()).exam(exam).build();
  }

  public static List<AcademicExamDto> academicExamsToExamAcaDto(List<ExamEntity> exams) {
    return exams.stream().map(e -> {
      try {
        return academicExamToExamAcaDto(e);
      } catch (JsonProcessingException e1) {
        e1.printStackTrace();
        return null;
      }
    }).toList();
  }
}
