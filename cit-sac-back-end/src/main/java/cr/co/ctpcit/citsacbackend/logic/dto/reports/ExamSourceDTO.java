package cr.co.ctpcit.citsacbackend.logic.dto.reports;

/**
 * Data Transfer Object (DTO) representing the source of an exam and the number of students associated with it.
 */
public record ExamSourceDTO(String examSource, int studentCount) {
}
