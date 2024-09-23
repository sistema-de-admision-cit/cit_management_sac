package cr.co.ctpcit.citsacbackend.logic.services.datesImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.config.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.config.ExamDayRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.config.ExamPeriodRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.config.ExamDayMapper;
import cr.co.ctpcit.citsacbackend.logic.services.config.ExamDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExamDayServiceImplementation implements ExamDayService {
  @Autowired
  private final ExamDayRepository examDayRepository;
  @Autowired
  private final ExamPeriodRepository examPeriodRepository;

  @Autowired
  public ExamDayServiceImplementation(ExamDayRepository examDayRepository,
      ExamPeriodRepository examPeriodRepository) {
    this.examDayRepository = examDayRepository;
    this.examPeriodRepository = examPeriodRepository;
  }

  @Override
  public List<ExamDayDto> getAllExamDays() {
    List<ExamDayEntity> entities = examDayRepository.findAll();
    return ExamDayMapper.convertToDtoList(entities);
  }

  @Override
  public ExamDayDto createExamDay(ExamDayDto dto) {
    ExamPeriodEntity examPeriod = examPeriodRepository.findById(dto.examPeriodId()).orElseThrow(
        () -> new NoSuchElementException("Exam Period not found with id " + dto.examPeriodId()));
    ExamDayEntity examDay = ExamDayMapper.convertToEntity(dto);
    examDay.setExamPeriod(examPeriod);
    ExamDayEntity savedDay = examDayRepository.save(examDay);
    return ExamDayMapper.convertToDto(savedDay);
  }

  @Override
  public ExamDayDto updateExamDay(int id, ExamDayDto dto) {
    ExamDayEntity entity = examDayRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Exam Day not found with id " + id));

    ExamPeriodEntity examPeriod = examPeriodRepository.findById(dto.examPeriodId()).orElseThrow(
        () -> new NoSuchElementException("Exam Period not found with id " + dto.examPeriodId()));

    entity.setExamPeriod(examPeriod);
    entity.setExamDay(dto.examDay());
    entity.setStartTime(dto.startTime());

    ExamDayEntity updated = examDayRepository.save(entity);
    return ExamDayMapper.convertToDto(updated);
  }
}
