package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Role;
import cr.co.ctpcit.citsacbackend.data.repositories.users.UserRepository;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageProperties;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CitSacBackEndApplication {

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
