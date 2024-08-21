package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_log")
public class Log {
    @Id
    @Column(name = "log_id", nullable = false)
    private Integer id;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "column_name", nullable = false, length = 50)
    private String columnName;

    @Lob
    @Column(name = "old_value")
    private String oldValue;

    @Lob
    @Column(name = "new_value")
    private String newValue;

    @Column(name = "changed_by", nullable = false)
    private Integer changedBy;

    @Column(name = "changed_at", nullable = false, updatable = false, insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Instant changedAt;

    @Lob
    @Column(name = "query")
    private String query;

}