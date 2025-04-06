package cr.co.ctpcit.citsacbackend.logic.dto.logs;

import cr.co.ctpcit.citsacbackend.data.enums.LogScoreStatus;

import java.time.LocalDate;

public record EnglishExamLogDto(Integer processId, Long enrollmentId, Integer trackTestExamId,
                                Integer previousScore, Integer newScore, LocalDate examDate,
                                LogScoreStatus status, String errorMessage) {
}
