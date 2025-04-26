package cr.co.ctpcit.citsacbackend.data.utils;

import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ResultUtils {

    private final SystemConfigRepository systemConfigRepository;

    public static BigDecimal convertEnglishLevelToScore(EnglishLevel level) {
        return switch (level) {
            case C2 -> new BigDecimal("100");
            case C1 -> new BigDecimal("90");
            case B2 -> new BigDecimal("80");
            case B1 -> new BigDecimal("70");
            case A2 -> new BigDecimal("60");
            case A1 -> new BigDecimal("50");
        };
    }

    public static  DaiExamEntity getDaiExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.DAI)
                .findFirst()
                .map(ExamEntity::getDaiExam)
                .orElse(null);
    }

    public static EnglishExamEntity getEnglishExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.ENG)
                .findFirst()
                .map(ExamEntity::getEnglishExam)
                .orElseThrow();
    }

    public static AcademicExamEntity getAcademicExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.ACA)
                .findFirst()
                .map(ExamEntity::getAcademicExam)
                .orElseThrow();
    }

    public  BigDecimal getConfigValue(String configName) {
        return systemConfigRepository.findByConfigName(Configurations.valueOf(configName))
                .map(c -> new BigDecimal(c.getConfigValue()))
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada: " + configName));
    }

}
