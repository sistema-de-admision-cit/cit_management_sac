package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.utils.BuildReport;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import cr.co.ctpcit.citsacbackend.logic.mappers.reports.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing reports. This class is responsible for handling the business
 * logic for reports. It is used to interact with the data access layer and perform operations on
 * reports.
 */
@Service
@RequiredArgsConstructor
public class ReportsServiceImpl {


  private final EnrollmentRepository enrollmentRepository;


  /**
   * Constructs a new ReportsServiceImpl with the required JdbcTemplate dependency.
   *
   * @param jdbcTemplate the JdbcTemplate used for database operations
   */
  private final JdbcTemplate jdbcTemplate;

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

  /**
   * Generates a list of report data DTOs based on the provided report request criteria.
   *
   * <p>This method builds a dynamic {@link Specification} using the provided request parameters,
   * queries the database for matching {@link EnrollmentEntity} records, and maps each result to a
   * {@link ReportDataDto} using the {@link ReportMapper} utility.
   *
   * @param request the {@link ReportRequestDto} containing the filtering criteria for the report
   * @return a list of {@link ReportDataDto} objects representing the generated report data
   */
  public List<ReportDataDto> generateReportData(ReportRequestDto request) {
    Specification<EnrollmentEntity> spec = BuildReport.buildSpecification(request);
    List<EnrollmentEntity> inscripciones = enrollmentRepository.findAll(spec);

    return inscripciones.stream()
            .map(inscripcion -> ReportMapper.mapDataReport(inscripcion, request))
            .collect(Collectors.toList());
  }


}
