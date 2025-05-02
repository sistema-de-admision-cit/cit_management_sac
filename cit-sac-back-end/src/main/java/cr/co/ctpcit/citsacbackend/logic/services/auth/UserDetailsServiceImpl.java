package cr.co.ctpcit.citsacbackend.logic.services.auth;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.users.UserRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.auth.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
/**
 * Custom implementation of Spring Security's UserDetailsManager.
 * Provides user management functionality including authentication, creation, deletion,
 * and password management for application users.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsManager {
  private final SecurityContextHolderStrategy securityContextHolderStrategy =
      SecurityContextHolder.getContextHolderStrategy();
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  /**
   * Loads user details by username (email in this implementation).
   *
   * @param username the username (email) identifying the user whose data is required
   * @return UserDetails containing the user's information
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> user = userRepository.findByEmail(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
    return new UserDto(user.get());
  }
  /**
   * Creates a new user in the system.
   *
   * @param user the user details to create
   * @throws ResponseStatusException if a user with the same email already exists
   */
  @Override
  public void createUser(UserDetails user) {
    //Validar que el usuario no exista con base al email
    if (userRepository.existsByEmail(user.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
    }

    UserEntity entity = UserMapper.toEntity((UserDto) user);

    entity = userRepository.save(entity);
    ((UserDto) user).setId(entity.getId());
  }
  /**
   * Updates a user's information (not implemented).
   *
   * @param user the user details to update
   */
  @Override
  public void updateUser(UserDetails user) {

  }
  /**
   * Deletes a user from the system.
   *
   * @param username the username (email) of the user to delete
   * @throws ResponseStatusException if the user is not found
   */
  @Override
  public void deleteUser(String username) {
    Optional<UserEntity> entity = userRepository.findByEmail(username);
    if (entity.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado");
    }

    userRepository.delete(entity.get());
  }
  /**
   * Changes a user's password after verifying the old password.
   *
   * @param oldPassword the current password
   * @param newPassword the new password to set
   * @throws ResponseStatusException if user is not authenticated, not found, or old password doesn't match
   */
  @Override
  public void changePassword(String oldPassword, String newPassword) {
    Authentication currentUser =
        this.securityContextHolderStrategy.getContext().getAuthentication();
    if (currentUser == null) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,
          "No se pudo cambiar la contraseña, usuario no autenticado.");
    }

    Optional<UserEntity> entity = userRepository.findByEmail(currentUser.getName());
    if (entity.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado.");
    }
    if (!passwordEncoder.matches(oldPassword, entity.get().getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    entity.get().setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(entity.get());
  }
  /**
   * Checks if a user exists in the system.
   *
   * @param username the username (email) to check
   * @return true if the user exists, false otherwise
   */
  @Override
  public boolean userExists(String username) {
    return userRepository.existsByEmail(username);
  }
  /**
   * Retrieves a paginated list of all users in the system.
   *
   * @param pageable pagination information
   * @return List of UserDto objects
   */
  public List<UserDto> getUsers(Pageable pageable) {
    Page<UserEntity> users = userRepository.findAll(
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            pageable.getSortOr(Sort.by(Sort.Direction.ASC, "email"))));

    return UserMapper.convertToDtoList(users.getContent());
  }
  /**
   * Retrieves a user by their ID.
   *
   * @param id the user ID
   * @return UserDto object or null if not found
   */
  public UserDto getUser(Long id) {
    Optional<UserEntity> user = userRepository.findById(id);
    return user.map(UserDto::new).orElse(null);
  }
  /**
   * Retrieves a user by their email.
   *
   * @param email the user's email
   * @return UserDto object or null if not found
   */
  public UserDto getUserByEmail(String email) {
    Optional<UserEntity> user = userRepository.findByEmail(email);
    return user.map(UserDto::new).orElse(null);
  }
}
