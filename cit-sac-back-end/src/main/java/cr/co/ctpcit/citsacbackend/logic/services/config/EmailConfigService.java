package cr.co.ctpcit.citsacbackend.logic.services.config;

import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;
import jakarta.mail.MessagingException;

public interface EmailConfigService {

    public void sendEmail(EmailConfigDto emailConfigDto);
}
