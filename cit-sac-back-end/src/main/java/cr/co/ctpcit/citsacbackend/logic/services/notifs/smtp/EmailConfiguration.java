package cr.co.ctpcit.citsacbackend.logic.services.notifs.smtp;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.services.EncryptionUtil;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfiguration {

  @Value("${email.host}")
  private String mailHost;

  @Value("${email.port}")
  private int mailPort;

  @Value("${email.smtp.auth}")
  private boolean mailAuth;

  @Value("${email.smtp.starttls.enable}")
  private boolean starttlsEnable;

  @Bean
  public JavaMailSender getMailSender(SystemConfigService systemConfigService) {
    String usernameEmail = systemConfigService.getConfigValue(Configurations.EMAIL_NOTIFICATION_CONTACT, false);
    String passwordEmail = systemConfigService.getConfigValue(Configurations.EMAIL_PASSWORD, true);

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

