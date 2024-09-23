package cr.co.ctpcit.citsacbackend.logic.services.datesImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.ExamPeriodRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.config.ExamPeriodMapper;
import cr.co.ctpcit.citsacbackend.logic.services.ExamPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExamPeriodServiceImplementation implements ExamPeriodService {

  @Autowired
  private ExamPeriodRepository examPeriodRepository;

  @Autowired
  public ExamPeriodServiceImplementation(ExamPeriodRepository examPeriodRepository) {
    this.examPeriodRepository = examPeriodRepository;
  }

  @Override
  public List<ExamPeriodDto> getAllExamPeriods() {
    List<ExamPeriodEntity> entities = examPeriodRepository.findAll();
    return ExamPeriodMapper.convertToDtoList(entities);
  }

  @Override
  public ExamPeriodDto createExamPeriod(ExamPeriodDto dto) {
    ExamPeriodEntity examPeriod = ExamPeriodMapper.convertToEntity(dto);
    ExamPeriodEntity savedPeriod = examPeriodRepository.save(examPeriod);
    return ExamPeriodMapper.convertToDto(savedPeriod);
  }

  @Override
  public ExamPeriodDto updateExamPeriod(int id, ExamPeriodDto dto) {
    ExamPeriodEntity entity = examPeriodRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Exam Period not found with id " + id));

    entity.setStartDate(dto.startDate());
    entity.setEndDate(dto.endDate());

    ExamPeriodEntity updated = examPeriodRepository.save(entity);
    return ExamPeriodMapper.convertToDto(updated);
  }
}
