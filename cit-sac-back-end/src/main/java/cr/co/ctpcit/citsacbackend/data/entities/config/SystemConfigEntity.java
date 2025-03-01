package cr.co.ctpcit.citsacbackend.data.entities.config;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_system_config")
public class SystemConfigEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "config_id", nullable = false)
  private Integer id;

  @Size(max = 32)
  @NotNull
  @Column(name = "config_name", nullable = false, length = 32)
  private String configName;

  @Size(max = 128)
  @NotNull
  @Column(name = "config_value", nullable = false, length = 128)
  private String configValue;

}
