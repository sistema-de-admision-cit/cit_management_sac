package cr.co.ctpcit.citsacbackend.logic.dto.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record QuestionFilterSpec(String questionText, Boolean deleted, QuestionType questionType,
                                 Grades grade, QuestionLevel questionLevel)
    implements Specification<QuestionEntity> {
  @Override
  public Predicate toPredicate(Root<QuestionEntity> root, CriteriaQuery<?> query,
      CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (questionText != null && !questionText.isEmpty()) {
      predicates.add(cb.like(root.get("questionText"), "%" + questionText + "%"));
    }

    if (deleted != null) {
      predicates.add(cb.equal(root.get("deleted"), deleted));
    }

    if (questionType != null) {
      predicates.add(cb.equal(root.get("questionType"), questionType));
    }

    if (grade != null) {
      predicates.add(cb.equal(root.get("grade"), grade));
    }

    if (questionLevel != null) {
      predicates.add(cb.equal(root.get("questionLevel"), questionLevel));
    }

    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
