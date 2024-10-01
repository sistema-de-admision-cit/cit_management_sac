package cr.co.ctpcit.citsacbackend.security;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DaoAuthenticationProviderCstm  extends DaoAuthenticationProvider {
  @Value("${cit.app.default-password}")
  String defaultPassword;

  public DaoAuthenticationProviderCstm() {
    super();
  }

  public DaoAuthenticationProviderCstm(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
    super(passwordEncoder);
    this.setUserDetailsService(userDetailsService);
  }

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws
      AuthenticationException {
    super.additionalAuthenticationChecks(userDetails, authentication);
    String presentedPassword = authentication.getCredentials().toString();
    ((UserDto)userDetails).setIsDefaultPassword(presentedPassword.equals(defaultPassword));
  }
}
