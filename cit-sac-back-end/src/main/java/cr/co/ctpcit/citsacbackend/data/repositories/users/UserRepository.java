package cr.co.ctpcit.citsacbackend.data.repositories.users;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);

  Boolean existsByEmail(String email);

  @Modifying
  @Query("update UserEntity u set u.password = ?2 where u.email = ?1")
  void updatePassword(String userEmail, String s);
}
