package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageProperties;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CitSacBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitSacBackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}
}
