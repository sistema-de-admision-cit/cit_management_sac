package cr.co.ctpcit.citsacbackend.logic.services.examsImplementations;

import cr.co.ctpcit.citsacbackend.data.repositories.exam.dai.DaiExamQuestionsRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai.DaiExamQuestionsMapper;
import cr.co.ctpcit.citsacbackend.logic.services.exams.dai.DaiExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DaiExamQuestionsServiceImplementation implements DaiExamQuestionsService {

  private final DaiExamQuestionsRepository repository;

  @Override
  public List<DaiExamQuestionsDto> getExamAnswers(Integer examId) {
    return repository.findAllByIdExamId(examId).stream().map(DaiExamQuestionsMapper::toDto)
        .collect(Collectors.toList());
  }

}
