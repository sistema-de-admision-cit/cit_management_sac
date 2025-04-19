package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing reports. This class is responsible for handling the business
 * logic for reports. It is used to interact with the data access layer and perform operations on
 * reports.
 */
@Service
public class ReportsServiceImpl {
  /**
   * Constructs a new ReportsServiceImpl with the required JdbcTemplate dependency.
   *
   * @param jdbcTemplate the JdbcTemplate used for database operations
   */
  private final JdbcTemplate jdbcTemplate;

  public ReportsServiceImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  /**
   * Retrieves exam source statistics by executing a stored procedure that groups students by their exam source.
   * The method calls the database stored procedure 'usp_Get_Students_By_Exam_Source' which returns
   * each exam source along with the count of students associated with it.
   *
   * @return a list of ExamSourceDTO objects containing exam source names and corresponding student counts
   * @throws org.springframework.jdbc.UncategorizedSQLException if there's an error executing the stored procedure
   */
  public List<ExamSourceDTO> getExamSourceStatistics() {
    // Call the stored procedure
    String sql = "CALL usp_Get_Students_By_Exam_Source()";
    return jdbcTemplate.query(sql, (rs, rowNum) -> {
      return new ExamSourceDTO(rs.getString("examSource"), rs.getInt("studentCount"));
    });
  }
}
