package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.exam.english.EnglishExamRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.EnrollmentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EnglishExamServiceImplementation implements EnglishExamService {
  private final EnglishExamRepository englishExamRepository;
  private final EnrollmentRepository enrollmentRepository;

  public EnglishExamServiceImplementation(EnglishExamRepository englishExamRepository,
      EnrollmentRepository enrollmentRepository) {
    this.englishExamRepository = englishExamRepository;
    this.enrollmentRepository = enrollmentRepository;
  }

  @Override
  public List<EnglishExamEntity> getAll() {
    return englishExamRepository.findAll();
  }

  @Override
  public EnglishExamEntity getById(Long id) {
    return englishExamRepository.findById(id).orElse(null);
  }

  @Override
  public EnglishExamEntity save(EnglishExamEntity englishExamEntity) {
    return englishExamRepository.save(englishExamEntity);
  }

  @Override
  public void delete(Long id) {
    englishExamRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void processEnglishScores(List<EnglishScoreEntryDTO> englishScores) {
    Instant now = Instant.now(); // process id for the stored procedure (tbl_logsscore)
    Integer processId = now.getNano();

    for (EnglishScoreEntryDTO score : englishScores) {
      // Normalizar nombres y apellidos
      String normalizedNames = normalizeString(score.names());
      String normalizedLastNames = normalizeString(score.lastNames());

      // Buscar la inscripción con nombres y apellidos normalizados
      Optional<EnrollmentEntity> enrollmentOpt =
          enrollmentRepository.usp_get_enrollment_id_for_english_test(normalizedNames,
              normalizedLastNames, score.lastTest());

      System.out.println("normalizedNames: " + enrollmentOpt);

      if (enrollmentOpt.isPresent()) {
        System.out.println(
            "Enrollment found for student: " + score.names() + " " + score.lastNames());
      }

      if (enrollmentOpt.isPresent()) {
        EnrollmentEntity enrollment = enrollmentOpt.get();

        // Crear una nueva entrada en la tabla de exámenes
        // instant instead of date

        EnglishExamEntity exam = new EnglishExamEntity();

        englishExamRepository.usp_process_english_exam(enrollment.getId(), score.id(),
            BigDecimal.valueOf(Double.parseDouble(score.core().replace("%", ""))),
            enrollment.getExamDate(), score.level(), processId);
      } else {
        // enrollment not found
        System.out.println(
            "Enrollment not found for student: " + score.names() + " " + score.lastNames());
      }
    }
  }

  // Método para normalizar cadenas (eliminar tildes, convertir a minúsculas)
  private String normalizeString(String input) {
    if (input == null)
      return null;
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{M}", "").toLowerCase();
  }
}
