package cr.co.ctpcit.citsacbackend.logic.services.config;
import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;

public interface EmailConfigService {

    void sendEmail(EmailConfigDto emailConfigDto);
}
