package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;

import cr.co.ctpcit.citsacbackend.data.repositories.AcademicExamQuestionRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.AcademicQuestionsRepository;


import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;

import cr.co.ctpcit.citsacbackend.logic.mappers.exams.academic.AcademicQuestionsMapper;
import cr.co.ctpcit.citsacbackend.logic.services.AcademicQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AcademicQuestionsServiceImplementation implements AcademicQuestionsService {

    @Autowired
    private AcademicQuestionsRepository academicQuestionsRepository;

    @Autowired
    private AcademicExamQuestionRepository academicExamQuestionsRepository;

    @Autowired
    public AcademicQuestionsServiceImplementation(AcademicQuestionsRepository repository) {
        this.academicQuestionsRepository = repository;
    }

    @Override
    public List<AcademicQuestionsDto> obtenerTodasLasPreguntas() {
        List<AcademicQuestionsEntity> entities = academicQuestionsRepository.findAll();
        return AcademicQuestionsMapper.toDtoList(entities);
    }

    @Override
    public AcademicQuestionsDto obtenerPreguntaPorId(Integer id) {
        AcademicQuestionsEntity entity = academicQuestionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));
        return AcademicQuestionsMapper.toDto(entity);
    }

    @Override
    public void eliminarPregunta(Integer questionId) {
        if (academicExamQuestionsRepository.existsByQuestionId(questionId)) {
            throw new IllegalStateException("No se puede eliminar la pregunta porque está asociada a un examen.");
        }
        academicQuestionsRepository.deleteById(questionId);
    }

    @Override
    public AcademicQuestionsDto modificarPregunta(Integer id, AcademicQuestionsDto preguntaDto) {
        AcademicQuestionsEntity entity = academicQuestionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));

        entity.setQuestionText(preguntaDto.questionText());
        entity.setQuestionGrade(preguntaDto.questionGrade());
        entity.setOptionA(preguntaDto.option_A());
        entity.setOptionB(preguntaDto.option_B());
        entity.setOptionC(preguntaDto.option_C());
        entity.setOptionD(preguntaDto.option_D());
        entity.setCorrectOption(preguntaDto.correctOption());
        entity.setImageUrl(preguntaDto.imageUrl());

        AcademicQuestionsEntity updatedEntity = academicQuestionsRepository.save(entity);
        return AcademicQuestionsMapper.toDto(updatedEntity);
    }

}