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

/**
 * Utility class for handling operations related to exam results,
 * such as converting English levels to scores, retrieving exam entities,
 * and fetching configuration values for weighted calculations.
 */

@Component
@RequiredArgsConstructor
public class ResultUtils {

    private final SystemConfigRepository systemConfigRepository;


    /**
     * Retrieves the DAI exam associated with a given enrollment, if available.
     *
     * @param enrollment The enrollment entity containing exam details.
     * @return The DAI exam entity if present, otherwise null.
     */

    public static  DaiExamEntity getDaiExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.DAI)
                .findFirst()
                .map(ExamEntity::getDaiExam)
                .orElse(null);
    }

    /**
     * Retrieves the English exam associated with a given enrollment.
     *
     * @param enrollment The enrollment entity containing exam details.
     * @return The English exam entity.
     */

    public static EnglishExamEntity getEnglishExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.ENG)
                .findFirst()
                .map(ExamEntity::getEnglishExam)
                .orElseThrow();
    }

    /**
     * Retrieves the Academic exam associated with a given enrollment.
     *
     * @param enrollment The enrollment entity containing exam details.
     * @return The Academic exam entity.
     */

    public static AcademicExamEntity getAcademicExam(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getExamType() == ExamType.ACA)
                .findFirst()
                .map(ExamEntity::getAcademicExam)
                .orElseThrow();
    }

    /**
     * Retrieves the configuration value for a given configuration name from the system settings.
     * The configuration value is expected to be a numeric string that is converted to a BigDecimal.
     *
     * @param configName The name of the configuration (e.g., "englishWeight", "academicWeight").
     * @return The configuration value as a BigDecimal.
     * @throws RuntimeException If the configuration cannot be found in the system.
     */

    public  BigDecimal getConfigValue(String configName) {
        return systemConfigRepository.findByConfigName(Configurations.valueOf(configName))
                .map(c -> new BigDecimal(c.getConfigValue()))
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada: " + configName));
    }

}
