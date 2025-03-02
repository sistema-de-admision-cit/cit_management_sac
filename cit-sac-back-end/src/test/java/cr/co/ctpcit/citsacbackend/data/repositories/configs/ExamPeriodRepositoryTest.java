package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class ExamPeriodRepositoryTest {

  @Autowired
  private ExamPeriodRepository examPeriodRepository;

  private ExamDayEntity examDayEntity;
  private ExamPeriodEntity examPeriodEntity;

  @BeforeEach
  void setUp() {
    examDayEntity = new ExamDayEntity();
    examDayEntity.setExamDay(WeekDays.M);
    examDayEntity.setStartTime(LocalTime.of(8, 0));

    examPeriodEntity = new ExamPeriodEntity();
    examPeriodEntity.setStartDate(LocalDate.of(2026, 1, 1));
    examPeriodEntity.setEndDate(LocalDate.of(2026, 3, 15));
  }

  @Test
  void testSaveExamPeriodEntity() {
    examPeriodEntity.addExamDay(examDayEntity);
    ExamPeriodEntity savedExamPeriodEntity = examPeriodRepository.save(examPeriodEntity);

    assertNotNull(savedExamPeriodEntity);
    assertNotNull(savedExamPeriodEntity.getId());
    assertEquals(examPeriodEntity, savedExamPeriodEntity);
    assertNotNull(savedExamPeriodEntity.getExamDays());
    assertFalse(savedExamPeriodEntity.getExamDays().isEmpty());
    assertNotNull(examDayEntity.getExamPeriod());
  }

  @Test
  void testFindExamPeriodEntityById() {
    examPeriodEntity.addExamDay(examDayEntity);
    ExamPeriodEntity savedExamPeriodEntity = examPeriodRepository.save(examPeriodEntity);
    ExamPeriodEntity foundExamPeriodEntity =
        examPeriodRepository.findById(savedExamPeriodEntity.getId()).orElse(null);

    assertNotNull(foundExamPeriodEntity);
    assertEquals(savedExamPeriodEntity, foundExamPeriodEntity);
    assertNotNull(foundExamPeriodEntity.getExamDays());
    assertFalse(foundExamPeriodEntity.getExamDays().isEmpty());
    assertNotNull(examDayEntity.getExamPeriod());
  }

  @Test
  void testFindByYear() {
    examPeriodEntity.addExamDay(examDayEntity);
    examPeriodRepository.save(examPeriodEntity);
    List<ExamPeriodEntity> foundExamPeriodEntity = examPeriodRepository.findByYear(2026);

    assertNotNull(foundExamPeriodEntity);
    assertFalse(foundExamPeriodEntity.isEmpty());
    assertEquals(examPeriodEntity, foundExamPeriodEntity.getFirst());
    assertNotNull(foundExamPeriodEntity.getFirst().getExamDays());
  }
}
