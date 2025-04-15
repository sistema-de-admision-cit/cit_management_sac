package cr.co.ctpcit.citsacbackend.logic.mappers.auth;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;

import java.util.List;

/**
 * A utility class for converting between {@link UserDto} and {@link UserEntity} objects.
 * This class provides methods for mapping data between the DTO and entity representations of a user.
 */
public class UserMapper {

  /**
   * Converts a {@link UserDto} object to a {@link UserEntity} object.
   *
   * @param dto the {@link UserDto} object to be converted
   * @return the corresponding {@link UserEntity} object
   */
  public static UserEntity toEntity(UserDto dto) {
    return UserEntity.builder()
            .email(dto.getEmail())
            .username(dto.getRealUsername())
            .password(dto.getPassword())
            .role(dto.getRole())
            .id(dto.getId())
            .build();
  }

  /**
   * Converts a list of {@link UserEntity} objects to a list of {@link UserDto} objects.
   *
   * @param content the list of {@link UserEntity} objects to be converted
   * @return a list of {@link UserDto} objects
   */
  public static List<UserDto> convertToDtoList(List<UserEntity> content) {
    return content.stream().map(UserMapper::toDto).toList();
  }

  /**
   * Converts a {@link UserEntity} object to a {@link UserDto} object.
   *
   * @param userEntity the {@link UserEntity} object to be converted
   * @return the corresponding {@link UserDto} object
   */
  private static UserDto toDto(UserEntity userEntity) {
    return UserDto.builder()
            .email(userEntity.getEmail())
            .username(userEntity.getUsername())
            .realUsername(userEntity.getUsername())
            .password(userEntity.getPassword())
            .role(userEntity.getRole())
            .id(userEntity.getId())
            .build();
  }

}
