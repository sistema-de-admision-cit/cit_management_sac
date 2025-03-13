package cr.co.ctpcit.citsacbackend.logic.mappers.exams;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.QuestionOptionAcaDto;

import java.util.List;
import java.util.stream.Collectors;

public class OptionsMapper {
  public static QuestionOptionAcaDto optionToOptionAcaDto(QuestionOptionEntity optionEntity) {
    return new QuestionOptionAcaDto(optionEntity.getId(), optionEntity.getIsCorrect(),
        optionEntity.getOption(), false);
  }

  public static List<QuestionOptionAcaDto> optionsToOptionsAcaDto(
      List<QuestionOptionEntity> optionEntities) {
    return optionEntities.stream().map(OptionsMapper::optionToOptionAcaDto)
        .collect(Collectors.toList());
  }
}
