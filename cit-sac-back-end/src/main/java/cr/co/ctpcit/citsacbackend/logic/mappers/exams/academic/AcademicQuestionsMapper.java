package cr.co.ctpcit.citsacbackend.logic.mappers.exams.academic;


import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;

import java.util.List;
import java.util.stream.Collectors;

public class AcademicQuestionsMapper {
  public static AcademicQuestionsDto toDto(AcademicQuestionsEntity entity) {
    return new AcademicQuestionsDto(entity.getId(), entity.getQuestionText(),
        entity.getQuestionGrade(), entity.getOptionA(), entity.getOptionB(), entity.getOptionC(),
        entity.getOptionD(), entity.getCorrectOption(), entity.getImageUrl());
  }

  public static List<AcademicQuestionsDto> toDtoList(List<AcademicQuestionsEntity> entities) {
    return entities.stream().map(AcademicQuestionsMapper::toDto).collect(Collectors.toList());
  }

}
