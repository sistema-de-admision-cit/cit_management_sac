package cr.co.ctpcit.citsacbackend.logic.services.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamsService {
  ExamAcaDto getAcademicExam(String id);

  void saveAcademicExam(ExamAcaDto examAcaDto) throws JsonProcessingException;

  ExamDaiDto getDaiExam(String id);

  void saveDaiExam(ExamDaiDto examDaiDto) throws JsonProcessingException;

  List<AcademicExamDto> getExistingAcademicExams(String id);
}
