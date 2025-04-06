package cr.co.ctpcit.citsacbackend.logic.services.configs;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;

public interface EmailConfigService {

    void sendEmail(EmailConfigDto emailConfigDto);
    void createEmail(EnrollmentDto inscriptionDto);
}
