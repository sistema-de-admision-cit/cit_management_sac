package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.EmailConfigService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailConfigImplementation implements EmailConfigService {

    private final JavaMailSender mailSender;

    public EmailConfigImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailConfigDto emailConfigDto) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF8");
        helper.setTo(emailConfigDto.getRecipient());
        helper.setSubject(emailConfigDto.getSubject());
        helper.setText(emailConfigDto.getMessage(), true);

        mailSender.send(message);
    }

}
