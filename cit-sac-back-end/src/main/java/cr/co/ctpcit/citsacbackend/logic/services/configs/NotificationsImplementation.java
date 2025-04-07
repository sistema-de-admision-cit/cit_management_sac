package cr.co.ctpcit.citsacbackend.logic.services.configs;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.WhatsappConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationsImplementation implements NotificationsService {

private final JavaMailSender mailSender;

public NotificationsImplementation(JavaMailSender mailSender) {
    this.mailSender = mailSender;
}

@Override
public void createEmail(EnrollmentDto inscription){
    for (ParentDto parent : inscription.student().parents()) {
        sendEmail(new EmailConfigDto(parent.email(),
        "Confirmación de Registro - Complejo Educativo CIT",
                "<html>" +
                        "<head>" +
                        "<style> " +
                        "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                        ".container { background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0px 0px 10px #ccc; }" +
                        "h1 { color: #4CAF50; }" +
                        "p { font-size: 16px; }" +
                        "</style>" +
                        "</head>" +
                        "<body>" +
                        "<div class='container'>" +
                        "<h3>Estimado/a " +
                        parent.person().firstName() + " " + parent.person().firstSurname() + " " + parent.person().secondSurname() +
                        ",</h3>" +
                        "<p>Nos complace informarle que el registro de su hijo/a, <strong>" +
                        inscription.student().person().firstName() + " " + inscription.student().person().firstSurname() + " " + inscription.student().person().secondSurname() +
                        "</strong>, ha sido exitosamente completado en <strong>Complejo Educativo CIT</strong>.</p>" +
                        "<h4>📌 Detalles del Registro:</h4>" +
                        "<ul>" +
                        "<li><strong>Estudiante:</strong> [Nombre del Estudiante]</li>" +
                        "<li><strong>Grado/Nivel:</strong> [Grado/Nivel Escolar]</li>" +
                        "<li><strong>Fecha de Inicio:</strong> [Fecha de Inicio de Clases]</li>" +
                        "</ul>" +
                        "<p>Para finalizar el proceso, le solicitamos que revise los documentos adjuntos y se comunique con nuestra administración en caso de dudas.</p>" +
                        "<p><em>Este es un mensaje automático, por favor no responda a este correo.</em> Si requiere más información, puede contactarnos a <strong>[Correo de Contacto]</strong> o llamarnos al <strong>[Teléfono]</strong>.</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>"

        ));
    }
}

@Override
public void sendEmail(EmailConfigDto emailConfigDto) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(emailConfigDto.getRecipient());
        helper.setSubject(emailConfigDto.getSubject());
        helper.setText(emailConfigDto.getMessage(), true);
        mailSender.send(message);
    } catch (MessagingException e) {
        throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
    }
}

    public void createWhatsappMessage(EnrollmentDto inscription){
        WhatsappConfigDto whatsappConfigDto = new WhatsappConfigDto();
        for (ParentDto parent : inscription.student().parents()) {
            whatsappConfigDto.setRecipient(parent.phoneNumber());
            whatsappConfigDto.setMessage(
                    "Hola es una prueba desde el Backend"
            );
        }
    }

    public void sendWhatsAppMessage(WhatsappConfigDto whatsappConfigDto){
        Message.creator( new PhoneNumber("+"+whatsappConfigDto.getRecipient()),
                new PhoneNumber(whatsappConfigDto.getFromWhatsAppNumber()),
                whatsappConfigDto.getMessage()
        ).create();
    }

}
