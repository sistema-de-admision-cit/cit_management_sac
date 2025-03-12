package cr.co.ctpcit.citsacbackend.logic.services.exams;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import org.springframework.stereotype.Service;

@Service
public interface ExamsService {
  ExamDto getAcademicExam(String id);
}
