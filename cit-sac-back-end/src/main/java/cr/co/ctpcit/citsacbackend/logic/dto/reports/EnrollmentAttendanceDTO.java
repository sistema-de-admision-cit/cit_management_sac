package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.time.LocalDate;

public record EnrollmentAttendanceDTO(
    LocalDate enrollmentDate,
    String grade,
    String sector,
    int totalEnrolled,
    int totalAttended
) {}
