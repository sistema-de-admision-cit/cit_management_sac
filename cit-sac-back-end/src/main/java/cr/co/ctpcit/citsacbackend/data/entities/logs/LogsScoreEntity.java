package cr.co.ctpcit.citsacbackend.data.entities.logs;

import cr.co.ctpcit.citsacbackend.data.enums.LogScoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_logs_score")
public class LogsScoreEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, columnDefinition = "INT UNSIGNED")
  private Long id;

  @Column(name = "process_id")
  private Integer processId;

  @Column(name = "tracktest_id")
  private Integer tracktestId;

  @Column(name = "enrollment_id", columnDefinition = "INT UNSIGNED")
  private Long enrollmentId;

  @Column(name = "previous_score")
  private Integer previousScore;

  @Column(name = "new_score")
  private Integer newScore;

  @Column(name = "exam_date")
  private LocalDate examDate;

  @Column(name = "timestamp")
  private Instant timestamp;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private LogScoreStatus status;

  @Lob
  @Column(name = "error_message")
  private String errorMessage;
}
