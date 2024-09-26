package cr.co.ctpcit.citsacbackend.data.entities.users;

import cr.co.ctpcit.citsacbackend.data.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  @JdbcTypeCode(SqlTypes.INTEGER)
  private Long id;

  @Size(max = 25)
  @NotNull
  @Column(name = "email", nullable = false, length = 25)
  private String email;

  @Size(max = 100)
  @NotNull
  @Column(name = "user_password", nullable = false, length = 100)
  private String userPassword;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private RoleType role;

}
