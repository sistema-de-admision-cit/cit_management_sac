package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_address")
public class AddressEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private ParentEntity parent;

  @Size(max = 32)
  @NotNull
  @Column(name = "country", nullable = false, length = 32)
  private String country;

  @Size(max = 32)
  @NotNull
  @Column(name = "province", nullable = false, length = 32)
  private String province;

  @Size(max = 32)
  @NotNull
  @Column(name = "city", nullable = false, length = 32)
  private String city;

  @Size(max = 32)
  @NotNull
  @Column(name = "district", nullable = false, length = 32)
  private String district;

  @Size(max = 128)
  @NotNull
  @ColumnDefault("'N/A'")
  @Column(name = "address_info", nullable = false, length = 128)
  private String addressInfo;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    AddressEntity that = (AddressEntity) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

}
