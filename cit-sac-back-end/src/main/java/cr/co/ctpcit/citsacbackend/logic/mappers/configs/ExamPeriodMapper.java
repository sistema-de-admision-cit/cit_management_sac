package cr.co.ctpcit.citsacbackend.logic.mappers.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for converting between {@link ExamPeriodDto}, {@link ExamDayDto} and their corresponding
 * entity representations, {@link ExamPeriodEntity} and {@link ExamDayEntity}.
 * This class provides methods for mapping data between DTOs and entities related to exam periods and exam days.
 */
public class ExamPeriodMapper {

  /**
   * A utility class for converting between {@link ExamPeriodDto}, {@link ExamDayDto} and their corresponding
   * entity representations, {@link ExamPeriodEntity} and {@link ExamDayEntity}.
   * This class provides methods for mapping data between DTOs and entities related to exam periods and exam days.
   */
  public static ExamPeriodDto toDto(ExamPeriodEntity entity) {
    return ExamPeriodDto.builder().id(entity.getId()).startDate(entity.getStartDate())
        .endDate(entity.getEndDate()).examDays(daysToDtoList(entity.getExamDays())).build();
  }

  /**
   * Converts a list of {@link ExamPeriodEntity} objects to a list of {@link ExamPeriodDto} objects.
   *
   * @param entities the list of {@link ExamPeriodEntity} objects to be converted
   * @return a list of {@link ExamPeriodDto} objects
   */
  public static List<ExamPeriodDto> periodsToDtoList(List<ExamPeriodEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::toDto).collect(Collectors.toList());
  }

  /**
   * Converts an {@link ExamDayEntity} to an {@link ExamDayDto}.
   *
   * @param entity the {@link ExamDayEntity} object to be converted
   * @return the corresponding {@link ExamDayDto} object
   */
  public static ExamDayDto toDto(ExamDayEntity entity) {
    return ExamDayDto.builder().id(entity.getId()).examDay(entity.getExamDay())
        .startTime(entity.getStartTime()).build();
  }

  /**
   * Converts a list of {@link ExamDayEntity} objects to a list of {@link ExamDayDto} objects.
   *
   * @param entities the list of {@link ExamDayEntity} objects to be converted
   * @return a list of {@link ExamDayDto} objects
   */
  public static List<ExamDayDto> daysToDtoList(List<ExamDayEntity> entities) {
    return entities.stream().map(ExamPeriodMapper::toDto).collect(Collectors.toList());
  }

  /**
   * Converts an {@link ExamPeriodDto} to an {@link ExamPeriodEntity}.
   *
   * @param examPeriodDto the {@link ExamPeriodDto} object to be converted
   * @return the corresponding {@link ExamPeriodEntity} object
   */
  public static ExamPeriodEntity toEntity(ExamPeriodDto examPeriodDto) {
    return ExamPeriodEntity.builder().id(examPeriodDto.id()).startDate(examPeriodDto.startDate())
        .endDate(examPeriodDto.endDate()).build();
  }

  /**
   * Converts an {@link ExamDayDto} to an {@link ExamDayEntity}.
   *
   * @param examDayDto the {@link ExamDayDto} object to be converted
   * @return the corresponding {@link ExamDayEntity} object
   */
  public static ExamDayEntity toEntity(ExamDayDto examDayDto) {
    return ExamDayEntity.builder().id(examDayDto.id()).examDay(examDayDto.examDay())
        .startTime(examDayDto.startTime()).build();
  }

  /**
   * Converts a list of {@link ExamDayDto} objects to a list of {@link ExamDayEntity} objects.
   *
   * @param examDays the list of {@link ExamDayDto} objects to be converted
   * @return a list of {@link ExamDayEntity} objects
   */
  public static List<ExamDayEntity> daysDtoToEntityList(List<ExamDayDto> examDays) {
    return examDays.stream().map(ExamPeriodMapper::toEntity).collect(Collectors.toList());
  }
}
