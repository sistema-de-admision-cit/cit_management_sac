package cr.co.ctpcit.citsacbackend.data.repositories.users;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} entities.
 * Extends {@link JpaRepository} for standard CRUD operations.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user
   * @return an {@link Optional} containing the {@link UserEntity} if found, otherwise empty
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Checks if a user exists with the given email address.
   *
   * @param email the email address to check
   * @return true if a user exists with the provided email, false otherwise
   */
  Boolean existsByEmail(String email);

  /**
   * Updates the password for a user with the given email address.
   *
   * @param userEmail the email address of the user whose password will be updated
   * @param s the new password to set for the user
   */
  @Modifying
  @Query("update UserEntity u set u.password = ?2 where u.email = ?1")
  void updatePassword(String userEmail, String s);
}
