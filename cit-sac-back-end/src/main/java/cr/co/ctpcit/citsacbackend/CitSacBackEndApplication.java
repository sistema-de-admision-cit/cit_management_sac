package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
public class CitSacBackEndApplication {

  public static void main(String[] args) {
    SpringApplication.run(CitSacBackEndApplication.class, args);


  }

  @Bean
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(10);
    executor.setQueueCapacity(500);
    executor.setThreadNamePrefix("AsyncFileUploader - ");
    executor.initialize();
    return executor;
  }

  /*@Bean
  CommandLineRunner init(StorageService storageService) {
    return (args) -> {
      storageService.init();
    };
  }*/

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
