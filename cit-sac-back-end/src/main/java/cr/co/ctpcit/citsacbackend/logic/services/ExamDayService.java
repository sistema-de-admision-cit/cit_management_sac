package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;

import java.util.List;

public interface ExamDayService {
  List<ExamDayDto> getAllExamDays();

  ExamDayDto createExamDay(ExamDayDto dto);

  ExamDayDto updateExamDay(int id, ExamDayDto dto);
}
