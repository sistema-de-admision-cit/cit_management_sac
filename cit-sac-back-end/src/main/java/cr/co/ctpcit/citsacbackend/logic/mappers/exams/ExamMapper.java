package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;

import java.util.List;

public class ExamMapper {
  public static ExamDto examToExamDto(ExamEntity examEntity, List<QuestionEntity> questions) {
    return ExamDto.builder().id(examEntity.getId()).enrollment(examEntity.getEnrollment().getId())
        .examDate(examEntity.getExamDate()).examType(examEntity.getExamType())
        .responses(ExamQuestionMapper.questionsToQuestionsAcaDto(questions)).build();
  }
}
