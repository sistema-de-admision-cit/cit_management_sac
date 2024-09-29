package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Component
public class UserDto implements UserDetails {
  private Long id;
  @JsonIgnore
  private String password;
  private String username;
  private Role role;

  @Getter
  private Boolean isDefaultPassword = false;

  public UserDto(UserEntity userEntity) {
    this.id = userEntity.getId();
    this.password = userEntity.getUserPassword();
    this.username = userEntity.getEmail();
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
    return username;
  }
}
