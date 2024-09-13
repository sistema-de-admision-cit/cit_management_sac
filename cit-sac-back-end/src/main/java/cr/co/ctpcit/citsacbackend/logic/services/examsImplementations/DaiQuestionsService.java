package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.repositories.DaiQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DaiQuestionsService {

    @Autowired
    private DaiQuestionsRepository daiQuestionsRepository;

    public List<DaiQuestionsEntity> obtenerTodasLasPreguntas() {
        try {
            return daiQuestionsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las preguntas de DAI", e);
        }
    }

    public DaiQuestionsEntity obtenerPreguntaPorId(Integer id) {
        return daiQuestionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));
    }

    public DaiQuestionsEntity modificarPregunta(Integer id, String questionText, Grades questionGrade, String imageUrl) {
        DaiQuestionsEntity preguntaExistente = obtenerPreguntaPorId(id);

        preguntaExistente.setQuestionText(questionText);
        preguntaExistente.setQuestionGrade(questionGrade);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            preguntaExistente.setImageUrl(imageUrl);
        }

        return daiQuestionsRepository.save(preguntaExistente);
    }

    public void eliminarPregunta(Integer id) {
        DaiQuestionsEntity pregunta = obtenerPreguntaPorId(id);
        daiQuestionsRepository.delete(pregunta);
    }

}
