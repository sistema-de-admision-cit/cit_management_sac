package cr.co.ctpcit.citsacbackend.logic.services.notifs;


import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentsStudentsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.EmailConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


/**
 * Service implementation for handling various types of notifications including
 * email communications related to student enrollment processes.
 * The service also handles localization of status messages and formatting of email content.
 */

@Service
public class NotificationsServiceImpl implements NotificationsService {

    /**
     * Content ID used for referencing the logo image in MIME multipart emails.
     * This CID must match exactly with the {@code src} attribute in the HTML content
     * (e.g., {@code <img src="cid:logo">}).
     */
    private static final String LOGO_CID = "logo";

    /**
     * Classpath resource path to the organization logo image file.
     * The image should be in WebP format and located in the resources/static/images directory.
     *
     * <p>Example location in project structure:
     * {@code src/main/resources/static/images/logo.png}</p>
     *
     * <p>Note: Some email clients or PDF generators may not support WebP format.
     * For maximum compatibility, consider using PNG or JPEG format instead.</p>
     */
    private static final String LOGO_PATH = "static/images/logo.png";
    /**
     * Mail sender component with refreshable configuration capabilities
     */
    private final RefreshableJavaMailSender mailSender;

    /**
     * Repository for accessing system configuration data
     */
    private final SystemConfigRepository configRepository;

    /**
     * Repository for accessing enrollment data
     */
    private final EnrollmentRepository enrollmentRepository;


