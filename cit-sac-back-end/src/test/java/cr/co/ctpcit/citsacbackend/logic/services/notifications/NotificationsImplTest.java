package cr.co.ctpcit.citsacbackend.logic.services.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.services.notifs.NotificationsImpl;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationsImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private SystemConfigRepository configRepository;

    @InjectMocks
    private NotificationsImpl notificationsService;

    @Test
    void testCreateEmailForInscription_FromJsonFile_SendsEmail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cr/co/ctpcit/citsacbackend/rest/inscriptions/InscriptionExistingDadJsonRequest.json");
        assertNotNull(inputStream, "No se encontró el archivo InscriptionExistingDadJsonRequest.json");

        EnrollmentDto inscription = objectMapper.readValue(inputStream, EnrollmentDto.class);

        SystemConfigEntity email = new SystemConfigEntity();
        email.setConfigName(Configurations.EMAIL_CONTACT);
        email.setConfigValue("contactcit@cit.co.cr");

        SystemConfigEntity phone = new SystemConfigEntity();
        phone.setConfigName(Configurations.OFFICE_CONTACT);
        phone.setConfigValue("22600000");

        when(configRepository.getContactInfo()).thenReturn(List.of(email, phone));

        MimeMessage message = new MimeMessage((Session) null);
        when(mailSender.createMimeMessage()).thenReturn(message);

        notificationsService.createEmailForInscription(inscription);

        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }

}

