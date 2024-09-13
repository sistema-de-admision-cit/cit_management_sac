package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.repositories.AcademicQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AcademicQuestionsService {

    @Autowired
    private AcademicQuestionsRepository academicQuestionsRepository;

    public List<AcademicQuestionsEntity> obtenerTodasLasPreguntas() {
        try {
            return academicQuestionsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las preguntas", e);
        }
    }

    public AcademicQuestionsEntity modificarPregunta(Integer id, String questionText, Grades questionGrade,
                                                     String imageUrl, String optionA, String optionB, String optionC,
                                                     String optionD, String correctOption) {
        AcademicQuestionsEntity preguntaExistente = academicQuestionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));

        preguntaExistente.setQuestionText(questionText);
        preguntaExistente.setQuestionGrade(questionGrade);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            preguntaExistente.setImageUrl(imageUrl);
        }

        preguntaExistente.setOptionA(optionA);
        preguntaExistente.setOptionB(optionB);
        preguntaExistente.setOptionC(optionC);
        preguntaExistente.setOptionD(optionD);
        preguntaExistente.setCorrectOption(correctOption);

        return academicQuestionsRepository.save(preguntaExistente);
    }


    public void eliminarPregunta(Integer id) {
        AcademicQuestionsEntity pregunta = academicQuestionsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pregunta no encontrada con el id " + id));

        academicQuestionsRepository.deleteById(id);
    }

}