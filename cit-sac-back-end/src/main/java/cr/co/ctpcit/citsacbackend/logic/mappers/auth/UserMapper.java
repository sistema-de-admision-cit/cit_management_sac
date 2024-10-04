package cr.co.ctpcit.citsacbackend.logic.mappers.auth;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;

import java.util.List;

public class UserMapper {
  public static UserEntity toEntity(UserDto dto) {
    return UserEntity.builder().email(dto.getUsername()).userPassword(dto.getPassword())
        .role(dto.getRole()).build();
  }

  public static List<UserDto> convertToDtoList(List<UserEntity> content) {
    return content.stream().map(UserMapper::toDto).toList();
  }

  private static UserDto toDto(UserEntity userEntity) {
    return UserDto.builder().username(userEntity.getEmail()).password(userEntity.getUserPassword())
        .role(userEntity.getRole()).build();
  }
}
