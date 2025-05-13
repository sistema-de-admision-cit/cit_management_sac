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
/**
 * Service interface for managing exam-related operations.
 * Provides methods for handling different types of exams (Academic, DAI, English)
 * including retrieval, saving, and processing of exam data.
 */
@Service
public interface ExamsService {
  /**
   * Retrieves an academic exam by its ID.
   *
   * @param id the unique identifier of the academic exam
   * @return the academic exam data
   */
  ExamAcaDto getAcademicExam(String id);
  /**
   * Saves an academic exam.
   *
   * @param examAcaDto the academic exam data to save
   * @throws JsonProcessingException if there's an error processing the exam data
   */
  void saveAcademicExam(ExamAcaDto examAcaDto) throws JsonProcessingException;
  /**
   * Retrieves a DAI exam by its ID.
   *
   * @param id the unique identifier of the DAI exam
   * @return the DAI exam data
   */
  ExamDaiDto getDaiExam(String id);
  /**
   * Saves a DAI exam.
   *
   * @param examDaiDto the DAI exam data to save
   * @throws JsonProcessingException if there's an error processing the exam data
   */
  void saveDaiExam(ExamDaiDto examDaiDto) throws JsonProcessingException;
  /**
   * Retrieves existing academic exams for a student.
   *
   * @param id the student identifier
   * @return list of academic exam details
   */
  List<AcademicExamDetailsDto> getExistingAcademicExams(String id);
  /**
   * Retrieves existing DAI exams for a student.
   *
   * @param idNumber the student identification number
   * @return list of DAI exam details
   */
  List<DaiExamDetailsDto> getExistingDaiExams(String idNumber);
  /**
   * Retrieves students by exam type with pagination.
   *
   * @param examType the type of exam to filter by
   * @param pageable pagination information
   * @return list of student exam data
   */
  List<StudentExamsDto> getStudentsByExamType(ExamType examType, Pageable pageable);
  /**
   * Updates a DAI exam.
   *
   * @param daiExamDetailsDto the updated DAI exam details
   */
  void updateDaiExam(DaiExamDetailsDto daiExamDetailsDto);
  /**
   * Processes English exam scores in bulk.
   *
   * @param englishScores list of English score entries to process
   * @return list of processing logs
   */
  List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores);
  /**
   * Retrieves existing English exams for a student.
   *
   * @param idNumber the student identification number
   * @return list of English exam details
   */
  List<EnglishExamDetailsDto> getExistingEnglishExams(String idNumber);
  /**
   * Searches student exams by value and exam type.
   *
   * @param value the search value (name, ID, etc.)
   * @param examType the type of exam to filter by
   * @return list of matching student exam data
   */
  List<StudentExamsDto> searchStudentExams(String value, ExamType examType, Pageable pageable);

  /**
   * Returns the number of students who have taken academic (ACA) exams.
   *
   * @return the count of students with academic exams
   */
  Long getStudentsCountWithAcademicExams();

  /**
   * Returns the number of students who have taken DAI exams.
   *
   * @return the count of students with DAI exams
   */
  Long getStudentsCountWithDAIExams();

  /**
   * Returns the number of students who match the given search value and have taken academic (ACA) exams.
   *
   * @param value the search term used to filter students by ID number or name fields
   * @return the count of matched students with academic exams
   */
  Long getSearchCountByAcademicExam(String value);

  /**
   * Returns the number of students who match the given search value and have taken DAI exams.
   *
   * @param value the search term used to filter students by ID number or name fields
   * @return the count of matched students with DAI exams
   */
  Long getSearchCountByDAIExam(String value);

}
