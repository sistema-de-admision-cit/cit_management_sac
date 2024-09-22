package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;

import java.util.List;

public interface ExamPeriodService {
  List<ExamPeriodDto> getAllExamPeriods();

  ExamPeriodDto createExamPeriod(ExamPeriodDto dto);

  ExamPeriodDto updateExamPeriod(int id, ExamPeriodDto dto);
}
