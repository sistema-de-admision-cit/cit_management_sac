package cr.co.ctpcit.citsacbackend.rest.config;


import cr.co.ctpcit.citsacbackend.logic.dto.config.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.EmailConfigService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
public class EmailConfigController {

    @Autowired
    EmailConfigService emailConfigService;

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailConfigDto emailConfigDto) throws MessagingException {
        emailConfigService.sendEmail(emailConfigDto);
        return new ResponseEntity<>("Email sent succesfuly", HttpStatus.OK);
    }
}