    /**
     * Constructs a new NotificationsServiceImpl with required dependencies.
     *
     * @param mailSender           the mail sender component
     * @param configRepository     the system configuration repository
     * @param enrollmentRepository the enrollment data repository
     */
    public NotificationsServiceImpl(
            RefreshableJavaMailSender mailSender,
            SystemConfigRepository configRepository, EnrollmentRepository enrollmentRepository
    ) {
        this.mailSender = mailSender;
        this.configRepository = configRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Creates and sends enrollment confirmation email to all parents/guardians
     * associated with the student in the enrollment record.
     *
     * @param inscription the enrollment data transfer object containing
     *                    student and parent information
     * @throws RuntimeException if there's an error sending the email
     */

    @Override
    public void createEmailForInscription(EnrollmentDto inscription) {
        String emailContact = "";
        String phoneContact = "";
        String facebookLink = "";
        String instragramLink = "";
        String gradoEsp = "";

        for (SystemConfigEntity config : configRepository.getContactInfo()) {
            switch (config.getConfigName().name()) {
                case "EMAIL_CONTACT" -> emailContact = config.getConfigValue();
                case "OFFICE_CONTACT" -> phoneContact = config.getConfigValue();
                case "INSTAGRAM_CONTACT" -> instragramLink = config.getConfigValue();
                case "FACEBOOK_CONTACT" -> facebookLink = config.getConfigValue();
            }
        }

        switch (inscription.gradeToEnroll().name()) {
            case "FIRST" -> gradoEsp = "Primero";
            case "SECOND" -> gradoEsp = "Segundo";
            case "THIRD" -> gradoEsp = "Tercero";
            case "FOURTH" -> gradoEsp = "Cuarto";
            case "FIFTH" -> gradoEsp = "Quinto";
            case "SIXTH" -> gradoEsp = "Sexto";
            case "SEVENTH" -> gradoEsp = "Sétimo";
            case "EIGHTH" -> gradoEsp = "Octavo";
            case "NINTH" -> gradoEsp = "Noveno";
            case "TENTH" -> gradoEsp = "Décimo";
            default -> gradoEsp = String.valueOf(inscription.gradeToEnroll());
        }

        for (ParentDto parent : inscription.student().parents()) {
            String parentFullName = parent.person().firstName() + " " +
                    parent.person().firstSurname() +
                    (parent.person().secondSurname() != null ?
                            " " + parent.person().secondSurname() : "");

            String studentFullName = inscription.student().person().firstName() + " " +
                    inscription.student().person().firstSurname() +
                    (inscription.student().person().secondSurname() != null ?
                            " " + inscription.student().person().secondSurname() : "");

            sendEmail(
                    new EmailConfigDto(
                            parent.email(),
                            "Confirmación de Inscripción - Complejo Educativo CIT",
                            buildInscriptionEmailContent(
                                    parentFullName,
                                    studentFullName,
                                    gradoEsp,
                                    inscription.examDate(),
                                    emailContact,
                                    phoneContact,
                                    instragramLink,
                                    facebookLink
                            )
                    )
            );
        }
    }

    /**
     * Builds HTML content for the enrollment confirmation email.
     *
     * @param parentFullName the full name of the parent/guardian
     * @param studentFullName the full name of the student
     * @param gradoEsp the grade level in Spanish
     * @param examDate the scheduled exam date
     * @param emailContact the school's contact email
     * @param phoneContact the school's contact phone number
     * @param instagramContact the school's contact instagram
     * @param facebookContact the school's contact facebook
     * @return formatted HTML email content
     */

    private String buildInscriptionEmailContent(
            String parentFullName, String studentFullName,
            String gradoEsp, LocalDate examDate,
            String emailContact, String phoneContact,
            String instagramContact, String facebookContact
    ) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                ".email-container { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; margin: 0 auto; }" +
                ".header-image { text-align: center; margin-bottom: 20px; }" +
                ".header-image img { max-width: 200px; height: auto; display: block; margin: 0 auto; }" +
                "h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-top: 0; }" +
                "h2 { color: #3498db; }" +
                ".details { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }" +
                ".detail-item { margin-bottom: 8px; }" +
                ".footer { margin-top: 20px; font-size: 14px; color: #7f8c8d; }" +
                ".social-links { margin-top: 15px; }" +
                ".social-link { display: inline-block; margin-right: 15px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='header-image'>" +
                "<img src='cid:logo' alt='Logo' style='max-width:200px; display:block;'>" +
                "</div>" +
                "<h1>Confirmación de Inscripción del Complejo Educativo CIT</h1>" +
                "<p>Estimado/a <strong>" + parentFullName + "</strong>,</p>" +
                "<p>Nos complace informarle que el registro de inscripción de su hijo/a ha sido exitosamente completado en el <strong>Complejo Educativo CIT</strong>.</p>" +

                "<div class='details'>" +
                "<h2>Detalles del Registro:</h2>" +
                "<div class='detail-item'><strong>Estudiante:</strong> " + studentFullName + "</div>" +
                "<div class='detail-item'><strong>Grado/Nivel:</strong> " + gradoEsp + "</div>" +
                "<div class='detail-item'><strong>Fecha de Examen:</strong> " + examDate + "</div>" +
                "</div>" +

                "<p>Para finalizar el proceso, le solicitamos que revise los documentos adjuntos y se comunique con nuestra administración en caso de dudas.</p>" +

                "<div class='social-links'>" +
                "<p>Manténgase informado de nuestras noticias:</p>" +
                "<div class='social-link'><a href='" + facebookContact + "' target='_blank'>Facebook</a></div>" +
                "<div class='social-link'><a href='" + instagramContact + "' target='_blank'>Instagram</a></div>" +
                "</div>" +

                "<div class='footer'>" +
                "<p><em>Este es un mensaje automático. Por favor no responda a este correo.</em></p>" +
                "<p>Para asistencia, contáctenos a: <a href='mailto:" + emailContact + "'>" + emailContact + "</a> o al teléfono: " + phoneContact + "</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Creates and sends email notifications to parents/guardians when
     * an enrollment record is updated.
     *
     * @param enrollmentId the ID of the enrollment being updated
     * @param updateDto the data transfer object containing update information
     * @throws RuntimeException if enrollment is not found or email sending fails
     */

    @Override
    public void createEmailForEnrollmentUpdate(Long enrollmentId, EnrollmentUpdateDto updateDto) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("No se encontró inscripción con ID: " + enrollmentId));

        String emailContact = "";
        String phoneContact = "";
        String facebookLink = "";
        String instragramLink = "";


        for (SystemConfigEntity config : configRepository.getContactInfo()) {
            switch (config.getConfigName().name()) {
                case "EMAIL_CONTACT" -> emailContact = config.getConfigValue();
                case "OFFICE_CONTACT" -> phoneContact = config.getConfigValue();
                case "INSTAGRAM_CONTACT" -> instragramLink = config.getConfigValue();
                case "FACEBOOK_CONTACT" -> facebookLink = config.getConfigValue();
            }
        }

        String statusEsp = switch (updateDto.status().name()) {
            case "ACCEPTED" -> "Aceptado";
            case "REJECTED" -> "Rechazado";
            case "PENDING" -> "Pendiente";
            case "ELIGIBLE" -> "Elegible";
            case "INELIGIBLE" -> "NO Elegible";
            default -> updateDto.status().name();
        };

