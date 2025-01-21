package cr.co.ctpcit.citsacbackend.data.entities.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties") //Que utilice una propiedad
public class EmailConfigEntity {
    @Value("${email.username}")
    private String usernameEmail;

    @Value("${email.password}")
    private String passwordEmail;

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getMailProperties());
        mailSender.setUsername(usernameEmail);
        mailSender.setPassword(passwordEmail);
        return mailSender;
    }

    @Bean
    public ResourceLoader getResourceLoader() {
        return new DefaultResourceLoader();
    }
}
