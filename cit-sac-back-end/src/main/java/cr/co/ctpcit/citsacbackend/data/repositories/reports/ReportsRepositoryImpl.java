package cr.co.ctpcit.citsacbackend.data.repositories.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
class ReportsRepositoryImpl implements ReportsRepository {
  private final JdbcTemplate jdbcTemplate;

  public ReportsRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ExamSourceDTO> findExamSourceStatistics(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    // parse the grades list to a CSV string
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);

    // call the stored procedure with the parameters
    String sql = "CALL usp_Get_Students_By_Exam_Source_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new ExamSourceDTO(rs.getString("examSource"), rs.getInt("studentCount")));
  }

  public List<EnrollmentAttendanceDTO> findEnrollmentAttendanceStats(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    // parse the grades list to a CSV string
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);

    // call the stored procedure with the parameters
    String sql = "CALL usp_Get_Enrollment_Attendance_Stats_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new EnrollmentAttendanceDTO(rs.getDate("enrollmentDate").toLocalDate(),
            rs.getString("grade"), rs.getString("sector"), rs.getInt("totalEnrolled"),
            rs.getInt("totalAttended")));
  }

  public List<AdmissionFinalDTO> findAdmissionFinalStats(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    // parse the grades list to a CSV string
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);

    // call the stored procedure with the parameters
    String sql = "CALL usp_Get_Admission_Final_Stats_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new AdmissionFinalDTO(rs.getDate("enrollmentDate").toLocalDate(),
            rs.getString("grade"), rs.getString("sector"), rs.getInt("totalAccepted"),
            rs.getInt("totalRejected")));
  }

  public List<AcademicDistributionDTO> findAcademicExamDistribution(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Academic_Exam_Distribution_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new AcademicDistributionDTO(rs.getString("difficulty"),
            rs.getBigDecimal("examScore")));
  }

  public List<AcademicGradeAverageDTO> findAcademicExamGradeAverages(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Academic_Exam_Grade_Average_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new AcademicGradeAverageDTO(rs.getString("grade"),
            rs.getBigDecimal("averageScore")));
  }

  public List<DaiDetailDTO> findDaiExamDetails(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Dai_Exam_Details_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new DaiDetailDTO(rs.getLong("enrollmentId"), rs.getString("area"),
            rs.getBigDecimal("score")));
  }

  public List<DaiAreaAverageDTO> findDaiExamAreaAverages(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Dai_Exam_Area_Average_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new DaiAreaAverageDTO(rs.getString("area"),
            rs.getBigDecimal("averageScore")));
  }
}
