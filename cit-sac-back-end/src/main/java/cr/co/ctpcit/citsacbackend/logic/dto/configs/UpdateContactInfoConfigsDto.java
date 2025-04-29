package cr.co.ctpcit.citsacbackend.logic.dto.configs;

public record UpdateContactInfoConfigsDto(String emailContact, String emailNotificationsContact,
                                          String whatsappContact, String officeContact,
                                          String instagramContact, String facebookContact,
                                          String emailPassword, String whatsappApiKey) {
}
