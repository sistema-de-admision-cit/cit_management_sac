package cr.co.ctpcit.citsacbackend.logic.dto.configs;

/**
 * Represents the Data Transfer Object (DTO) for updating the quantity configurations.
 * Contains the quantities for DAI questions and academic questions.
 */
public record UpdateQuantityConfigsDto(int daiQuestionsQuantity, int academicQuestionsQuantity) {
}
