package cr.co.ctpcit.citsacbackend.logic.dto.configs;

/**
 * Represents the Data Transfer Object (DTO) for updating contact information configurations.
 * Contains various contact fields such as email, phone, and social media contact information.
 */
public record UpdateContactInfoConfigsDto(String emailContact, String emailNotificationsContact,
                                          String whatsappContact, String officeContact,
                                          String instagramContact, String facebookContact,
                                          String emailPassword, String whatsappApiKey) {
}
