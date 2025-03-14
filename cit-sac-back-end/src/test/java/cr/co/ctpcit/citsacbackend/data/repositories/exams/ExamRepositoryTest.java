package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class ExamRepositoryTest {
  @Autowired
  private ExamRepository examRepository;

  private ExamEntity exam;

  @Test
  void shouldSaveAnExam() {
    exam = TestProvider.provideExam();

    //save
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertEquals(exam.getExamDate(), saveExam.getExamDate());
  }

  @Test
  void shouldSaveAnEnglishExam() {
    exam = TestProvider.provideExam();
    EnglishExamEntity englishExam = TestProvider.provideEnglishExam();

    //save
    exam.addEnglishExam(englishExam);
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertNotNull(saveExam.getEnglishExam());
    assertEquals(englishExam.getExam(), saveExam);
  }

  @Test
  void shouldSaveADaiExam() {
    exam = TestProvider.provideExam();
    DaiExamEntity daiExam = TestProvider.provideDaiExam();

    //save
    exam.addDaiExam(daiExam);
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertNotNull(saveExam.getDaiExam());
    assertEquals(daiExam.getExam(), saveExam);
  }

  @Test
  void shouldSaveAnAcademicExam() {
    exam = TestProvider.provideExam();
    AcademicExamEntity academicExam = TestProvider.provideAcademicExam();

    //save
    exam.addAcademicExam(academicExam);
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertNotNull(saveExam.getAcademicExam());
    assertEquals(academicExam.getExam(), saveExam);
  }

  @Test
  void saveExamWithResponses() {
    exam = TestProvider.provideExam();
    Map<String, Object> responses = exam.getResponses();
    responses.put("exam", List.of(TestProvider.provideQuestionAcaDto()));

    //save
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertEquals(exam.getResponses(), saveExam.getResponses());
  }
}
