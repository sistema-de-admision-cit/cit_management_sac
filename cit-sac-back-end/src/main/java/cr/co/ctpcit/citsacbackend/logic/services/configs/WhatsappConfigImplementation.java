package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.logic.dto.configs.WhatsappConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class WhatsappConfigImplementation implements WhatsappConfigService {

    public void createWhatsappMessage(EnrollmentDto inscription){
        WhatsappConfigDto whatsappConfigDto = new WhatsappConfigDto();
        for (ParentDto parent : inscription.student().parents()) {
             whatsappConfigDto.setRecipient(parent.phoneNumber());
             whatsappConfigDto.setMessage(
                  "Hola es una prueba desde el Backend"
             );
        }
    }

  public void sendWhatsAppMessage(WhatsappConfigDto whatsappConfigDto){
          Message.creator( new PhoneNumber("+"+whatsappConfigDto.getRecipient()),
                  new PhoneNumber(whatsappConfigDto.getFromWhatsAppNumber()),
                  whatsappConfigDto.getMessage()
          ).create();
  }
}
