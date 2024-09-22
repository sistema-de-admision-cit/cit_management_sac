package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.repositories.AcademicExamQuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.academic.AcademicExamQuestionsMapper;
import cr.co.ctpcit.citsacbackend.logic.services.AcademicExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AcademicExamQuestionsServiceImplementation implements AcademicExamQuestionsService {

    private final AcademicExamQuestionRepository repository;

    @Override
    public List<AcademicExamQuestionsDto> getExamAnswers(Integer examId) {
        return repository.findAllByIdExamId(examId).stream()
                .map(AcademicExamQuestionsMapper::toDto)
                .collect(Collectors.toList());
    }

}
