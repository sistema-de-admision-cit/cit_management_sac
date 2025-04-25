package cr.co.ctpcit.citsacbackend.data.utils;

import cr.co.ctpcit.citsacbackend.data.enums.GradeType;
import cr.co.ctpcit.citsacbackend.data.enums.ReportType;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportDataDto;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportRequestDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Component responsible for generating a CSV report of student enrollments.
 * The report includes only the most recent enrollment per student,
 * sorted alphabetically by first name.
 */

@Component
public class CsvReportGenerator {

    /**
     * Generates a CSV report from the provided list of student data and request details.
     * Duplicated students are filtered by keeping only the latest enrollment by date.
     *
     * @param data    The full list of report data entries.
     * @param request The report request details, including type and date filters.
     * @return A byte array containing the CSV file contents.
     */
    public byte[] generateReport(List<ReportDataDto> data, ReportRequestDto request) {
        Map<String, ReportDataDto> uniqueStudents = data.stream()
                .collect(Collectors.toMap(
                        ReportDataDto::getStudentId,
                        Function.identity(),
                        (existing, replacement) -> {
                            return existing.getEnrollmentDate().isAfter(replacement.getEnrollmentDate())
                                    ? existing
                                    : replacement;
                        }
                ));

        List<ReportDataDto> sortedData = uniqueStudents.values().stream()
                .sorted(Comparator.comparing(ReportDataDto::getFirstName))
                .collect(Collectors.toList());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

            writer.write('\uFEFF');

            CSVFormat csvFormat;
            boolean isFullGradesReport = request.getReportType() == ReportType.GRADES &&
                    (request.getGradeTypeFilter() == null ||
                            request.getGradeTypeFilter() == GradeType.ALL);

            // Configurar el formato CSV según el tipo de reporte
            if (isFullGradesReport) {
                csvFormat = CSVFormat.DEFAULT.withHeader(
                        "Cédula Estudiante",
                        "Nombre",
                        "Apellidos",
                        "Fecha Inscripción",
                        "Nivel Inglés",
                        "Nota Académica",
                        "Notas Anteriores"
                );
            } else {
                csvFormat = CSVFormat.DEFAULT.withHeader(
                        "Cédula Estudiante",
                        "Nombre",
                        "Apellidos",
                        "Fecha Inscripción",
                        getCategoryHeader(request)
                );
            }

            try (CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)) {
                for (ReportDataDto item : sortedData) {
                    if (isFullGradesReport) {
                        csvPrinter.printRecord(
                                item.getStudentId(),
                                item.getFirstName(),
                                item.getFullSurname(),
                                formatDate(item.getEnrollmentDate()),
                                item.getEnglishGrade() != null ? item.getEnglishGrade() : "N/A",
                                item.getAcademicGrade() != null ? item.getAcademicGrade() : "N/A",
                                item.getPreviousGrade() != null ? item.getPreviousGrade() : "N/A"
                        );
                    } else {
                        String categoryValue;
                        if (request.getReportType() == ReportType.GRADES) {
                            switch (request.getGradeTypeFilter()) {
                                case ENGLISH:
                                    categoryValue = item.getEnglishGrade() != null ?
                                            translateGradeValue(item.getEnglishGrade(), request.getReportType()) : "N/A";
                                    break;
                                case ACADEMIC:
                                    categoryValue = item.getAcademicGrade() != null ?
                                            item.getAcademicGrade() : "N/A";
                                    break;
                                case PREVIOUS:
                                    categoryValue = item.getPreviousGrade() != null ?
                                            item.getPreviousGrade() : "N/A";
                                    break;
                                default:
                                    categoryValue = translateReportValue(item.getReportCategory(), request.getReportType());
                            }
                        } else {
                            categoryValue = translateReportValue(item.getReportCategory(), request.getReportType());
                        }

                        csvPrinter.printRecord(
                                item.getStudentId(),
                                item.getFirstName(),
                                item.getFullSurname(),
                                formatDate(item.getEnrollmentDate()),
                                categoryValue
                        );
                    }
                }
                csvPrinter.flush();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generando reporte CSV", e);
        }
    }

    /**
     * Formats an {@link Instant} into a human-readable date string.
     */
    private String formatDate(Instant instant) {
        return instant.atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    /**
     * Returns the CSV column header label based on the report type.
     */
    private String getCategoryHeader(ReportRequestDto request) {
        switch (request.getReportType()) {
            case KNOWN_THROUGH: return "Conocido por";
            case GRADE_TO_ENROLL: return "Grado";
            case PROCESS_STATUS: return "Estado";
            case PROVINCE: return "Provincia";
            case GRADES:
                if (request.getGradeTypeFilter() != null) {
                    switch (request.getGradeTypeFilter()) {
                        case ENGLISH: return "Nivel Inglés";
                        case ACADEMIC: return "Nota Académica";
                        case PREVIOUS: return "Notas Anteriores";
                        case ALL: return "Notas";
                    }
                }
                return "Notas";
            default: return "Categoría";
        }
    }

    /**
     * Translates internal report values to human-readable labels.
     */
    private String translateReportValue(String value, ReportType reportType) {
        if (reportType == ReportType.KNOWN_THROUGH) {
            switch (value) {
                case "SM": return "Redes Sociales";
                case "OH": return "Open House";
                case "FD": return "Amigos";
                case "FM": return "Familiar";
                case "OT": return "Otro";
                default: return value;
            }
        } else if (reportType == ReportType.PROCESS_STATUS) {
            switch (value) {
                case "PENDING": return "Pendiente";
                case "ELIGIBLE": return "Elegible";
                case "INELIGIBLE": return "No elegible";
                case "ACCEPTED": return "Aceptado";
                case "REJECTED": return "Rechazado";
                default: return value;
            }
        } else if (reportType == ReportType.GRADE_TO_ENROLL) {
            switch (value) {
                case "FIRST": return "Primer Grado";
                case "SECOND": return "Segundo Grado";
                case "THIRD": return "Tercer Grado";
                case "FOURTH": return "Cuarto Grado";
                case "FIFTH": return "Quinto Grado";
                case "SIXTH": return "Sexto Grado";
                case "SEVENTH": return "Séptimo Grado";
                case "EIGHTH": return "Octavo Grado";
                case "NINTH": return "Noveno Grado";
                case "TENTH": return "Décimo Grado";
                default: return value;
            }
        } else if (reportType == ReportType.GRADES) {
            return translateGradeValue(value, reportType);
        }
        return value;
    }

    /**
     * Special translation for grade values
     */
    private String translateGradeValue(String value, ReportType reportType) {
        // Puedes agregar traducciones específicas para valores de notas si es necesario
        return value;
    }

    /**
     * Gets description for grade type
     */
    private String getGradeTypeDescription(GradeType gradeType) {
        switch (gradeType) {
            case ENGLISH: return "Inglés";
            case ACADEMIC: return "Académico";
            case PREVIOUS: return "Anteriores";
            case ALL: return "Todas";
            default: return "";
        }
    }
}
