package cr.co.ctpcit.citsacbackend.logic.services.notifs.smtp;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Configuration class for email-related properties and beans.
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.PropertySource
 */

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfiguration {

    /**
     * Creates and configures a PropertySourcesPlaceholderConfigurer bean.
     *
     * @return a configured PropertySourcesPlaceholderConfigurer instance
     * @see org.springframework.context.support.PropertySourcesPlaceholderConfigurer
     */

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

