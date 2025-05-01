package cr.co.ctpcit.citsacbackend.logic.services.notifs;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.WhatsappConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;

public interface NotificationsService {

    void sendEmail(EmailConfigDto emailConfigDto);
    void createEmailForInscription(EnrollmentDto inscriptionDto);
    void createEmailForEnrollmentUpdate(Long enrollmentId, EnrollmentUpdateDto updateDto);
    void createEmailForAdmissionDecision(String studentIdNumber);
    void sendWhatsAppMessage(WhatsappConfigDto whatsappConfigDto);
    void createWhatsappMessage(EnrollmentDto inscription);
}
