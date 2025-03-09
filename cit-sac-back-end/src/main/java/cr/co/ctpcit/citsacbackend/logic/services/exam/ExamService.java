package cr.co.ctpcit.citsacbackend.logic.services.exam;

import cr.co.ctpcit.citsacbackend.logic.dto.exam.ExamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamService {
    ExamDto createExam(ExamDto examDto);
    public Page<ExamDto> getExams(Pageable pageable);
    public ExamDto getExamById(Long id);
    public ExamDto updateExam(ExamDto examDto);
    public void deleteExam(Long id);
}
