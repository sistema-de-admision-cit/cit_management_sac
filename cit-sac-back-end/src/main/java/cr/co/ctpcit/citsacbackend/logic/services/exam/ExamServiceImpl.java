package cr.co.ctpcit.citsacbackend.logic.services.exam;

import cr.co.ctpcit.citsacbackend.data.entities.exam.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.exam.ExamRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exam.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.exam.ExamMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {this.examRepository = examRepository;}

    @Override
    public ExamDto createExam(ExamDto examDto) {
        ExamEntity examEntity = ExamMapper.dtoToEntity(examDto);
        ExamEntity savedExam = examRepository.save(examEntity);
        return ExamMapper.entityToDto(savedExam);
    }

    @Override
    public Page<ExamDto> getExams(Pageable pageable) {
        return examRepository.findAll(pageable).map(ExamMapper::entityToDto);
    }

    @Override
    public ExamDto getExamById(Long id) {
        return examRepository.findById(id).map(ExamMapper::entityToDto).orElse(null);
    }

    @Override
    public ExamDto updateExam(ExamDto examDto) {
        ExamEntity examEntity = ExamMapper.dtoToEntity(examDto);
        ExamEntity updatedExam = examRepository.save(examEntity);
        return ExamMapper.entityToDto(updatedExam);
    }

    @Override
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

}
