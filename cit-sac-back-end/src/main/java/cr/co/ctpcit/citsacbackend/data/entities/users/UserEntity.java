package cr.co.ctpcit.citsacbackend.data.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cr.co.ctpcit.citsacbackend.data.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tbl_users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  @JdbcTypeCode(SqlTypes.INTEGER)
  private Long id;

  @Size(max = 128)
  @NotNull
  @Column(name = "email", nullable = false, length = 128)
  private String email;

  @Size(max = 64)
  @NotNull
  @Column(name = "username", nullable = false, length = 64)
  private String username;

  @Size(max = 128)
  @NotNull
  @Column(name = "user_password", nullable = false, length = 128)
  @JsonIgnore
  private String password;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false,
      columnDefinition = "enum('SYS', 'ADMIN', 'TEACHER', 'PSYCHOLOGIST')")
  private Role role;

  @Override
  public final boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null)
      return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ?
        ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() :
        o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() :
        this.getClass();
    if (thisEffectiveClass != oEffectiveClass)
      return false;
    UserEntity that = (UserEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
        getClass().hashCode();
  }
}
