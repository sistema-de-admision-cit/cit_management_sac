package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.DaiExamQuestionsRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exams.dai.DaiExamQuestionsMapper;
import cr.co.ctpcit.citsacbackend.logic.services.DaiExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaiExamQuestionsServiceImplementation implements DaiExamQuestionsService {

    private final DaiExamQuestionsRepository repository;

    @Autowired
    public DaiExamQuestionsServiceImplementation(DaiExamQuestionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DaiExamQuestionsDto> getAllExamQuestions() {
        List<DaiExamQuestionsEntity> entities = repository.findAll();
        return DaiExamQuestionsMapper.toDtoList(entities);
    }

    @Override
    public DaiExamQuestionsDto addExamQuestion(DaiExamQuestionsDto dto) {
        DaiExamQuestionsEntity entity = DaiExamQuestionsMapper.toEntity(dto);
        DaiExamQuestionsEntity savedEntity = repository.save(entity);
        return DaiExamQuestionsMapper.toDto(savedEntity);
    }
}
