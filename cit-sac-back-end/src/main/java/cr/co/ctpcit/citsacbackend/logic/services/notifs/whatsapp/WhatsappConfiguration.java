package cr.co.ctpcit.citsacbackend.logic.services.notifs.whatsapp;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:whatsapp.properties")
public class WhatsappConfiguration {

    @Value("${twilio.whatsapp.from}")
    private String fromWhatsAppNumber;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}
