package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.ExamMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class ExamRepositoryTest {
  @Autowired
  private ExamRepository examRepository;

  @Autowired
  ObjectMapper mapper;

  private ExamEntity exam;

  @Test
  void shouldSaveAnExam() {
    exam = TestProvider.provideNonSavedExam();

    //save
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertEquals(exam.getExamDate(), saveExam.getExamDate());
  }

  @Test
  void shouldSaveAnEnglishExam() {
    exam = TestProvider.provideNonSavedExam();
    EnglishExamEntity englishExam = TestProvider.provideNonSavedEnglishExam();

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
    exam = TestProvider.provideNonSavedExam();
    DaiExamEntity daiExam = TestProvider.provideNonSavedDaiExam();

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
    exam = TestProvider.provideNonSavedExam();
    AcademicExamEntity academicExam = TestProvider.provideNonSavedAcademicExam();

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
  void saveExamWithResponses() throws JsonProcessingException {
    exam = TestProvider.provideNonSavedExam();
    Map<String, Object> responses = exam.getResponses();
    responses.put("exam", mapper.writeValueAsString(List.of(TestProvider.provideQuestionAcaDto())));

    //save
    ExamEntity saveExam = examRepository.save(exam);

    //assert
    assertNotNull(saveExam);
    assertNotNull(saveExam.getId());
    assertEquals(exam.getResponses(), saveExam.getResponses());
  }

  @Test
  void saveAcademicExam_Then_GetItAndMapItIntoAcademicExamDto() throws JsonProcessingException {
    exam = TestProvider.provideNonSavedExam();
    Map<String, Object> responses = exam.getResponses();
    responses.put("exam", mapper.writeValueAsString(List.of(TestProvider.provideQuestionAcaDto())));

    AcademicExamEntity academicExam = TestProvider.provideNonSavedAcademicExam();

    //save
    exam.addAcademicExam(academicExam);
    ExamEntity saveExam = examRepository.save(exam);

    //get
    AcademicExamDetailsDto academicExamDetailsDto = ExamMapper.academicExamToAcademicExamDetailsDto(exam);

    //assert
    assertThat(academicExamDetailsDto).isNotNull();
    assert academicExamDetailsDto != null;
    assertThat(academicExamDetailsDto.exam()).isNotNull();
    assertThat(academicExamDetailsDto.exam().responses()).isNotNull();
  }
}
