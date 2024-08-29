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
@Table(name = "tbl_parentsguardians")
public class ParentsGuardianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_guardian_id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Size(max = 32)
    @NotNull
    @Column(name = "first_surname", nullable = false, length = 32)
    private String firstSurname;

    @Size(max = 32)
    @Column(name = "second_surname", length = 32)
    private String secondSurname;

    @NotNull
    @Lob
    @Column(name = "id_type", nullable = false)
    private String idType;

    @Size(max = 20)
    @NotNull
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "home_address", nullable = false, length = 100)
    private String homeAddress;

    @NotNull
    @Lob
    @Column(name = "relationship", nullable = false)
    private String relationship;

}