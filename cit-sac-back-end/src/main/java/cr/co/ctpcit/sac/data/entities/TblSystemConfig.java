package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_systemconfig")
public class TblSystemConfig {
    @Id
    @Column(name = "config_id", nullable = false)
    private Integer id;

    @Column(name = "config_name", nullable = false, length = 100)
    private String configName;

    @Column(name = "config_value", nullable = false)
    private String configValue;

}