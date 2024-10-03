package cr.co.ctpcit.citsacbackend.logic.services.datesImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.config.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import cr.co.ctpcit.citsacbackend.data.repositories.config.ExamDayRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.config.ExamPeriodRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ConfigDateDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.config.ExamPeriodMapper;
import cr.co.ctpcit.citsacbackend.logic.services.config.ExamPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExamPeriodServiceImplementation implements ExamPeriodService {
  private ExamPeriodRepository examPeriodRepository;
  private ExamDayRepository examDayRepository;

  @Autowired
  public ExamPeriodServiceImplementation(ExamPeriodRepository examPeriodRepository,
      ExamDayRepository examDayRepository) {
    this.examPeriodRepository = examPeriodRepository;
    this.examDayRepository = examDayRepository;
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

  @Override
  public Boolean addExamApplicationDays(ConfigDateDTO configDateDTO) {
    // año actual
    LocalDate startOfThisYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
    LocalDate endOfThisYear = LocalDate.of(LocalDate.now().getYear(), 12, 31);

    // Formato para las fechas
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Definir las fechas basadas en allYear
    LocalDate startDate = configDateDTO.allYear() ?
        startOfThisYear :
        configDateDTO.startDate() != null ?
            LocalDate.parse(configDateDTO.startDate(), formatter) :
            startOfThisYear;

    LocalDate endDate = configDateDTO.allYear() ?
        endOfThisYear :
        configDateDTO.endDate() != null ?
            LocalDate.parse(configDateDTO.endDate(), formatter) :
            endOfThisYear;

    // Crear el nuevo periodo de examen
    ExamPeriodEntity examPeriod = new ExamPeriodEntity();
    examPeriod.setStartDate(java.sql.Date.valueOf(startDate));
    examPeriod.setEndDate(java.sql.Date.valueOf(endDate));

    // Guardar el nuevo periodo de examen
    ExamPeriodEntity savedPeriod = examPeriodRepository.save(examPeriod);

    // Obtener los días de la semana de aplicación en ENUM format
    final List<WeekDays> applicationDays = WeekDays.getWeekDays(configDateDTO.applicationDays());

    final String startTime = configDateDTO.startTime();

    // Guardar los días de aplicación
    for (WeekDays day : applicationDays) {
      ExamDayEntity examDay = new ExamDayEntity();
      examDay.setExamPeriod(savedPeriod);
      examDay.setExamDay(day);
      examDay.setStartTime(startTime);

      // Guardar el día de examen
      examDayRepository.save(examDay);
    }

    return true;
  }
}
