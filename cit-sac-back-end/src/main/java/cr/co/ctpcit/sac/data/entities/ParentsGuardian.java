package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_parentsguardians")
public class ParentsGuardian {
    @Id
    @Column(name = "parent_guardian_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Lob
    @Column(name = "id_type", nullable = false)
    private String idType;

    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "home_address", nullable = false, length = 100)
    private String homeAddress;

    @Lob
    @Column(name = "relationship", nullable = false)
    private String relationship;

}