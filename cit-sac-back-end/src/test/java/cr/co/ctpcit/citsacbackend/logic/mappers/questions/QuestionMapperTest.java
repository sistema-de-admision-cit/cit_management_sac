package cr.co.ctpcit.citsacbackend.logic.mappers.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionOptionDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {

  @Test
  public void testEntityToDto_NullEntity() {
    // When passing a null QuestionEntity, the result should be null.
    assertNull(QuestionMapper.entityToDto(null));
  }

  @Test
  public void testDtoToEntity_NullDto() {
    // When passing a null QuestionDto, the result should be null.
    assertNull(QuestionMapper.dtoToEntity(null));
  }

  @Test
  public void testEntityToDtoMapping() {
    QuestionOptionEntity optionA =
        QuestionOptionEntity.builder().id(100L).isCorrect(true).option("Option A").build();

    QuestionOptionEntity optionB =
        QuestionOptionEntity.builder().id(101L).isCorrect(false).option("Option B").build();

    List<QuestionOptionEntity> options = Arrays.asList(optionA, optionB);

    QuestionEntity questionEntity = QuestionEntity.builder().id(1L).questionType(QuestionType.ACA)
        .questionText("Question Text Test").imageUrl("/home/questions/01.jpg")
        .questionGrade(Grades.EIGHTH).questionLevel(QuestionLevel.MEDIUM)
        .selectionType(SelectionType.SINGLE).deleted(false).build();

    questionEntity.setQuestionOptions(options);

    QuestionDto questionDto = QuestionMapper.entityToDto(questionEntity);

    // all fields are supposed to be copied from the entity to the dto
    assertNotNull(questionDto);
    assertEquals(questionEntity.getId(), questionDto.id());
    assertEquals(questionEntity.getQuestionType(), questionDto.questionType());
    assertEquals(questionEntity.getQuestionText(), questionDto.questionText());
    assertEquals(questionEntity.getImageUrl(), questionDto.imageUrl());
    assertEquals(questionEntity.getQuestionGrade(), questionDto.questionGrade());
    assertEquals(questionEntity.getQuestionLevel(), questionDto.questionLevel());
    assertEquals(questionEntity.getSelectionType(), questionDto.selectionType());
    assertEquals(questionEntity.getDeleted(), questionDto.deleted());

    assertNotNull(questionDto.questionOptions());
    assertEquals(options.size(), questionDto.questionOptions().size());
    for (int i = 0; i < options.size(); i++) {
      QuestionOptionEntity entityOption = options.get(i);
      QuestionOptionDto dtoOption = questionDto.questionOptions().get(i);
      assertEquals(entityOption.getId(), dtoOption.id());
      assertEquals(entityOption.getIsCorrect(), dtoOption.isCorrect());
      assertEquals(entityOption.getOption(), dtoOption.option());
    }
  }

  @Test
  public void testDtoToEntityMapping() {
    // Create sample QuestionOptionDto objects.
    QuestionOptionDto optionDto1 = new QuestionOptionDto(200L, true, "Option A");
    QuestionOptionDto optionDto2 = new QuestionOptionDto(201L, false, "Option B");
    List<QuestionOptionDto> optionDtos = Arrays.asList(optionDto1, optionDto2);

    // Create a sample QuestionDto.
    QuestionDto questionDto =
        new QuestionDto(2L, QuestionType.ACA, "What is 2+2?", "http://example.com/math.png",
            Grades.THIRD, QuestionLevel.MEDIUM, SelectionType.MULTIPLE, false, optionDtos);

    // Convert the DTO to an entity.
    QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);

    // Verify that all fields are mapped correctly.
    assertNotNull(questionEntity);
    assertEquals(questionDto.id(), questionEntity.getId());
    assertEquals(questionDto.questionType(), questionEntity.getQuestionType());
    assertEquals(questionDto.questionText(), questionEntity.getQuestionText());
    assertEquals(questionDto.imageUrl(), questionEntity.getImageUrl());
    assertEquals(questionDto.questionGrade(), questionEntity.getQuestionGrade());
    assertEquals(questionDto.questionLevel(), questionEntity.getQuestionLevel());
    assertEquals(questionDto.selectionType(), questionEntity.getSelectionType());
    assertEquals(questionDto.deleted(), questionEntity.getDeleted());

    // Verify that the options are mapped correctly.
    List<QuestionOptionEntity> entityOptions = questionEntity.getQuestionOptions();
    assertNotNull(entityOptions);
    assertEquals(optionDtos.size(), entityOptions.size());
    for (int i = 0; i < entityOptions.size(); i++) {
      QuestionOptionEntity optionEntity = entityOptions.get(i);
      QuestionOptionDto optionDto = optionDtos.get(i);
      assertEquals(optionDto.id(), optionEntity.getId());
      assertEquals(optionDto.isCorrect(), optionEntity.getIsCorrect());
      assertEquals(optionDto.option(), optionEntity.getOption());
      // Ensure the parent QuestionEntity is correctly associated.
      assertEquals(questionEntity, optionEntity.getQuestion());
    }
  }

  @Test
  public void testDtoListToEntityListMapping() {
    // Create two sample QuestionDto objects with options.
    QuestionOptionDto optionDto1 = new QuestionOptionDto(300L, true, "Yes");
    QuestionOptionDto optionDto2 = new QuestionOptionDto(301L, false, "No");

    QuestionDto questionDto1 =
        new QuestionDto(3L, QuestionType.ACA, "Is the sky blue?", null, Grades.FIRST,
            QuestionLevel.EASY, SelectionType.SINGLE, false, Collections.singletonList(optionDto1));

    QuestionDto questionDto2 =
        new QuestionDto(4L, QuestionType.ACA, "Select the colors:", "http://example.com/colors.png",
            Grades.FOURTH, QuestionLevel.MEDIUM, SelectionType.MULTIPLE, false,
            Collections.singletonList(optionDto2));

    List<QuestionDto> dtoList = Arrays.asList(questionDto1, questionDto2);

    // Convert the list of DTOs to a list of entities.
    List<QuestionEntity> entityList = QuestionMapper.dtoListToEntityList(dtoList);

    // Verify that the list sizes match and that each element is correctly mapped.
    assertNotNull(entityList);
    assertEquals(dtoList.size(), entityList.size());

    for (int i = 0; i < dtoList.size(); i++) {
      QuestionDto dto = dtoList.get(i);
      QuestionEntity entity = entityList.get(i);
      assertEquals(dto.id(), entity.getId());
      assertEquals(dto.questionType(), entity.getQuestionType());
      assertEquals(dto.questionText(), entity.getQuestionText());
      assertEquals(dto.imageUrl(), entity.getImageUrl());
      assertEquals(dto.questionGrade(), entity.getQuestionGrade());
      assertEquals(dto.questionLevel(), entity.getQuestionLevel());
      assertEquals(dto.selectionType(), entity.getSelectionType());
      assertEquals(dto.deleted(), entity.getDeleted());

      // Check the mapping of options.
      assertEquals(dto.questionOptions().size(), entity.getQuestionOptions().size());
      for (int j = 0; j < dto.questionOptions().size(); j++) {
        QuestionOptionDto optionDto = dto.questionOptions().get(j);
        QuestionOptionEntity optionEntity = entity.getQuestionOptions().get(j);
        assertEquals(optionDto.id(), optionEntity.getId());
        assertEquals(optionDto.isCorrect(), optionEntity.getIsCorrect());
        assertEquals(optionDto.option(), optionEntity.getOption());
        assertEquals(entity, optionEntity.getQuestion());
      }
    }
  }

  @Test
  public void testEntityListToDtoListMapping() {
    // Create a sample QuestionEntity with options.
    QuestionOptionEntity optionEntity1 =
        QuestionOptionEntity.builder().id(400L).isCorrect(false).option("False").build();
    QuestionOptionEntity optionEntity2 =
        QuestionOptionEntity.builder().id(401L).isCorrect(true).option("True").build();
    List<QuestionOptionEntity> options = Arrays.asList(optionEntity1, optionEntity2);

    QuestionEntity questionEntity1 = QuestionEntity.builder().id(5L).questionType(QuestionType.ACA)
        .questionText("Is Java platform independent?").imageUrl(null).questionGrade(Grades.SECOND)
        .questionLevel(QuestionLevel.MEDIUM).selectionType(SelectionType.SINGLE).deleted(false)
        .build();
    questionEntity1.setQuestionOptions(options);

    // Create another QuestionEntity with an empty options list.
    QuestionEntity questionEntity2 = QuestionEntity.builder().id(6L).questionType(QuestionType.ACA)
        .questionText("Explain polymorphism.").imageUrl(null).questionGrade(Grades.SIXTH)
        .questionLevel(QuestionLevel.HARD).selectionType(SelectionType.PARAGRAPH).deleted(false)
        .build();
    questionEntity2.setQuestionOptions(Collections.emptyList());

    List<QuestionEntity> entityList = Arrays.asList(questionEntity1, questionEntity2);

    // Convert the list of entities to a list of DTOs.
    List<QuestionDto> dtoList = QuestionMapper.entityListToDtoList(entityList);

    // Verify the mapping for each QuestionEntity.
    assertNotNull(dtoList);
    assertEquals(entityList.size(), dtoList.size());

    // Check mapping for the first entity.
    QuestionDto dto1 = dtoList.get(0);
    assertEquals(questionEntity1.getId(), dto1.id());
    assertEquals(questionEntity1.getQuestionType(), dto1.questionType());
    assertEquals(questionEntity1.getQuestionText(), dto1.questionText());
    assertEquals(questionEntity1.getImageUrl(), dto1.imageUrl());
    assertEquals(questionEntity1.getQuestionGrade(), dto1.questionGrade());
    assertEquals(questionEntity1.getQuestionLevel(), dto1.questionLevel());
    assertEquals(questionEntity1.getSelectionType(), dto1.selectionType());
    assertEquals(questionEntity1.getDeleted(), dto1.deleted());
    assertEquals(questionEntity1.getQuestionOptions().size(), dto1.questionOptions().size());
    for (int i = 0; i < questionEntity1.getQuestionOptions().size(); i++) {
      QuestionOptionEntity optionEntity = questionEntity1.getQuestionOptions().get(i);
      QuestionOptionDto optionDto = dto1.questionOptions().get(i);
      assertEquals(optionEntity.getId(), optionDto.id());
      assertEquals(optionEntity.getIsCorrect(), optionDto.isCorrect());
      assertEquals(optionEntity.getOption(), optionDto.option());
    }

    // Check mapping for the second entity with no options.
    QuestionDto dto2 = dtoList.get(1);
    assertEquals(questionEntity2.getId(), dto2.id());
    assertNotNull(dto2.questionOptions());
    assertTrue(dto2.questionOptions().isEmpty());
  }
}
