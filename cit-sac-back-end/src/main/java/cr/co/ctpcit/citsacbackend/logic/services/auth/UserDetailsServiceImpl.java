package cr.co.ctpcit.citsacbackend.logic.services.auth;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.users.UserRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsManager {

  private final UserRepository userRepository;

  @Override
  public void createUser(UserDetails userDetails) {
    // TODO Auto-generated method stub
  }

  @Override
  public void updateUser(UserDetails userDetails) {
    // TODO Auto-generated method stub
  }

  @Override
  public void deleteUser(String username) {
    // TODO Auto-generated method stub
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean userExists(String username) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> user = userRepository.findByEmail(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }

    UserDto userDto = new UserDto(user.get());
    if (!userDto.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Debe cambiar la contraseña la primera vez que inicia sesión");
    }

    return userDto;
  }
}
