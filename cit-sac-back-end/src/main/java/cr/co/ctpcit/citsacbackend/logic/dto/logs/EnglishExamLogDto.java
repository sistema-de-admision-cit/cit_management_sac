package cr.co.ctpcit.citsacbackend.logic.dto.logs;

import cr.co.ctpcit.citsacbackend.data.enums.LogScoreStatus;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the log of an English exam process.
 * Contains information about the process, enrollment, score changes, exam date, status, and any potential error messages.
 */
public record EnglishExamLogDto(Integer processId, Long enrollmentId, Integer trackTestExamId,
                                Integer previousScore, Integer newScore, LocalDate examDate,
                                LogScoreStatus status, String errorMessage) {
}
