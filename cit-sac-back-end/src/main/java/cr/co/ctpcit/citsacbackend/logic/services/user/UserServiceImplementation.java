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


  }
}
