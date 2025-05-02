package cr.co.ctpcit.citsacbackend.logic.services.notifs;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.WhatsappConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServiceImpl implements NotificationsService {

  private final JavaMailSender mailSender;

  private final SystemConfigRepository configRepository;

  @Value("${twilio.whatsapp.from}")
  private String fromWhatsAppNumber;

  public NotificationsServiceImpl(JavaMailSender mailSender,
      SystemConfigRepository configRepository) {
    this.mailSender = mailSender;
    this.configRepository = configRepository;
  }


  @Override
  public void createEmailForInscription(EnrollmentDto inscription) {
    String emailContact = "";
    String phoneContact = "";
    String gradoEsp = "";

    for (SystemConfigEntity config : configRepository.getContactInfo()) {
      switch (config.getConfigName().name()) {
        case "EMAIL_CONTACT" -> emailContact = config.getConfigValue();
        case "OFFICE_CONTACT" -> phoneContact = config.getConfigValue();
      }
    }

    switch (inscription.gradeToEnroll().name()) {
      case "FIRST" -> gradoEsp = "Primero";
      case "SECOND" -> gradoEsp = "Segundo";
      case "THIRD" -> gradoEsp = "Tercero";
      case "FOURTH" -> gradoEsp = "Cuarto";
      case "FIFTH" -> gradoEsp = "Quinto";
      case "SIXTH" -> gradoEsp = "Sexto";
      case "SEVENTH" -> gradoEsp = "Sétimo";
      case "EIGHTH" -> gradoEsp = "Octavo";
      case "NINTH" -> gradoEsp = "Noveno";
      case "TENTH" -> gradoEsp = "Décimo";
      default -> gradoEsp = String.valueOf(inscription.gradeToEnroll());
    }

    for (ParentDto parent : inscription.student().parents()) {
      sendEmail(
          new EmailConfigDto(parent.email(), "Confirmación de Inscripción - Complejo Educativo CIT",
              "<html>" + "<head>" + "<style> " + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" + ".container { background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0px 0px 10px #ccc; }" + "h1 { color: #4CAF50; }" + "p { font-size: 16px; }" + "</style>" + "</head>" + "<body>" + "<div class='container'>" + "<h3>Estimado/a " + parent.person()
                  .firstName() + " " + parent.person().firstSurname() + " " + parent.person()
                  .secondSurname() + "</h3>" + "<p>Nos complace informarle que el registro de inscripción de su hijo/a, ha sido exitosamente completado en el <strong>Complejo Educativo CIT</strong>.</p>" + "<h4>📌 Detalles del Registro:</h4>" + "<ul>" + "<li><strong>Estudiante:</strong> " + inscription.student()
                  .person().firstName() + " " + inscription.student().person()
                  .firstSurname() + " " + inscription.student().person()
                  .secondSurname() + "</li>" + "<li><strong>Grado/Nivel:</strong> " + gradoEsp + "</li>" + "<li><strong>Fecha de Examen:</strong> " + inscription.examDate() + "</li>" + "</ul>" + "<p>Para finalizar el proceso, le solicitamos que revise los documentos adjuntos y se comunique con nuestra administración en caso de dudas.</p>" + "<p><em>Este es un mensaje automático, por favor no responda a este correo.</em> Si requiere más información, puede contactarnos a <strong>" + emailContact + "</strong> o llamarnos al <strong>" + phoneContact + "</strong>.</p>" + "</div>" + "</body>" + "</html>"));
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

  public void createWhatsappMessage(EnrollmentDto inscription) {
    String emailContact = "";
    String phoneContact = "";
    String gradoEsp = "";

    for (SystemConfigEntity config : configRepository.getContactInfo()) {
      switch (config.getConfigName().name()) {
        case "EMAIL_CONTACT" -> emailContact = config.getConfigValue();
        case "OFFICE_CONTACT" -> phoneContact = config.getConfigValue();
      }
    }

    switch (inscription.gradeToEnroll().name()) {
      case "FIRST" -> gradoEsp = "Primero";
      case "SECOND" -> gradoEsp = "Segundo";
      case "THIRD" -> gradoEsp = "Tercero";
      case "FOURTH" -> gradoEsp = "Cuarto";
      case "FIFTH" -> gradoEsp = "Quinto";
      case "SIXTH" -> gradoEsp = "Sexto";
      case "SEVENTH" -> gradoEsp = "Sétimo";
      case "EIGHTH" -> gradoEsp = "Octavo";
      case "NINTH" -> gradoEsp = "Noveno";
      case "TENTH" -> gradoEsp = "Décimo";
      default -> gradoEsp = String.valueOf(inscription.gradeToEnroll());
    }

    for (ParentDto parent : inscription.student().parents()) {
      WhatsappConfigDto whatsappConfigDto = new WhatsappConfigDto();
      whatsappConfigDto.setRecipient(parent.phoneNumber());
      whatsappConfigDto.setMessage("""
          📢 *Confirmación de Registro - Complejo Educativo CIT*
          
          Estimado/a %s %s %s
          
          Nos alegra informarle que el registro de su hijo/a ha sido exitosamente completado ✅
          
          👨‍🎓 *Estudiante:* %s %s %s
          📚 *Grado/Nivel:* %s
          📅 *Fecha de Examen:* %s
          
          Para más detalles, puede comunicarse con nuestra administración:
          📧 %s
          📞 %s
          
          *¡Gracias por confiar en nosotros!* 🌟
          """.formatted(parent.person().firstName(), parent.person().firstSurname(),
          parent.person().secondSurname(), inscription.student().person().firstName(),
          inscription.student().person().firstSurname(),
          inscription.student().person().secondSurname(), gradoEsp, inscription.examDate(),
          emailContact, phoneContact));
      sendWhatsAppMessage(whatsappConfigDto);
    }
  }


  public void sendWhatsAppMessage(WhatsappConfigDto whatsappConfigDto) {
    Message.creator(new PhoneNumber("whatsapp:+506" + whatsappConfigDto.getRecipient()),
        new PhoneNumber(fromWhatsAppNumber), whatsappConfigDto.getMessage()).create();
  }

}
