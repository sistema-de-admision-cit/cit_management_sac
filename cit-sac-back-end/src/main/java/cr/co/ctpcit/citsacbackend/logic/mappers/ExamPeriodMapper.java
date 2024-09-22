package cr.co.ctpcit.citsacbackend.logic.mappers;



import cr.co.ctpcit.citsacbackend.data.entities.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;

import java.util.List;

/**
 * Mapper for {@link ExamPeriodEntity} This class is used to convert an {@link ExamPeriodEntity} to
 * an {@link ExamPeriodDto}
 */

public class ExamPeriodMapper {
  public static ExamPeriodDto convertToDto(ExamPeriodEntity examPeriod) {
    return new ExamPeriodDto(examPeriod.getExamPeriodId(), examPeriod.getStartDate(),
        examPeriod.getEndDate());
  }

  public static ExamPeriodEntity convertToEntity(ExamPeriodDto examPeriodDTO) {
    ExamPeriodEntity examPeriod = new ExamPeriodEntity();
    examPeriod.setExamPeriodId(examPeriodDTO.examPeriodId());
    examPeriod.setStartDate(examPeriodDTO.startDate());
    examPeriod.setEndDate(examPeriodDTO.endDate());
    return examPeriod;
  }

  public static List<ExamPeriodDto> convertToDtoList(List<ExamPeriodEntity> examPeriods) {
    return examPeriods.stream().map(ExamPeriodMapper::convertToDto).toList();
  }
}
