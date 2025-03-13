package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.Question;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.QuestionAcaDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExamQuestionMapper {
  public static QuestionAcaDto questionToQuestionAcaDto(QuestionEntity questionEntity) {
    return new QuestionAcaDto(questionEntity.getId(), questionEntity.getQuestionType(),
        questionEntity.getQuestionText(), questionEntity.getImageUrl(),
        questionEntity.getQuestionGrade(), questionEntity.getQuestionLevel(),
        questionEntity.getSelectionType(), questionEntity.getDeleted(),
        OptionsMapper.optionsToOptionsAcaDto(questionEntity.getQuestionOptions()));
  }

  public static List<Question> questionsToQuestionsAcaDto(
      List<QuestionEntity> questionEntities) {
    return questionEntities.stream().map(ExamQuestionMapper::questionToQuestionAcaDto)
        .collect(Collectors.toList());
  }
}
