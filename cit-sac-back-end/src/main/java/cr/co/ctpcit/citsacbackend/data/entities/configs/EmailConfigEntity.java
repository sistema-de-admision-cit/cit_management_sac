package cr.co.ctpcit.citsacbackend.data.entities.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfigEntity {

    @Value("${email.username}")
    private String usernameEmail;

    @Value("${email.password}")
    private String passwordEmail;

    @Value("${email.host}")
    private String mailHost;

    @Value("${email.port}")
    private int mailPort;

    @Value("${email.smtp.auth}")
    private boolean mailAuth;

    @Value("${email.smtp.starttls.enable}")
    private boolean starttlsEnable;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(usernameEmail);
        mailSender.setPassword(passwordEmail);
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", mailAuth);
        properties.put("mail.smtp.starttls.enable", starttlsEnable);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.debug", "true");

        return mailSender;
    }
}

