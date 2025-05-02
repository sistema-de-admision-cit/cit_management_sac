package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionOptionAcaDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class to map {@link QuestionOptionEntity} to {@link QuestionOptionAcaDto}.
 */
public class OptionsMapper {

  /**
   * Maps a {@link QuestionOptionEntity} to a {@link QuestionOptionAcaDto}.
   *
   * @param optionEntity the {@link QuestionOptionEntity} to be mapped
   * @return a {@link QuestionOptionAcaDto} representing the mapped data
   */
  public static QuestionOptionAcaDto optionToOptionAcaDto(QuestionOptionEntity optionEntity) {
    return new QuestionOptionAcaDto(optionEntity.getId(), optionEntity.getIsCorrect(),
        optionEntity.getOption(), false);
  }

  /**
   * Maps a list of {@link QuestionOptionEntity} objects to a list of {@link QuestionOptionAcaDto} objects.
   *
   * @param optionEntities the list of {@link QuestionOptionEntity} objects to be mapped
   * @return a list of {@link QuestionOptionAcaDto} objects representing the mapped data
   */
  public static List<QuestionOptionAcaDto> optionsToOptionsAcaDto(
      List<QuestionOptionEntity> optionEntities) {
    return optionEntities.stream().map(OptionsMapper::optionToOptionAcaDto)
        .collect(Collectors.toList());
  }
}
