package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_users")
public class TblUser {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 25)
    private String email;

    @Column(name = "user_password", nullable = false, length = 100)
    private String userPassword;

    @Lob
    @Column(name = "role", nullable = false)
    private String role;

}