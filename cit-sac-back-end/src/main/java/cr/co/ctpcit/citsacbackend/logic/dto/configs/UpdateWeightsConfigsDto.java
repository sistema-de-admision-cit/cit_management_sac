package cr.co.ctpcit.citsacbackend.logic.dto.configs;

/**
 * Represents the Data Transfer Object (DTO) for updating weight configurations.
 * Contains the weights for previous grades, academic performance, and English scores.
 */
public record UpdateWeightsConfigsDto(Double prevGradesWeight, Double academicWeight) {
}
