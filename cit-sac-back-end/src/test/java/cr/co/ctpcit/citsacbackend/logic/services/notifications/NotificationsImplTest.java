package cr.co.ctpcit.citsacbackend.logic.services.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.*;
import cr.co.ctpcit.citsacbackend.logic.services.notifs.NotificationsServiceImpl;
import cr.co.ctpcit.citsacbackend.logic.services.notifs.RefreshableJavaMailSender;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationsImplTest {

    @Mock
    private RefreshableJavaMailSender mailSender;

    @Mock
    private SystemConfigRepository configRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private NotificationsServiceImpl notificationsService;

    private EnrollmentDto testEnrollment;
    private SystemConfigEntity emailConfig;
    private SystemConfigEntity phoneConfig;
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() throws Exception {
        // Configurar MimeMessage mock
        mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Configurar datos de prueba basados en el JSON proporcionado
        PersonDto parentPerson = PersonDto.builder()
                .firstName("Elena")
                .firstSurname("Cordero")
                .secondSurname("Solano")
                .idType(IdType.DI)
                .idNumber("940123789")
                .build();

        ParentDto parent = ParentDto.builder()
                .person(parentPerson)
                .phoneNumber("896734320")
                .email("elena.cordero@example.com")
                .relationship(Relationship.M)
                .addresses(List.of(
                        AddressDto.builder()
                                .country("Costa Rica")
                                .province("Puntarenas")
                                .city("Esparza")
                                .district("Macacona")
                                .addressInfo("Detrás de la iglesia")
                                .build()
                ))
                .build();

        PersonDto studentPerson = PersonDto.builder()
                .firstName("Genio")
                .firstSurname("Fernandez")
                .secondSurname("Cordero")
                .idType(IdType.CC)
                .idNumber("503690412")
                .build();

        StudentDto student = StudentDto.builder()
                .person(studentPerson)
                .birthDate(LocalDate.of(2017, 12, 31))
                .previousSchool("Escuela Jose Figueres Ferrer")
                .hasAccommodations(false)
                .parents(List.of(parent))
                .build();

        testEnrollment = EnrollmentDto.builder()
                .student(student)
                .gradeToEnroll(Grades.FIRST)
                .examDate(LocalDate.of(2025, 3, 14))
                .build();

        // Configurar mocks para SystemConfigRepository
        emailConfig = new SystemConfigEntity();
        emailConfig.setConfigName(Configurations.EMAIL_CONTACT);
        emailConfig.setConfigValue("contacto@colegiocit.com");

        phoneConfig = new SystemConfigEntity();
        phoneConfig.setConfigName(Configurations.OFFICE_CONTACT);
        phoneConfig.setConfigValue("2222-5555");
    }

    @Test
    void createEmailForInscription_ShouldSendEmailToParent() {
        // Arrange
        when(configRepository.getContactInfo()).thenReturn(List.of(emailConfig, phoneConfig));

        // Act
        notificationsService.createEmailForInscription(testEnrollment);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void createEmailForInscription_ShouldUseCorrectEmailTemplate() throws MessagingException {
        // Arrange
        when(configRepository.getContactInfo()).thenReturn(List.of(emailConfig, phoneConfig));
        doNothing().when(mimeMessage).setContent(any(Multipart.class));

        // Act
        notificationsService.createEmailForInscription(testEnrollment);

        // Assert
        verify(mailSender).createMimeMessage();
        verify(mimeMessage).setRecipient(Message.RecipientType.TO, new InternetAddress("elena.cordero@example.com"));

        // Verificar el subject con el charset UTF-8
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> charsetCaptor = ArgumentCaptor.forClass(String.class);
        verify(mimeMessage).setSubject(subjectCaptor.capture(), charsetCaptor.capture());

        assertEquals("Confirmación de Inscripción - Complejo Educativo CIT", subjectCaptor.getValue());
        assertEquals("UTF-8", charsetCaptor.getValue());

        // Verificar contenido del email
        verify(mimeMessage).setContent(any(Multipart.class));
    }



    @Test
    void createEmailForInscription_ShouldHandleAllGradeLevels() throws MessagingException {
        // Arrange
        when(configRepository.getContactInfo()).thenReturn(List.of(emailConfig, phoneConfig));
        doNothing().when(mimeMessage).setContent(any(Multipart.class));

        for (Grades grade : Grades.values()) {
            EnrollmentDto enrollment = EnrollmentDto.builder()
                    .student(testEnrollment.student())
                    .gradeToEnroll(grade)
                    .examDate(LocalDate.of(2025, 3, 14))
                    .build();

            // Act
            notificationsService.createEmailForInscription(enrollment);
        }

        // Assert
        verify(mailSender, times(Grades.values().length)).send(mimeMessage);
    }

    @Test
    void createEmailForInscription_ShouldSendEmailForEachParent() throws MessagingException {
        // Arrange
        when(configRepository.getContactInfo()).thenReturn(List.of(emailConfig, phoneConfig));
        doNothing().when(mimeMessage).setContent(any(Multipart.class));

        // Agregar un segundo padre
        PersonDto secondParentPerson = PersonDto.builder()
                .firstName("Juan")
                .firstSurname("Fernandez")
                .secondSurname("Gomez")
                .idType(IdType.CC)
                .idNumber("123456789")
                .build();

        ParentDto secondParent = ParentDto.builder()
                .person(secondParentPerson)
                .phoneNumber("87654321")
                .email("juan.fernandez@example.com")
                .relationship(Relationship.F)
                .build();

        StudentDto studentWithTwoParents = StudentDto.builder()
                .person(testEnrollment.student().person())
                .birthDate(testEnrollment.student().birthDate())
                .previousSchool(testEnrollment.student().previousSchool())
                .hasAccommodations(testEnrollment.student().hasAccommodations())
                .parents(List.of(testEnrollment.student().parents().get(0), secondParent))
                .build();

        EnrollmentDto enrollment = EnrollmentDto.builder()
                .student(studentWithTwoParents)
                .gradeToEnroll(Grades.FIRST)
                .examDate(LocalDate.of(2025, 3, 14))
                .build();

        // Act
        notificationsService.createEmailForInscription(enrollment);

        // Assert
        verify(mailSender, times(2)).createMimeMessage();
        verify(mimeMessage, times(2)).setRecipient(eq(Message.RecipientType.TO), any(InternetAddress.class));
    }


}


