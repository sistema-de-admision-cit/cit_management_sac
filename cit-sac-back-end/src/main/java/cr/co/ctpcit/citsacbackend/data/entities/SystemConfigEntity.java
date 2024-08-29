package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_systemconfig")
public class SystemConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "config_name", nullable = false, length = 100)
    private String configName;

    @Size(max = 255)
    @NotNull
    @Column(name = "config_value", nullable = false)
    private String configValue;

}