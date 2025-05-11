package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.reports.ReportsRepository;
import cr.co.ctpcit.citsacbackend.data.utils.BuildReport;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import cr.co.ctpcit.citsacbackend.logic.mappers.reports.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl {
  private final ReportsRepository reportsRepository;
  private final EnrollmentRepository enrollmentRepository;

  public List<ExamSourceDTO> getExamSourceStatistics(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    return reportsRepository.findExamSourceStatistics(startDate, endDate, grades, sector);
  }

  public List<EnrollmentAttendanceDTO> getEnrollmentAttendanceStats(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    return reportsRepository.findEnrollmentAttendanceStats(startDate, endDate, grades, sector);
  }

  public List<AdmissionFinalDTO> getAdmissionFinalStats(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    return reportsRepository.findAdmissionFinalStats(startDate, endDate, grades, sector);
  }

  public AcademicExamReportDTO getAcademicExamReport(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {

    List<AcademicDistributionDTO> dist =
        reportsRepository.findAcademicExamDistribution(startDate, endDate, grades, sector);
    List<AcademicGradeAverageDTO> avg =
        reportsRepository.findAcademicExamGradeAverages(startDate, endDate, grades, sector);

    return new AcademicExamReportDTO(dist, avg);
  }

  public DaiExamReportDTO getDaiExamReport(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {

    List<DaiDetailDTO> details = new ArrayList<>();
    List<DaiAreaAverageDTO> avgs = new ArrayList<>();

    return new DaiExamReportDTO(details, avgs);
  }

  /**
   * Retrieves daily admission-funnel metrics: interested → eligible → accepted
   */
  public List<AdmissionFunnelTrendDTO> getAdmissionFunnelTrend(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    return reportsRepository.findAdmissionFunnelTrend(startDate, endDate, grades, sector);
  }

  /**
   * Retrieves lead source effectiveness metrics.
   *
   * @param startDate
   * @param endDate
   * @param grades
   * @param sector
   * @return
   */
  public List<LeadSourceEffectivenessDTO> getLeadSourceEffectiveness(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    return reportsRepository.findLeadSourceEffectiveness(startDate, endDate, grades, sector);
  }

  /**
   * Retrieves previous grades by status metrics.
   *
   * @param start
   * @param end
   * @param grades
   * @param sector
   * @return
   */
  public List<PreviousGradesStatusDTO> getPreviousGradesByStatus(LocalDate start, LocalDate end,
      List<String> grades, String sector) {
    return reportsRepository.findPreviousGradesByStatus(start, end, grades, sector);
  }

  /**
   * Generates a list of report data DTOs based on the provided report request criteria.
   *
   * <p>
   * This method builds a dynamic {@link Specification} using the provided request parameters,
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
