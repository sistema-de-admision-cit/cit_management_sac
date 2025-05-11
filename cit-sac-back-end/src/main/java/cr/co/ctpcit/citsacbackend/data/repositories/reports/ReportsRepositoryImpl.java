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

  /**
   * Tracks daily counts and conversion rates through the admission funnel: Interested → Eligible →
   * Accepted
   */
  @Override
  public List<AdmissionFunnelTrendDTO> findAdmissionFunnelTrend(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Admission_Funnel_Trend_Filters(?, ?, ?, ?)";

    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new AdmissionFunnelTrendDTO(rs.getDate("enrollmentDate").toLocalDate(),
            rs.getInt("interestedCount"), rs.getInt("eligibleCount"), rs.getInt("acceptedCount"),
            rs.getBigDecimal("pct_Interested_to_Eligible"),
            rs.getBigDecimal("pct_Eligible_to_Accepted")));
  }

  @Override
  public List<LeadSourceEffectivenessDTO> findLeadSourceEffectiveness(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_LeadSource_Effectiveness_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new LeadSourceEffectivenessDTO(rs.getString("examSource"),
            rs.getInt("studentCount"), rs.getBigDecimal("acceptanceRate"),
            rs.getBigDecimal("avgExamScore")));
  }

  public List<PreviousGradesStatusDTO> findPreviousGradesByStatus(LocalDate start, LocalDate end,
      List<String> grades, String sector) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    var sql = "CALL usp_Get_PreviousGrades_By_Status_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[] {start, end, gradesCsv, sector},
        (rs, i) -> new PreviousGradesStatusDTO(rs.getBigDecimal("previousGrades"),
            rs.getString("status")));
  }

  @Override
  public List<CefrDistributionDTO> findCefrDistribution(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {

    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_CEFR_Distribution_Filters(?, ?, ?, ?)";

    return jdbcTemplate.query(sql, new Object[] {startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new CefrDistributionDTO(rs.getString("level"), rs.getInt("count")));
  }
}
