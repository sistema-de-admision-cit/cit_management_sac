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
}
