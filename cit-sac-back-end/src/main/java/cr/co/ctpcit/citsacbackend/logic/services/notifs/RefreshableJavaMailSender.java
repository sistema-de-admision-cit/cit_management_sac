package cr.co.ctpcit.citsacbackend.logic.services.notifs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;


@Component
public class RefreshableJavaMailSender implements JavaMailSender {


    private final SystemConfigService systemConfigService;
    private final JavaMailSenderImpl delegate;

    private final String host;
    private final int port;
    private final boolean smtpAuth;
    private final boolean starttlsEnable;
    private final String protocol;
    private final String debug;

    public RefreshableJavaMailSender(
            SystemConfigService systemConfigService,
            @Value("${email.host}") String host,
            @Value("${email.port}") int port,
            @Value("${email.smtp.auth}") boolean smtpAuth,
            @Value("${email.smtp.starttls.enable}") boolean starttlsEnable,
            @Value("${email.transport.protocol}") String protocol,
            @Value("${email.debug}") String debug
    ) {
        this.systemConfigService = systemConfigService;
        this.host = host;
        this.port = port;
        this.smtpAuth = smtpAuth;
        this.starttlsEnable = starttlsEnable;
        this.protocol = protocol;
        this.debug = debug;

        this.delegate = new JavaMailSenderImpl();
        configureDelegate();
    }

    private void configureDelegate() {
        // Obtener valores actualizados de configuración
        String username = systemConfigService.getConfigValue(Configurations.EMAIL_NOTIFICATION_CONTACT, false);
        String password = systemConfigService.getConfigValue(Configurations.EMAIL_PASSWORD, true);

        // Configurar el delegate (JavaMailSenderImpl)
        delegate.setHost(host);
        delegate.setPort(port);
        delegate.setUsername(username);
        delegate.setPassword(password);

        Properties props = delegate.getJavaMailProperties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.debug", debug);

    }

    @Override
    public MimeMessage createMimeMessage() {
        refreshCredentials();
        return delegate.createMimeMessage();
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return null;
    }


    @Override
    public void send(MimeMessage mimeMessage) {
        refreshCredentials();
        delegate.send(mimeMessage);
    }

    @Override
    public void send(MimeMessage... mimeMessages) {
        refreshCredentials();
        delegate.send(mimeMessages);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) {
        refreshCredentials();
        delegate.send(mimeMessagePreparator);
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) {
        refreshCredentials();
        delegate.send(mimeMessagePreparators);
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) {
        refreshCredentials();
        delegate.send(simpleMailMessage);
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) {
        refreshCredentials();
        delegate.send(simpleMailMessages);
    }

    private void refreshCredentials() {
        String currentEmail = systemConfigService.getConfigValue(Configurations.EMAIL_NOTIFICATION_CONTACT, false);
        String currentPassword = systemConfigService.getConfigValue(Configurations.EMAIL_PASSWORD, true);

        if (!delegate.getUsername().equals(currentEmail) || !delegate.getPassword().equals(currentPassword)) {
            delegate.setUsername(currentEmail);
            delegate.setPassword(currentPassword);
        }
    }
}
