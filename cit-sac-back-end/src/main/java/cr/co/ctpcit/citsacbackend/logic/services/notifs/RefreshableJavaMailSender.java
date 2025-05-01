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

/**
 * A refreshable implementation of {@link JavaMailSender} that dynamically updates email credentials
 * from the system configuration before sending emails.
 * This component decorates a {@link JavaMailSenderImpl} delegate and provides the ability
 * to refresh SMTP credentials at runtime without restarting the application. Credentials
 * are automatically checked and updated before each email sending operation.
 * <p><b>Thread Safety:</b> This class is thread-safe for sending emails as it refreshes
 * credentials for each operation, though the underlying JavaMailSender implementation
 * should also be thread-safe.</p>
 */

@Component
public class RefreshableJavaMailSender implements JavaMailSender {


    /**
     * Service for retrieving current system configuration values
     */
    private final SystemConfigService systemConfigService;

    /**
     * The underlying JavaMailSender implementation that handles actual email operations
     */
    private final JavaMailSenderImpl delegate;

    /**
     * SMTP server hostname configured from application properties
     */
    private final String host;

    /**
     * SMTP server port configured from application properties
     */
    private final int port;

    /**
     * SMTP authentication flag configured from application properties
     */
    private final boolean smtpAuth;

    /**
     * STARTTLS enablement flag configured from application properties
     */
    private final boolean starttlsEnable;

    /**
     * Email transport protocol configured from application properties
     */
    private final String protocol;

    /**
     * JavaMail debug flag configured from application properties
     */
    private final String debug;

    /**
     * Constructs a new RefreshableJavaMailSender with the specified configuration.
     *
     * @param systemConfigService the service for accessing system configuration
     * @param host the SMTP server hostname
     * @param port the SMTP server port
     * @param smtpAuth whether SMTP authentication is required
     * @param starttlsEnable whether STARTTLS should be enabled
     * @param protocol the email transport protocol to use
     * @param debug whether JavaMail debugging is enabled ("true"/"false")
     */
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

    /**
     * Configures the underlying JavaMailSender delegate with current settings.
     * <p>
     * This method initializes the delegate with:
     * <ul>
     *   <li>SMTP server connection details (host/port)</li>
     *   <li>Current credentials from system configuration</li>
     *   <li>JavaMail properties for authentication and encryption</li>
     * </ul>
     */

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


    /**
     * {@inheritDoc}
     * <p>
     * Refreshes credentials before creating the message to ensure current credentials are used.
     */

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

    /**
     * Refreshes the SMTP credentials from system configuration if they have changed.
     * Compares current delegate credentials with system configuration and updates
     * them if different. This ensures email operations always use the most recent
     * credentials without requiring application restart.
     */

    private void refreshCredentials() {
        String currentEmail = systemConfigService.getConfigValue(Configurations.EMAIL_NOTIFICATION_CONTACT, false);
        String currentPassword = systemConfigService.getConfigValue(Configurations.EMAIL_PASSWORD, true);

        if (!delegate.getUsername().equals(currentEmail) || !delegate.getPassword().equals(currentPassword)) {
            delegate.setUsername(currentEmail);
            delegate.setPassword(currentPassword);
        }
    }
}
