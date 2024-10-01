package cr.co.ctpcit.citsacbackend.logic.services.auth;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.users.UserRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsManager {
  private final SecurityContextHolderStrategy securityContextHolderStrategy =
      SecurityContextHolder.getContextHolderStrategy();
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> user = userRepository.findByEmail(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
    return new UserDto(user.get());
  }

  @Override
  public void createUser(UserDetails user) {

  }

  @Override
  public void updateUser(UserDetails user) {

  }

  @Override
  public void deleteUser(String username) {

  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    Authentication currentUser =
        this.securityContextHolderStrategy.getContext().getAuthentication();
    if (currentUser == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "Can't change password as no Authentication object found in context for current user.");
    }

    Optional<UserEntity> entity = userRepository.findByEmail(currentUser.getName());
    if (entity.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
    }
    if (!passwordEncoder.matches(oldPassword, entity.get().getUserPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
    }

    entity.get().setUserPassword(passwordEncoder.encode(newPassword));
    userRepository.save(entity.get());
  }

  @Override
  public boolean userExists(String username) {
    return userRepository.existsByEmail(username);
  }
}
