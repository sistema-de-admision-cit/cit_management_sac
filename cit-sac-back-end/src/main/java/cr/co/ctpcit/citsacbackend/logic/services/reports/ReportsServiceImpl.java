package cr.co.ctpcit.citsacbackend.logic.services.reports;

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
   * @param startDate fecha inicial de inscripción (o null para no filtrar)
   * @param endDate   fecha final de inscripción (o null para no filtrar)
   * @param grades    lista de grados a incluir (e.g. ["FIRST","SECOND"]) o empty para todos
   * @param sector    "All", "Primaria" o "Secundaria"
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
}
