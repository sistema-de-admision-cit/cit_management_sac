package cr.co.ctpcit.citsacbackend.logic.mappers.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;

import java.util.List;
import java.util.stream.Collectors;

public class ExamPeriodMapper {
  public static ExamPeriodDto toDto(ExamPeriodEntity entity) {
    return ExamPeriodDto.builder().id(entity.getId()).startDate(entity.getStartDate())
        .endDate(entity.getEndDate()).examDays(daysToDtoList(entity.getExamDays())).build();
  }

  public static List<ExamPeriodDto> periodsToDtoList(List<ExamPeriodEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::toDto).collect(Collectors.toList());
  }

  public static ExamDayDto toDto(ExamDayEntity entity) {
    return ExamDayDto.builder().id(entity.getId()).examDay(entity.getExamDay())
        .startTime(entity.getStartTime()).build();
  }

  public static List<ExamDayDto> daysToDtoList(List<ExamDayEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::toDto).collect(Collectors.toList());
  }

  public static ExamPeriodEntity toEntity(ExamPeriodDto examPeriodDto) {
    return ExamPeriodEntity.builder().id(examPeriodDto.id()).startDate(examPeriodDto.startDate())
        .endDate(examPeriodDto.endDate()).build();
  }

  public static ExamDayEntity toEntity(ExamDayDto examDayDto) {
    return ExamDayEntity.builder().id(examDayDto.id()).examDay(examDayDto.examDay())
        .startTime(examDayDto.startTime()).build();
  }

  public static List<ExamDayEntity> daysDtoToEntityList(List<ExamDayDto> examDays) {
    return examDays.stream().map(ExamPeriodMapper::toEntity).collect(Collectors.toList());
  }
}
