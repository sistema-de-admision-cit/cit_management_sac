package cr.co.ctpcit.citsacbackend.data.repositories.users;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}
