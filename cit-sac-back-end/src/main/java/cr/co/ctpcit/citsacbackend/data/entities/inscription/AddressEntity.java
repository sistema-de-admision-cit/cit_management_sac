package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Entity
@Table(name = "tbl_address")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class AddressEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  @JdbcTypeCode(SqlTypes.INTEGER)
  private Long id;

  @Column(name = "country")
  @Size(max = 16)
  @NotNull(message = "El país es obligatorio")
  @NotBlank(message = "El país es obligatorio")
  private String country;

  @Column(name = "province")
  @Size(max = 32)
  @NotNull(message = "La provincia es obligatoria")
  @NotBlank(message = "La provincia es obligatoria")
  private String province;

  @Column(name = "city")
  @Size(max = 32)
  @NotNull(message = "La ciudad es obligatoria")
  @NotBlank(message = "La ciudad es obligatoria")
  private String city;

  @Column(name = "district")
  @Size(max = 32)
  @NotNull(message = "El distrito es obligatorio")
  @NotBlank(message = "El distrito es obligatorio")
  private String district;

  @Column(name = "address_info")
  @Size(max = 64)
  @NotNull(message = "La dirección es obligatoria")
  @NotBlank(message = "La dirección es obligatoria")
  private String addressInfo;

  @NotNull(message = "Es obligatorio que exista un padre asociado")
  @ManyToOne
  @JoinColumn(name = "parent_guardian_id", nullable = false)
  private ParentsGuardianEntity parentGuardian;

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
    AddressEntity that = (AddressEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
        getClass().hashCode();
  }
}
