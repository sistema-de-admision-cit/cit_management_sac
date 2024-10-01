package cr.co.ctpcit.citsacbackend.logic.services.user;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {
  // update user password
  @Transactional
  void updatePassword(String userEmail, ChangePasswordRequestDTO changePasswordRequestDTO);
}
