package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.EmailConfigService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageProperties;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import jakarta.mail.MessagingException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CitSacBackEndApplication {

  public static void main(String[] args) throws MessagingException {
    // Inicia el contexto de Spring
    ApplicationContext context = SpringApplication.run(CitSacBackEndApplication.class, args);

    // Obtén el bean del servicio de correo
    EmailConfigService emailService = context.getBean(EmailConfigService.class);

    // Crea un DTO con los datos del correo
    EmailConfigDto emailConfigDto = new EmailConfigDto();
    emailConfigDto.setRecipient("Calessandro0903@gmail.com");
    emailConfigDto.setSubject("Prueba de Correo desde el backend");
    emailConfigDto.setMessage("<h1>Mae ya almenos esta el flujo!</h1>");

    // Envía el correo
    emailService.sendEmail(emailConfigDto);

    System.out.println("Correo enviado con éxito.");
  }

  /*public static void main(String[] args) {
    SpringApplication.run(CitSacBackEndApplication.class, args);


  }*/

  @Bean
  CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      // storageService.deleteAll();
      storageService.init();
    };
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  /*@Bean
  CommandLineRunner saveTestUser(PasswordEncoder encoder, UserRepository userRepository) {
    try {
      return (args) -> {
        UserEntity user =
            new UserEntity(null, "rocio@cit.co.cr", encoder.encode("Mate8520").toString(), Role.T);
        userRepository.save(user);
      };
    } catch (Exception e) {
        return (args) -> {
            System.out.println("Error al guardar usuario de prueba");
        };
    }
  }*/
}
