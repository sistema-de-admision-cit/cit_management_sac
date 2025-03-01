package cr.co.ctpcit.citsacbackend.data.entities.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

}
