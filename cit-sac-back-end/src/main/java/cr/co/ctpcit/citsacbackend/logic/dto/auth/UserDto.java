package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDto implements UserDetails {
  @Value("${cit.app.default-password}")
  private String defaultPassword;

  @JsonIgnore
  private String password;
  private String username;
  private Role role;

  public UserDto(String username, String password, Role role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public UserDto(UserEntity user) {
    this.username = user.getEmail();
    this.password = user.getUserPassword();
    this.role = user.getRole();
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

  @Override
  public boolean isEnabled() {
    return password.equals(defaultPassword);
  }
}
