package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.exam.english.EnglishExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglishExamServiceImplementation implements EnglishExamService {
  private final EnglishExamRepository englishExamRepository;

  public EnglishExamServiceImplementation(EnglishExamRepository englishExamRepository) {
    this.englishExamRepository = englishExamRepository;
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
}
