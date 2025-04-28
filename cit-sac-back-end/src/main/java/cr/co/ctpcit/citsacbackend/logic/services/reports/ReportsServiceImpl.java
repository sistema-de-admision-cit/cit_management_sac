package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.AdmissionFinalDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.EnrollmentAttendanceDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing reports. This class is responsible for handling the business
 * logic for reports. It is used to interact with the data access layer and perform operations on
 * reports.
 */
@Service
public class ReportsServiceImpl {
  private final JdbcTemplate jdbcTemplate;

  public ReportsServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ExamSourceDTO> getExamSourceStatistics() {
    // Call the stored procedure
    String sql = "CALL usp_Get_Students_By_Exam_Source()";
    return jdbcTemplate.query(sql, (rs, rowNum) -> {
      return new ExamSourceDTO(rs.getString("examSource"), rs.getInt("studentCount"));
    });
  }

  /**
   * @param startDate starting date for the enrollment (or null to not filter)
   * @param endDate ending date for the enrollment (or null to not filter)
   * @param grades list of grades to include (e.g. ["FIRST","SECOND"]) or empty for all
   * @param sector "All", "Primaria" or "Secundaria"
   */
  public List<EnrollmentAttendanceDTO> getEnrollmentAttendanceStats(
      LocalDate startDate,
      LocalDate endDate,
      List<String> grades,
      String sector
  ) {
    // convierte la lista de grados en CSV; si está vacía, usa "All"
    String gradesCsv = grades.isEmpty() ? "All"
        : grades.stream().collect(Collectors.joining(","));
    String sql = "CALL usp_Get_Enrollment_Attendance_Stats_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[]{ startDate, endDate, gradesCsv, sector },
        (rs, rowNum) -> new EnrollmentAttendanceDTO(
            rs.getDate("enrollmentDate").toLocalDate(),
            rs.getString("grade"),
            rs.getString("sector"),
            rs.getInt("totalEnrolled"),
            rs.getInt("totalAttended")
        ));
  }

  /**
   * Get admission final statistics based on the provided filters.
   * @param startDate starting date for the enrollment (or null to not filter)
   * @param endDate ending date for the enrollment (or null to not filter)
   * @param grades list of grades to include (e.g. ["FIRST","SECOND"]) or empty for all
   * @param sector "All", "Primaria" or "Secundaria"
   * @return a list of AdmissionFinalDTO objects containing the statistics
   */
  public List<AdmissionFinalDTO> getAdmissionFinalStats(
      LocalDate startDate,
      LocalDate endDate,
      List<String> grades,
      String sector
  ) {
    String gradesCsv = grades.isEmpty() ? "All" : String.join(",", grades);
    String sql = "CALL usp_Get_Admission_Final_Stats_Filters(?, ?, ?, ?)";
    return jdbcTemplate.query(sql, new Object[]{startDate, endDate, gradesCsv, sector},
        (rs, rowNum) -> new AdmissionFinalDTO(
            rs.getDate("enrollmentDate").toLocalDate(),
            rs.getString("grade"),
            rs.getString("sector"),
            rs.getInt("totalAccepted"),
            rs.getInt("totalRejected")
        ));
  }
}
