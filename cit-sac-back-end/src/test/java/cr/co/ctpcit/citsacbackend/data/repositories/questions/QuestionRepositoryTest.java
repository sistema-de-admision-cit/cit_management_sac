package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class QuestionRepositoryTest {
  @Autowired
  private QuestionRepository questionRepository;

  private QuestionEntity question;

  @BeforeEach
  void setUp() {
    question = new QuestionEntity();
    question.setQuestionType(QuestionType.ACA);
    question.setQuestionText("Si se suma 11 más 5, existe un escenario donde el resultado es 4.");
    question.setImageUrl(null);
    question.setQuestionGrade(Grades.EIGHTH);
    question.setQuestionLevel(QuestionLevel.HARD);
    question.setSelectionType(SelectionType.SINGLE);
    question.setDeleted(false);
  }

  @Test
  @Transactional
  @Rollback
  @Order(1)
  void testSaveQuestionEntity() {
    QuestionEntity savedQuestion = questionRepository.save(question);

    assertNotNull(savedQuestion);
    assertNotNull(savedQuestion.getId());
    assertEquals(question, savedQuestion);
  }

  @Test
  @Order(2)
  void testSaveOptionsInCascade() {
    QuestionOptionEntity option1 = new QuestionOptionEntity();
    option1.setIsCorrect(false);
    option1.setOption("Falso");

    QuestionOptionEntity option2 = new QuestionOptionEntity();
    option2.setIsCorrect(true);
    option2.setOption("Verdadero");

    question.addQuestionOption(option1);
    question.addQuestionOption(option2);

    QuestionEntity savedQuestion = questionRepository.save(question);

    assertNotNull(savedQuestion);
    assertNotNull(savedQuestion.getId());
    assertEquals(question, savedQuestion);
    assertNotNull(savedQuestion.getQuestionOptions());
    assertEquals(2, savedQuestion.getQuestionOptions().size());
  }

}
