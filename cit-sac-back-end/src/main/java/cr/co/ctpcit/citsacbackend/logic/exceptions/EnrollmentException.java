package cr.co.ctpcit.citsacbackend.logic.exceptions;

public class EnrollmentException extends RuntimeException {
  public EnrollmentException() {
    super();
  }

  public EnrollmentException(String message) {
    super(message);
  }

  public EnrollmentException(Throwable cause) {
    super(cause);
  }

  public EnrollmentException(String message, Throwable cause) {
    super(message, cause);
  }
}
