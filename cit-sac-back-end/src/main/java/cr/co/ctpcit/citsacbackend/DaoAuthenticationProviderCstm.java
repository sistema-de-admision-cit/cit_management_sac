package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    if (authentication.getCredentials() == null) {
      this.logger.debug("Failed to authenticate since no credentials provided");
      throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
    } else {
      String presentedPassword = authentication.getCredentials().toString();
      if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
        this.logger.debug("Failed to authenticate since password does not match stored value");
        throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
      }
      ((UserDto)userDetails).setIsDefaultPassword(presentedPassword.equals(defaultPassword));
    }
  }
}
