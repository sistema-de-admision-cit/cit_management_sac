package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;

import java.util.List;

public interface EnglishExamService {
  List<EnglishExamEntity> getAll();

  EnglishExamEntity getById(Long id);

  EnglishExamEntity save(EnglishExamEntity englishExamEntity);

  void delete(Long id);
}
