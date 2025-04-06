package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.QuestionDaiDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExamQuestionMapper {
  public static QuestionAcaDto questionToQuestionAcaDto(QuestionEntity questionEntity) {
    return new QuestionAcaDto(questionEntity.getId(), questionEntity.getQuestionType(),
        questionEntity.getQuestionText(), questionEntity.getImageUrl(),
        questionEntity.getSelectionType(), questionEntity.getDeleted(),
        OptionsMapper.optionsToOptionsAcaDto(questionEntity.getQuestionOptions()));
  }

  public static List<QuestionAcaDto> questionsToQuestionsAcaDto(
      List<QuestionEntity> questionEntities) {
    return questionEntities.stream().map(ExamQuestionMapper::questionToQuestionAcaDto)
        .collect(Collectors.toList());
  }

  private static QuestionDaiDto questionToQuestionDaiDto(QuestionEntity question) {
    return QuestionDaiDto.builder().id(question.getId()).questionType(question.getQuestionType())
        .questionText(question.getQuestionText()).imageUrl(question.getImageUrl())
        .selectionType(question.getSelectionType()).deleted(question.getDeleted()).build();
  }

  public static List<QuestionDaiDto> questionsToQuestionsDaiDto(List<QuestionEntity> questions) {
    return questions.stream().map(ExamQuestionMapper::questionToQuestionDaiDto)
        .collect(Collectors.toList());
  }
}
