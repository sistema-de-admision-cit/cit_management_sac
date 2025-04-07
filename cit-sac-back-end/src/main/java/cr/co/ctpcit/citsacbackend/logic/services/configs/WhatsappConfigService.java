package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.WhatsappConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;

public interface WhatsappConfigService {
    void sendWhatsAppMessage(WhatsappConfigDto whatsappConfigDto);
    void createWhatsappMessage(EnrollmentDto inscription);
}
