package cr.co.ctpcit.citsacbackend.logic.dto.reports;


import cr.co.ctpcit.citsacbackend.data.enums.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to encapsulate the request data for generating a report.
 * This DTO includes filtering options for date ranges, report type, and specific filters
 * depending on the report type, such as known through method, grade, process status, or province.
 */

@Setter
@Getter
public class ReportRequestDto {


    /**
     * The start date of the date range for the report.
     * This value cannot be null.
     */
    @NotNull
    private LocalDate startDate;

    /**
     * The end date of the date range for the report.
     * This value cannot be null.
     */
    @NotNull
    private LocalDate endDate;

    /**
     * The type of report to generate, determining what data to include and how to process it.
     * This value cannot be null.
     */
    @NotNull
    private ReportType reportType;

    /**
     * Filter for the "known through" method, applicable if the report type is KNOWN_THROUGH.
     */
    private KnownThrough knownThroughFilter;

    /**
     * Filter for the grade to enroll, applicable if the report type is GRADE_TO_ENROLL.
     */
    private Grades gradeFilter;

    /**
     * Filter for the process status, applicable if the report type is PROCESS_STATUS.
     */
    private ProcessStatus statusFilter;

    /**
     * Filter for the province, applicable if the report type is PROVINCE.
     */
    private String provinceFilter;


    /**
     * Filter for Grades, applicable if the report type is GRADES.
     */
    private GradeType gradeTypeFilter;

}
