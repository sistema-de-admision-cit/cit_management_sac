package cr.co.ctpcit.citsacbackend.logic.mappers.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionOptionDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
  /**
   * Converts a QuestionEntity to a QuestionDto.
   *
   * @param entity the QuestionEntity instance
   * @return a QuestionDto with data copied from the entity
   */

  public static QuestionDto entityToDto(QuestionEntity entity) {
      if (entity == null) {
        return null;
      }

    // Convert the list of QuestionOptionEntity to QuestionOptionDto
    List<QuestionOptionDto> optionDtos = entity.getQuestionOptions().stream().map(
            option -> new QuestionOptionDto(option.getId(), option.getIsCorrect(), option.getOption()))
        .collect(Collectors.toList());

    // Build and return the QuestionDto
    return new QuestionDto(entity.getId(), entity.getQuestionType(), entity.getQuestionText(),
        entity.getImageUrl(), entity.getQuestionGrade(), entity.getQuestionLevel(),
        entity.getSelectionType(), entity.getDeleted(), optionDtos);
  }

  /**
   * Converts a QuestionDto to a QuestionEntity.
   *
   * @param dto the QuestionDto instance
   * @return a QuestionEntity with data copied from the dto
   */
  public static QuestionEntity dtoToEntity(QuestionDto dto) {
    if (dto == null) {
      return null;
    }

    // Build the main QuestionEntity
    QuestionEntity questionEntity = QuestionEntity.builder()
        .id(dto.id())  // Note: Typically we might not set the ID if it's auto-generated
        .questionType(dto.questionType()).questionText(dto.questionText()).imageUrl(dto.imageUrl())
        .questionGrade(dto.questionGrade()).questionLevel(dto.questionLevel())
        .selectionType(dto.selectionType()).deleted(dto.deleted()).build();

    // For each QuestionOptionDto, convert to entity and add to the questionEntity
    if (dto.questionOptions() != null) {
      dto.questionOptions().forEach(optionDto -> {
        QuestionOptionEntity optionEntity = QuestionOptionEntity.builder()
            .id(optionDto.id()) // Also often skipped if auto-generated
            .isCorrect(optionDto.isCorrect()).option(optionDto.option())
            .question(questionEntity) // associate with the parent question
            .build();
        questionEntity.addQuestionOption(optionEntity);
      });
    }

    return questionEntity;
  }

  /**
   * Converts a List of QuestionDto to a List of QuestionEntity.
   *
   * @param dtos the List of QuestionDto instances to convert to a List of QuestionEntity instances
   * @return a List of QuestionEntity instances with data copied from the dtos
   */
  public static List<QuestionEntity> dtoListToEntityList(List<QuestionDto> dtos) {
    return dtos.stream().map(QuestionMapper::dtoToEntity).collect(Collectors.toList());
  }

  /**
   * Converts a List of QuestionEntity to a List of QuestionDto.
   *
   * @param entities the List of QuestionEntity instances to convert to a List of QuestionDto
   *                 instances
   * @return a List of QuestionDto instances with data copied from the entities
   */
  public static List<QuestionDto> entityListToDtoList(List<QuestionEntity> entities) {
    return entities.stream().map(QuestionMapper::entityToDto).collect(Collectors.toList());
  }
}
