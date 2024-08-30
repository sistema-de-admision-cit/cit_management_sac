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
@Table(name = "tbl_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 25)
    @NotNull
    @Column(name = "email", nullable = false, length = 25)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "user_password", nullable = false, length = 100)
    private String userPassword;

    @NotNull
    @Lob
    @Column(name = "role", nullable = false)
    private String role;

    //TODO: Create role as enum and map it correctly if needed and allowed by SpringSecurity
}