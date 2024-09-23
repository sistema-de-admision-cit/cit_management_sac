package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.repositories.exam.english.EnglishExamRepository;
import org.springframework.stereotype.Service;

@Service
public class EnglishExamService {
  private final EnglishExamRepository englishExamRepository;

  public EnglishExamService(EnglishExamRepository englishExamRepository) {
    this.englishExamRepository = englishExamRepository;
  }
}
