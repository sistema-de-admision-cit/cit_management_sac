package cr.co.ctpcit.citsacbackend.data.entities.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_englishexams")
public class EnglishExamEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_id", nullable = false)
  private Integer id;

  @NotNull(message = "Es obligatorio indicar el id del tracktest")
  @Column(name = "tracktest_id", nullable = false)
  private BigInteger tracktestId;

  @NotNull(message = "Es obligatorio agregar el enrollment")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "enrollment_id", nullable = false)
  private EnrollmentEntity enrollment;

  @NotNull(message = "Es obligatorio agregar la fecha del examen")
  @Column(name = "exam_date", nullable = false)
  private LocalDate examDate;

  @NotNull(message = "Es obligatorio agregar el nivel de inglés")
  @Enumerated(EnumType.STRING)
  @Column(name = "level", nullable = false)
  private EnglishLevel level;

  @NotNull(message = "Es obligatorio agregar la nota del examen")
  @Column(name = "grade", nullable = false, precision = 5, scale = 2)
  private BigDecimal grade;

}
