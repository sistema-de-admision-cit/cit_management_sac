package cr.co.ctpcit.citsacbackend.logic.services.user;

import cr.co.ctpcit.citsacbackend.data.repositories.users.UserRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImplementation implements UserService {
  private final UserRepository userRepository;

  public UserServiceImplementation(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public void updatePassword(String userEmail, ChangePasswordRequestDTO changePasswordRequestDTO) {
    // validate if the new passwords matches
    if (!changePasswordRequestDTO.newPassword()
        .equals(changePasswordRequestDTO.confirmPassword())) {
      throw new IllegalArgumentException("Las contraseñas no coinciden.");
    }

    // validate that the new password is not the same as the current password
    if (changePasswordRequestDTO.newPassword().equals(changePasswordRequestDTO.currentPassword())) {
      throw new IllegalArgumentException("La nueva contraseña no puede ser igual a la actual.");
    }

    // validate that the current password is correct
    if (!this.userRepository.findByEmail(userEmail).map(
            user -> new BCryptPasswordEncoder().matches(changePasswordRequestDTO.currentPassword(),
                user.getUserPassword()))
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."))) {
      throw new IllegalArgumentException("La contraseña actual es incorrecta.");
    }

    // encode the new password (BCryptPasswordEncoder)
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(changePasswordRequestDTO.newPassword());

    // update the password
    this.userRepository.updatePassword(userEmail, encodedPassword);

  }
}
