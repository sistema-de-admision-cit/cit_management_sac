package cr.co.ctpcit.citsacbackend.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        reason = "Ya existe un registro para hacer examen de admisión en la misma fecha.",
        value = HttpStatus.CONFLICT
)
public class SameDateEnrollmentException extends RuntimeException {
    public SameDateEnrollmentException() {super();}
    public SameDateEnrollmentException(String message) {super(message);}
    public SameDateEnrollmentException(Throwable cause) {super(cause);}
    public SameDateEnrollmentException(String message, Throwable cause) {super(message, cause);}
}
