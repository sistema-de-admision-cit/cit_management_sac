package cr.co.ctpcit.citsacbackend.logic.services.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import org.springframework.data.domain.Pageable;
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

  List<StudentExamsDto> getStudentsByExamType(ExamType examType, Pageable pageable);

  void updateDaiExam(DaiExamDetailsDto daiExamDetailsDto);

  List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores);

  List<EnglishExamDetailsDto> getExistingEnglishExams(String idNumber);
}
