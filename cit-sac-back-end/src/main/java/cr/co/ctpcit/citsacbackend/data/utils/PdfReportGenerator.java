package cr.co.ctpcit.citsacbackend.data.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import cr.co.ctpcit.citsacbackend.data.enums.GradeType;
import cr.co.ctpcit.citsacbackend.data.enums.ReportType;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportDataDto;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportRequestDto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Component responsible for generating student enrollment PDF reports.
 */

@Component
public class PdfReportGenerator {

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
    private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.GRAY);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
    private static final Font BODY_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10);
    private static final Font FOOTER_FONT = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, BaseColor.GRAY);


    /**
     * Generates a PDF report from a list of student report data.
     *
     * @param data List of {@link ReportDataDto} with student information
     * @param request {@link ReportRequestDto} containing filter information for the report
     * @return a byte array representing the generated PDF
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

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, baos);


            writer.setPageEvent(new PdfPageEvent());

            document.open();

            try {
                ClassPathResource logoResource = new ClassPathResource("static/images/logo.png");
                Image logo = Image.getInstance(logoResource.getURL());
                logo.scaleToFit(150, 120);
                logo.setAbsolutePosition(36, document.top() - 60);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception e) {
                System.err.println("No se pudo cargar el logo: " + e.getMessage());
            }

            Paragraph title = new Paragraph("Reporte de Inscripciones Estudiantiles", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            // Subtítulo con información del reporte
            Paragraph subtitle = new Paragraph("Informe Generado", SUBTITLE_FONT);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(15f);
            document.add(subtitle);

            // Información de filtros con mejor formato
            Paragraph filters = new Paragraph();
            filters.setFont(BODY_FONT);
            filters.add(new Chunk("Rango de fechas: ", BODY_FONT));
            filters.add(new Chunk(request.getStartDate() + " a " + request.getEndDate(), BODY_FONT));
            filters.add(Chunk.NEWLINE);
            filters.add(new Chunk("Tipo de reporte: ", BODY_FONT));
            filters.add(new Chunk(getReportTypeDescription(request), BODY_FONT));


            if (request.getReportType() == ReportType.GRADES && request.getGradeTypeFilter() != null) {
                filters.add(Chunk.NEWLINE);
                filters.add(new Chunk("Tipo de nota: ", BODY_FONT));
                filters.add(new Chunk(getGradeTypeDescription(request.getGradeTypeFilter()), BODY_FONT));
            }

            filters.setSpacingAfter(20f);
            document.add(filters);

            PdfPTable table;
            boolean isFullGradesReport = request.getReportType() == ReportType.GRADES &&
                    (request.getGradeTypeFilter() == null ||
                            request.getGradeTypeFilter() == GradeType.ALL);

            if (isFullGradesReport) {
                table = new PdfPTable(7);
                float[] columnWidths = {2f, 2f, 3f, 2f, 2f, 2f, 2f};
                table.setWidths(columnWidths);

                Stream.of("Cédula Estudiante", "Nombre", "Apellidos", "Fecha Inscripción",
                                "Nivel Inglés", "Nota Académica", "Notas Anteriores")
                        .forEach(header -> {
                            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
                            cell.setBackgroundColor(new BaseColor(57, 99, 148));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setPadding(5f);
                            table.addCell(cell);
                        });
            } else {

                table = new PdfPTable(5);
                float[] columnWidths = {2f, 2f, 3f, 2f, 2f};
                table.setWidths(columnWidths);


                Stream.of("Cédula Estudiante", "Nombre", "Apellidos", "Fecha Inscripción",
                                getCategoryHeader(request))
                        .forEach(header -> {
                            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
                            cell.setBackgroundColor(new BaseColor(57, 99, 148));
                            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell.setPadding(5f);
                            table.addCell(cell);
                        });
            }

            table.setHeaderRows(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (ReportDataDto item : sortedData) {
                addTableCell(table, item.getStudentId());
                addTableCell(table, item.getFirstName());
                addTableCell(table, item.getFullSurname());
                addTableCell(table, item.getEnrollmentDate().format(formatter));

                if (isFullGradesReport) {
                    addTableCell(table, item.getEnglishGrade() != null ? item.getEnglishGrade() : "N/A");
                    addTableCell(table, item.getAcademicGrade() != null ? item.getAcademicGrade() : "N/A");
                    addTableCell(table, item.getPreviousGrade() != null ? item.getPreviousGrade() : "N/A");
                } else {
                    String cellValue;
                    if (request.getReportType() == ReportType.GRADES) {
                        switch (request.getGradeTypeFilter()) {
                            case ENGLISH:
                                cellValue = item.getEnglishGrade() != null ? item.getEnglishGrade() : "N/A";
                                break;
                            case ACADEMIC:
                                cellValue = item.getAcademicGrade() != null ? item.getAcademicGrade() : "N/A";
                                break;
                            case PREVIOUS:
                                cellValue = item.getPreviousGrade() != null ? item.getPreviousGrade() : "N/A";
                                break;
                            default:
                                cellValue = translateReportValue(item.getReportCategory(), request.getReportType());
                        }
                    } else {
                        cellValue = translateReportValue(item.getReportCategory(), request.getReportType());
                    }
                    addTableCell(table, cellValue);
                }
            }

            document.add(table);

            // Nota al pie
            Paragraph note = new Paragraph("* Reporte generado automáticamente por el sistema de admisiones", FOOTER_FONT);
            note.setAlignment(Element.ALIGN_CENTER);
            document.add(note);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error generando reporte PDF", e);
        }
    }

    /**
     * Custom PDF page event handler for adding footer on each page.
     */

    private static class PdfPageEvent extends PdfPageEventHelper {
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase(String.format("Página %d", writer.getPageNumber()), FOOTER_FONT);
            ColumnText.showTextAligned(
                    cb, Element.ALIGN_CENTER, footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0
            );


            Phrase generationDate = new Phrase(
                    "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    FOOTER_FONT
            );
            ColumnText.showTextAligned(
                    cb, Element.ALIGN_LEFT, generationDate,
                    document.left(),
                    document.bottom() - 10, 0
            );
        }
    }

    /**
     * Adds a table cell with the specified content to the PDF table.
     *
     * @param table  the PDF table
     * @param content the content to be added in the cell
     */

    private void addTableCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BODY_FONT));
        cell.setPadding(5f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(cell);
    }

    /**
     * Formats an {@link Instant} into a human-readable date string.
     *
     * @param instant the date to format
     * @return formatted date string
     */

    private String formatDate(Instant instant) {
        return instant.atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    private String getGradeTypeDescription(GradeType gradeType) {
        switch (gradeType) {
            case ENGLISH: return "Inglés";
            case ACADEMIC: return "Académico";
            case PREVIOUS: return "Anteriores";
            case ALL: return "Todas";
            default: return "";
        }
    }

    /**
     * Translates internal report category values into human-readable labels.
     *
     * @param value the internal value to translate
     * @param reportType  the type of report
     * @return translated label
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
        }
        return value;
    }

    /**
     * Returns the description of the selected report type with applied filters.
     *
     * @param request the report request
     * @return formatted description string
     */

    private String getReportTypeDescription(ReportRequestDto request) {
        switch (request.getReportType()) {
            case KNOWN_THROUGH:
                return "Conocido por" + (request.getKnownThroughFilter() != null ?
                        " (" + translateReportValue(request.getKnownThroughFilter().name(), ReportType.KNOWN_THROUGH) + ")" : " (Todos)");
            case GRADE_TO_ENROLL:
                return "Grado a inscribir" + (request.getGradeFilter() != null ?
                        " (" + translateReportValue(request.getGradeFilter().name(),ReportType.GRADE_TO_ENROLL) + ")" : " (Todos)");
            case PROCESS_STATUS:
                return "Estado del proceso" + (request.getStatusFilter() != null ?
                        " (" + translateReportValue(request.getStatusFilter().name(), ReportType.PROCESS_STATUS) + ")" : " (Todos)");
            case PROVINCE:
                return "Provincia" + (request.getProvinceFilter() != null ?
                        " (" + request.getProvinceFilter() + ")" : " (Todas)");
            case GRADES:
                return "Notas" + (request.getGradeTypeFilter() != null ?
                        " (" + getGradeTypeDescription(request.getGradeTypeFilter()) + ")" : " (Todas)");
            default:
                return "";
        }
    }

    /**
     * Gets the appropriate table header title based on the report type.
     *
     * @param request the report request
     * @return header name for the category column
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
}