        // Obtener padres del estudiante
        List<ParentEntity> parents = enrollment.getStudent().getParents().stream()
                .map(ParentsStudentsEntity::getParent)
                .toList();

        for (ParentEntity parent : parents) {
            sendEmail(
                    new EmailConfigDto(
                            parent.getEmail(),
                            "Actualización de Inscripción - Complejo Educativo CIT",
                            buildUpdateEmailContent(parent, enrollment.getStudent(), updateDto, statusEsp, emailContact, phoneContact, instragramLink,
                                    facebookLink)
                    )
            );
        }
    }

    /**
     * Builds HTML content for the enrollment update email.
     *
     * @param parent the parent entity receiving the notification
     * @param student the student entity associated with the enrollment
     * @param updateDto the enrollment update data
     * @param statusEsp the process status in Spanish
     * @param emailContact the school's contact email
     * @param phoneContact the school's contact phone number
     * @return formatted HTML email content
     */

    private String buildUpdateEmailContent(
            ParentEntity parent, StudentEntity student,
            EnrollmentUpdateDto updateDto, String statusEsp, String emailContact, String phoneContact,
            String instagramContact, String facebookContact
    ) {

        String parentFullName = parent.getParentPerson().getFirstName() + " " +
                parent.getParentPerson().getFirstSurname() +
                (parent.getParentPerson().getSecondSurname() != null ?
                        " " + parent.getParentPerson().getSecondSurname() : "");

        String studentFullName = student.getStudentPerson().getFirstName() + " " +
                student.getStudentPerson().getFirstSurname() +
                (student.getStudentPerson().getSecondSurname() != null ?
                        " " + student.getStudentPerson().getSecondSurname() : "");

        String whatsappStatus = updateDto.whatsappPermission() ? "ACTIVADO" : "DESACTIVADO";

        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                ".email-container { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; margin: 0 auto; }" +
                ".header-image { text-align: center; margin-bottom: 20px; }" +
                ".header-image img { max-width: 200px; height: auto; display: block; margin: 0 auto; }" +
                "h1 { color: #2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-top: 0; }" +
                "h2 { color: #3498db; }" +
                ".changes { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }" +
                ".change-item { margin-bottom: 8px; }" +
                ".footer { margin-top: 20px; font-size: 14px; color: #7f8c8d; }" +
                ".social-links { margin-top: 15px; }" +
                ".social-link { display: inline-block; margin-right: 15px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='header-image'>" +
                "<img src='cid:logo' alt='Logo' style='max-width:200px; display:block;'>" +
                "</div>" +
                "<h1>Actualización de Inscripción del Complejo Educativo CIT</h1>" +
                "<p>Estimado/a <strong>" + parentFullName + "</strong>,</p>" +
                "<p>Le informamos que se han realizado modificaciones en la inscripción de su hijo/a <strong>" + studentFullName + "</strong> en el Complejo Educativo CIT.</p>" +

                "<div class='changes'>" +
                "<h2>Cambios realizados:</h2>" +
                "<div class='change-item'><strong>Fecha de Examen:</strong> " + updateDto.examDate() + "</div>" +
                "<div class='change-item'><strong>Estado del Proceso:</strong> " + statusEsp + "</div>" +
                "<div class='change-item'><strong>Notas Anteriores:</strong> " + updateDto.previousGrades() + "</div>" +
                "<div class='change-item'><strong>Notificaciones por WhatsApp:</strong> " + whatsappStatus + "</div>" +
                "</div>" +

                "<p>Si necesitas más información, por favor contáctenos.</p>" +

                "<div class='social-links'>" +
                "<p>Manténgase informado de nuestras noticias:</p>" +
                "<div class='social-link'><a href='" + facebookContact + "' target='_blank'>Facebook</a></div>" +
                "<div class='social-link'><a href='" + instagramContact + "' target='_blank'>Instagram</a></div>" +
                "</div>" +

                "<div class='footer'>" +
                "<p><em>Este es un mensaje automático. Por favor no responda a este correo.</em></p>" +
                "<p>Para asistencia, contáctenos a: <a href='mailto:" + emailContact + "'>" + emailContact + "</a> o al teléfono: " + phoneContact + "</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Creates and sends admission decision notifications to parents/guardians.
     *
     * @param studentIdNumber the national ID number of the student
     * @throws RuntimeException if enrollment is not found or email sending fails
     */

    @Override
    public void createEmailForAdmissionDecision(String studentIdNumber) {
        EnrollmentEntity enrollment = enrollmentRepository.findByStudentStudentPersonIdNumber(studentIdNumber)
                .orElseThrow(() -> new RuntimeException("No se encontró inscripción para el estudiante con cédula: " + studentIdNumber));

        String emailContact = "";
        String phoneContact = "";
        String facebookLink = "";
        String instragramLink = "";


        for (SystemConfigEntity config : configRepository.getContactInfo()) {
            switch (config.getConfigName().name()) {
                case "EMAIL_CONTACT" -> emailContact = config.getConfigValue();
                case "OFFICE_CONTACT" -> phoneContact = config.getConfigValue();
                case "INSTAGRAM_CONTACT" -> instragramLink = config.getConfigValue();
                case "FACEBOOK_CONTACT" -> facebookLink = config.getConfigValue();
            }
        }

        String gradoEsp = switch (enrollment.getGradeToEnroll().name()) {
            case "FIRST" -> "Primero";
            case "SECOND" -> "Segundo";
            case "THIRD" -> "Tercero";
            case "FOURTH" -> "Cuarto";
            case "FIFTH" -> "Quinto";
            case "SIXTH" -> "Sexto";
            case "SEVENTH" -> "Sétimo";
            case "EIGHTH" -> "Octavo";
            case "NINTH" -> "Noveno";
            case "TENTH" -> "Décimo";
            default -> enrollment.getGradeToEnroll().name();
        };

        String statusEsp = switch (enrollment.getStatus().name()) {
            case "ACCEPTED" -> "Aceptado";
            case "REJECTED" -> "Rechazado";
            case "PENDING" -> "Pendiente";
            case "ELIGIBLE" -> "Elegible";
            case "INELIGIBLE" -> "No Elegible";
            default -> enrollment.getStatus().name();
        };

        List<ParentEntity> parents = enrollment.getStudent().getParents().stream()
                .map(ParentsStudentsEntity::getParent)
                .toList();

        for (ParentEntity parent : parents) {
            String decisionMessage = enrollment.getStatus() == ProcessStatus.ACCEPTED ?
                    "<p>¡Felicitaciones! Su hijo/a ha sido <strong>" + statusEsp + "</strong> en el Complejo Educativo CIT.</p>" +
                            "<p>Por favor esté atento a futuras comunicaciones con las instrucciones para completar el proceso de matrícula.</p>" :
                    "<p>Lamentamos informarle que su hijo/a no ha sido aceptado en esta ocasión.</p>" +
                            "<p>Si desea conocer más detalles sobre esta decisión o sobre posibles opciones futuras, no dude en contactarnos.</p>";

            String parentFullName = parent.getParentPerson().getFirstName() + " " +
                    parent.getParentPerson().getFirstSurname() +
                    (parent.getParentPerson().getSecondSurname() != null ?
                            " " + parent.getParentPerson().getSecondSurname() : "");

            String studentFullName = enrollment.getStudent().getStudentPerson().getFirstName() + " " +
                    enrollment.getStudent().getStudentPerson().getFirstSurname() +
                    (enrollment.getStudent().getStudentPerson().getSecondSurname() != null ?
                            " " + enrollment.getStudent().getStudentPerson().getSecondSurname() : "");

            sendEmail(
                    new EmailConfigDto(
                            parent.getEmail(),
                            "Resultado de Admisión - Complejo Educativo CIT",
                            buildDecisionEmailContent(
                                    parentFullName,
                                    studentFullName,
                                    gradoEsp,
                                    statusEsp,
                                    decisionMessage,
                                    emailContact,
                                    phoneContact,
                                    instragramLink,
                                    facebookLink,
                                    enrollment.getStatus()
                            )
                    )
            );
        }
    }

    /**
     * Builds HTML content for the admission decision email.
     *
     * @param parentFullName the full name of the parent/guardian
     * @param studentFullName the full name of the student
     * @param gradoEsp the grade level in Spanish
     * @param statusEsp the admission status in Spanish
     * @param decisionMessage the main decision message content
     * @param emailContact the school's contact email
     * @param phoneContact the school's contact phone number
     * @param status the admission process status
     * @return formatted HTML email content
     */

    private String buildDecisionEmailContent(
            String parentFullName, String studentFullName,
            String gradoEsp, String statusEsp, String decisionMessage,
            String emailContact, String phoneContact, String instagramContact,
            String facebookContact, ProcessStatus status

    ) {
        return "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                ".container { background-color: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 600px; margin: 0 auto; }" +
                ".header-image { text-align: center; margin-bottom: 20px; }" +
                ".header-image img { max-width: 200px; height: auto; }" +
                "h1 { color: " + (status == ProcessStatus.ACCEPTED ? "#4CAF50" : "#f44336") + "; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-top: 20px; }" +
                "h2 { color: #3498db; }" +
                ".details { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin: 15px 0; }" +
                ".detail-item { margin-bottom: 8px; }" +
                ".footer { margin-top: 20px; font-size: 14px; color: #7f8c8d; }" +
                ".social-links { margin-top: 15px; }" +
                ".social-link { display: inline-block; margin-right: 15px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header-image'>" +
                "<img src='cid:logo' alt='Logo' style='max-width:200px; display:block;'>" +
                "</div>" +
                "<h1>Resultado del Proceso de Admisión del Complejo Educativo CIT</h1>" +
                "<p>Estimado/a <strong>" + parentFullName + "</strong>,</p>" +
                "<p>El proceso de admisión para su hijo/a en el <strong>Complejo Educativo CIT</strong> ha finalizado.</p>" +
                decisionMessage +

                "<div class='details'>" +
                "<h2>Detalles del Proceso:</h2>" +
                "<div class='detail-item'><strong>Estudiante:</strong> " + studentFullName + "</div>" +
                "<div class='detail-item'><strong>Grado/Nivel:</strong> " + gradoEsp + "</div>" +
                "<div class='detail-item'><strong>Estado Final:</strong> " + statusEsp + "</div>" +
                "</div>" +

                "<div class='social-links'>" +
                "<p>Manténgase informado de nuestras noticias:</p>" +
                "<div class='social-link'><a href='" + facebookContact + "' target='_blank'>Facebook</a></div>" +
                "<div class='social-link'><a href='" + instagramContact + "' target='_blank'>Instagram</a></div>" +
                "</div>" +

                "<div class='footer'>" +
                "<p><em>Este es un mensaje automático. Por favor no responda a este correo.</em></p>" +
                "<p>Para asistencia, contáctenos a: <a href='mailto:" + emailContact + "'>" + emailContact + "</a> o al teléfono: " + phoneContact + "</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Sends an email using the provided configuration.
     *
     * @param emailConfigDto the email configuration data transfer object containing:
     *                       - recipient email address
     *                       - email subject
     *                       - HTML message content
     * @throws RuntimeException if there's an error creating or sending the email
     */

    @Override
    public void sendEmail(EmailConfigDto emailConfigDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String fromEmail = mailSender.getUsername();
            helper.setFrom(fromEmail);
            helper.setTo(emailConfigDto.getRecipient());
            helper.setSubject(emailConfigDto.getSubject());


            String logoBase64 = getLogoAsBase64();

            String htmlContent = emailConfigDto.getMessage()
                    .replace("${logoBase64}", logoBase64);

            String outlookCompatibleHtml = htmlContent
                    .replace("<!--[if !mso]><!-- -->", "")
                    .replace("<!--<![endif]-->", "")
                    .replace("<!--[if gte mso 9]>", "")
                    .replace("<![endif]-->", "");

            helper.setText(outlookCompatibleHtml, true);

            ClassPathResource logoResource = new ClassPathResource(LOGO_PATH);
            if (logoResource.exists()) {
                helper.addInline(LOGO_CID, logoResource, "image/png");
            }
            mailSender.send(message);
        } catch (AuthenticationFailedException e) {
            System.err.println("Error de autenticación al enviar el correo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    /**
     * Converts the logo image to a Base64 encoded string.
     *
     * <p>This method reads the logo file from the classpath resource location specified by {@code LOGO_PATH},
     * converts it to a byte array, and then encodes it as a Base64 string. This is useful for embedding
     * the logo directly in HTML content without requiring separate file attachments.</p>
     *
     * @return Base64 encoded string representation of the logo image, or an empty string if the logo
     *         cannot be read or converted
     * @throws IOException if there is an error reading the logo file from the resources
     *
     * @see java.util.Base64
     * @see org.springframework.util.FileCopyUtils
     * @see org.springframework.core.io.ClassPathResource
     */

    private String getLogoAsBase64() {
        try {
            ClassPathResource logoResource = new ClassPathResource(LOGO_PATH);
            byte[] imageBytes = FileCopyUtils.copyToByteArray(logoResource.getInputStream());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.err.println("Error al convertir logo a base64: " + e.getMessage());
            return "";
        }
    }


}
