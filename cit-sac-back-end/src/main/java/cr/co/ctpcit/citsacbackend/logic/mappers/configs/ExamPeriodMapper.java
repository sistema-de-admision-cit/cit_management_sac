package cr.co.ctpcit.citsacbackend.logic.mappers.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExamPeriodMapper {
  public static ExamPeriodDto examPeriodDto(ExamPeriodEntity entity) {
    return ExamPeriodDto.builder().id(entity.getId()).startDate(entity.getStartDate())
        .endDate(entity.getEndDate()).examDays(examDayToDtoList(entity.getExamDays())).build();
  }

  public static List<ExamPeriodDto> examPeriodToDtoList(List<ExamPeriodEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::examPeriodDto).collect(Collectors.toList());
  }

  public static ExamDayDto examDayToDto(ExamDayEntity entity) {
    return ExamDayDto.builder().id(entity.getId()).examDay(entity.getExamDay())
        .startTime(entity.getStartTime()).build();
  }

  public static List<ExamDayDto> examDayToDtoList(List<ExamDayEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::examDayToDto).collect(Collectors.toList());
  }
}
