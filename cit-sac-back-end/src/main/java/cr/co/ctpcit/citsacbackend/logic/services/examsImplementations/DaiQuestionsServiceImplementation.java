package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.DaiExamQuestionsRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.DaiQuestionsRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai.DaiQuestionsMapper;
import cr.co.ctpcit.citsacbackend.logic.services.DaiQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DaiQuestionsServiceImplementation implements DaiQuestionsService {

  @Autowired
  private DaiQuestionsRepository daiQuestionsRepository;

  @Autowired
  private DaiExamQuestionsRepository daiExamQuestionsRepository;

  @Autowired
  public DaiQuestionsServiceImplementation(DaiQuestionsRepository repository) {
    this.daiQuestionsRepository = repository;
  }

  @Override
  public List<DaiQuestionsDto> obtenerTodasLasPreguntas() {
    List<DaiQuestionsEntity> entities = daiQuestionsRepository.findAll();
    return DaiQuestionsMapper.toDtoList(entities);
  }

  @Override
  public DaiQuestionsDto obtenerPreguntaPorId(Integer id) {
    DaiQuestionsEntity entity = daiQuestionsRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));
    return DaiQuestionsMapper.toDto(entity);
  }

  @Override
  public List<DaiQuestionsDto> obtenerPreguntasPorQuestionText(String questionText) {
    List<DaiQuestionsEntity> entities =
        daiQuestionsRepository.findByQuestionTextContaining(questionText);
    if (entities.isEmpty()) {
      throw new NoSuchElementException(
          "No se encontraron preguntas que contengan: " + questionText);
    }
    return entities.stream().map(DaiQuestionsMapper::toDto).collect(Collectors.toList());
  }

  @Override
  public void eliminarPregunta(Integer questionId) {
    if (daiExamQuestionsRepository.existsByQuestionId(questionId)) {
      throw new IllegalStateException(
          "No se puede eliminar la pregunta porque está asociada a un examen.");
    }
    daiQuestionsRepository.deleteById(questionId);
  }

  @Override
  public DaiQuestionsDto modificarPregunta(Integer id, DaiQuestionsDto preguntaDto) {
    DaiQuestionsEntity entity = daiQuestionsRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));

    entity.setQuestionText(preguntaDto.questionText());
    entity.setQuestionGrade(preguntaDto.questionGrade());
    entity.setImageUrl(preguntaDto.imageUrl());

    DaiQuestionsEntity updatedEntity = daiQuestionsRepository.save(entity);
    return DaiQuestionsMapper.toDto(updatedEntity);
  }
}
