package cr.co.ctpcit.citsacbackend.data.entities.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_system_config")
public class SystemConfigEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "config_id", nullable = false)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "config_name", nullable = false,
      columnDefinition = "ENUM('EMAIL_CONTACT', 'EMAIL_NOTIFICATION_CONTACT', 'WHATSAPP_CONTACT', 'OFFICE_CONTACT', 'FACEBOOK_CONTACT', 'INSTAGRAM_CONTACT', 'PREV_GRADES_WEIGHT', 'ACADEMIC_WEIGHT', 'ENGLISH_WEIGHT')")
  private Configurations configName;

  @Size(max = 128)
  @NotNull
  @Column(name = "config_value", nullable = false, length = 128)
  private String configValue;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof SystemConfigEntity that))
      return false;

    if (!getId().equals(that.getId()))
      return false;
    if (!getConfigName().equals(that.getConfigName()))
      return false;
    return getConfigValue().equals(that.getConfigValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, configName, configValue);
  }
}
