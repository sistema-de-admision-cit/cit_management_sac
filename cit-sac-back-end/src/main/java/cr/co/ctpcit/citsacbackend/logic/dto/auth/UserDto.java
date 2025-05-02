package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Role;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


/**
 * Represents the Data Transfer Object (DTO) for a user.
 * This class implements {@link UserDetails} to provide user details for authentication in Spring Security.
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Component
public class UserDto implements UserDetails {

  private Long id;

  @JsonIgnore
  private String password;

  /**
   * The email address of the user, which must belong to the domain 'ctpcit.co.cr'.
   */
  @Pattern(regexp = "^[\\w.-]+@ctpcit\\.co\\.cr$",
          message = "El correo debe pertenecer al dominio ctpcit.co.cr")
  private String email;

  @JsonIgnore
  private String username;

  @JsonProperty("realUsername")
  private String realUsername;

  private Role role;

  @Builder.Default
  private Boolean isDefaultPassword = false;

  public UserDto(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.password = userEntity.getPassword();
    this.email = userEntity.getEmail();
    this.username = userEntity.getUsername();
    this.realUsername = userEntity.getUsername();
    this.role = userEntity.getRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
}

