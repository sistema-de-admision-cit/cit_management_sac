package cr.co.ctpcit.citsacbackend.logic.mappers;


import cr.co.ctpcit.citsacbackend.data.entities.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;

import java.util.List;

public class ExamDayMapper {
    public static ExamDayDto convertToDto(ExamDayEntity examDay) {
        return new ExamDayDto(
                examDay.getExamDayId(),
                examDay.getExamPeriod().getExamPeriodId(),
                examDay.getExamDay(),
                examDay.getStartTime()
        );
    }

    public static ExamDayEntity convertToEntity(ExamDayDto examDayDTO) {
        ExamDayEntity examDay = new ExamDayEntity();
        examDay.setExamDayId(examDayDTO.examDayId());
        examDay.setStartTime(examDayDTO.startTime());
        examDay.setExamDay(examDayDTO.examDay());
        return examDay;
    }

    public static List<ExamDayDto> convertToDtoList(List<ExamDayEntity> examDays) {
        return examDays.stream()
                .map(ExamDayMapper::convertToDto)
                .toList();
    }
}