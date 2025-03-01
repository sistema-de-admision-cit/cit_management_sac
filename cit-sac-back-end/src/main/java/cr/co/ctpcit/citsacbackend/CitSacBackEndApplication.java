package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.EmailConfigService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageProperties;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CitSacBackEndApplication {

  /*public static void main(String[] args)  {
      // Usa try-with-resources para cerrar el contexto automáticamente
      try (ConfigurableApplicationContext context = SpringApplication.run(CitSacBackEndApplication.class, args)) {

        // Obtén el bean del servicio de correo
        EmailConfigService emailService = context.getBean(EmailConfigService.class);

        // Crea un DTO con los datos del correo
        EmailConfigDto emailConfigDto = new EmailConfigDto();
        emailConfigDto.setRecipient("alessandrocd07@hotmail.com");
        emailConfigDto.setSubject("Confirmación de Registro - Complejo Educativo CIT");
        emailConfigDto.setMessage(
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
                        "<h3>Estimado/a [Nombre del Padre/Madre/Tutor],</h3>" +
                        "<p>Nos complace informarle que el registro de su hijo/a, <strong>[Nombre del Estudiante]</strong>, ha sido exitosamente completado en <strong>Complejo Educativo CIT</strong>.</p>" +
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
        );

        // Envía el correo con manejo de errores
        try {
          emailService.sendEmail(emailConfigDto);
          System.out.println("Correo enviado con éxito.");
        } catch (Exception e) {
          System.err.println("Error al enviar el correo: " + e.getMessage());
        }
      }
    }*/

  public static void main(String[] args) {
    SpringApplication.run(CitSacBackEndApplication.class, args);
  }

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
