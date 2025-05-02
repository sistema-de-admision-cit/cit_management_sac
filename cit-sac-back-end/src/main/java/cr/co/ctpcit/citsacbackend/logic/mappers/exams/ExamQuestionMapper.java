package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.QuestionDaiDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class to map {@link QuestionEntity} to different DTOs.
 */
public class ExamQuestionMapper {

  /**
   * Maps a {@link QuestionEntity} to a {@link QuestionAcaDto}.
   *
   * @param questionEntity the {@link QuestionEntity} to be mapped
   * @return a {@link QuestionAcaDto} representing the mapped data
   */
  public static QuestionAcaDto questionToQuestionAcaDto(QuestionEntity questionEntity) {
    return new QuestionAcaDto(questionEntity.getId(), questionEntity.getQuestionType(),
        questionEntity.getQuestionText(), questionEntity.getImageUrl(),
        questionEntity.getSelectionType(), questionEntity.getDeleted(),
        OptionsMapper.optionsToOptionsAcaDto(questionEntity.getQuestionOptions()));
  }

  /**
   * Maps a list of {@link QuestionEntity} objects to a list of {@link QuestionAcaDto} objects.
   *
   * @param questionEntities the list of {@link QuestionEntity} objects to be mapped
   * @return a list of {@link QuestionAcaDto} objects representing the mapped data
   */
  public static List<QuestionAcaDto> questionsToQuestionsAcaDto(
      List<QuestionEntity> questionEntities) {
    return questionEntities.stream().map(ExamQuestionMapper::questionToQuestionAcaDto)
        .collect(Collectors.toList());
  }

  /**
   * Maps a {@link QuestionEntity} to a {@link QuestionDaiDto}.
   *
   * @param question the {@link QuestionEntity} to be mapped
   * @return a {@link QuestionDaiDto} representing the mapped data
   */
  private static QuestionDaiDto questionToQuestionDaiDto(QuestionEntity question) {
    return QuestionDaiDto.builder().id(question.getId()).questionType(question.getQuestionType())
        .questionText(question.getQuestionText()).imageUrl(question.getImageUrl())
        .selectionType(question.getSelectionType()).deleted(question.getDeleted()).build();
  }

  /**
   * Maps a list of {@link QuestionEntity} objects to a list of {@link QuestionDaiDto} objects.
   *
   * @param questions the list of {@link QuestionEntity} objects to be mapped
   * @return a list of {@link QuestionDaiDto} objects representing the mapped data
   */
  public static List<QuestionDaiDto> questionsToQuestionsDaiDto(List<QuestionEntity> questions) {
    return questions.stream().map(ExamQuestionMapper::questionToQuestionDaiDto)
        .collect(Collectors.toList());
  }
}
