package cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams;

import java.time.LocalDate;

public record EnglishExamLogDto(Integer processId, Integer enrollmentId, Integer trackTestExamId,
                                Integer previousScore, Integer newScore, LocalDate examDate,
                                String status, String errorMessage) {
}
