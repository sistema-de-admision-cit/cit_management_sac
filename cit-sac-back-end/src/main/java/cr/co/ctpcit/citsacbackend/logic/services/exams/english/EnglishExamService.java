package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.exam.english.EnglishExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglishExamService {
  private final EnglishExamRepository englishExamRepository;

  public EnglishExamService(EnglishExamRepository englishExamRepository) {
    this.englishExamRepository = englishExamRepository;
  }

  public List<EnglishExamEntity> getAll() {
    return englishExamRepository.findAll();
  }

  public EnglishExamEntity getById(Long id) {
    return englishExamRepository.findById(id).orElse(null);
  }

  public EnglishExamEntity save(EnglishExamEntity englishExamEntity) {
    return englishExamRepository.save(englishExamEntity);
  }

  public void delete(Long id) {
    englishExamRepository.deleteById(id);
  }
}
