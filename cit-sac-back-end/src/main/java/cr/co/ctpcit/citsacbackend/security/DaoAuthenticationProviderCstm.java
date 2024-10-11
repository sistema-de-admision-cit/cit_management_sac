package cr.co.ctpcit.citsacbackend.security;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class DaoAuthenticationProviderCstm extends DaoAuthenticationProvider {
  @Value("${cit.app.default-password}")
  String defaultPassword;

  public DaoAuthenticationProviderCstm() {
    super();
  }

  public DaoAuthenticationProviderCstm(PasswordEncoder passwordEncoder,
      UserDetailsService userDetailsService) {
    super(passwordEncoder);
    this.setUserDetailsService(userDetailsService);
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    super.additionalAuthenticationChecks(userDetails, authentication);
    String presentedPassword = authentication.getCredentials().toString();
    ((UserDto) userDetails).setIsDefaultPassword(presentedPassword.equals(defaultPassword));
  }

  public void updatePassword(ChangePasswordRequestDTO changePasswordRequest) {
    // validate if the new passwords matches
    if (!changePasswordRequest.newPassword().equals(changePasswordRequest.confirmPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden.");
    }

    // validate that the new password is not the same as the current password
    if (changePasswordRequest.newPassword().equals(changePasswordRequest.currentPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "La nueva contraseña no puede ser igual a la actual.");
    }

    // update the password
    ((UserDetailsServiceImpl) getUserDetailsService()).changePassword(
        changePasswordRequest.currentPassword(), changePasswordRequest.newPassword());
  }

  public void createUser(UserDto user) {
    user.setPassword(getPasswordEncoder().encode(defaultPassword));
    user.setId(null);
    ((UserDetailsServiceImpl) getUserDetailsService()).createUser(user);
  }
}
