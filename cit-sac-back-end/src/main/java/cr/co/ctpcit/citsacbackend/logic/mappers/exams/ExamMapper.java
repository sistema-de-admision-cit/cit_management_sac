package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;

import java.util.List;

public class ExamMapper {
  public static ExamAcaDto examToExamAcaDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamAcaDto.builder().id(examEntity.getId()).enrollment(examEntity.getEnrollment().getId())
        .examDate(examEntity.getExamDate()).examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsAcaDto(questions)).build();
  }

  public static ExamDaiDto examToExamDaiDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamDaiDto.builder().id(examEntity.getId()).enrollment(examEntity.getEnrollment().getId())
        .examDate(examEntity.getExamDate()).examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsDaiDto(questions)).build();
  }
}
