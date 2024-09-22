package cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;

import java.util.List;
import java.util.stream.Collectors;

public class DaiQuestionsMapper {
  public static DaiQuestionsDto toDto(DaiQuestionsEntity entity) {
    return new DaiQuestionsDto(entity.getId(), entity.getQuestionText(), entity.getQuestionGrade(),
        entity.getImageUrl());
  }

  public static List<DaiQuestionsDto> toDtoList(List<DaiQuestionsEntity> entities) {
    return entities.stream().map(DaiQuestionsMapper::toDto).collect(Collectors.toList());
  }

}
