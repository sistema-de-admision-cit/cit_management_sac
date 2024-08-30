package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_log")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Size(max = 50)
    @NotNull
    @Column(name = "column_name", nullable = false, length = 50)
    private String columnName;

    @NotNull
    @Lob
    @Column(name = "old_value", nullable = false)
    private String oldValue;

    @NotNull
    @Lob
    @Column(name = "new_value", nullable = false)
    private String newValue;

    @NotNull
    @Column(name = "changed_by", nullable = false)
    private Integer changedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "changed_at")
    private Instant changedAt;

    @Lob
    @Column(name = "query")
    private String query;

    @Size(max = 255)
    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

}