package cr.co.ctpcit.citsacbackend.logic.services.exams;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import org.springframework.stereotype.Service;

@Service
public interface ExamsService {
  ExamAcaDto getAcademicExam(String id);

  void saveAcademicExam(ExamAcaDto examAcaDto);

  ExamDaiDto getDaiExam(String id);

  void saveDaiExam(ExamDaiDto examDaiDto);
}
