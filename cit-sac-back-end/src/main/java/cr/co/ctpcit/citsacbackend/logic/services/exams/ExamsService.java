package cr.co.ctpcit.citsacbackend.logic.services.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExamsService {
  ExamAcaDto getAcademicExam(String id);

  void saveAcademicExam(ExamAcaDto examAcaDto) throws JsonProcessingException;

  ExamDaiDto getDaiExam(String id);

  void saveDaiExam(ExamDaiDto examDaiDto) throws JsonProcessingException;

  List<AcademicExamDetailsDto> getExistingAcademicExams(String id);

  List<DaiExamDetailsDto> getExistingDaiExams(String idNumber);

  List<StudentExamsDto> getStudentsByExamType(ExamType examType);
}
