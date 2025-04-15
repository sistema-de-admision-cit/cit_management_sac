package cr.co.ctpcit.citsacbackend.logic.mappers.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;

import java.util.List;

/**
 * A utility class for converting between {@link SystemConfigDto} and {@link SystemConfigEntity}.
 * This class provides methods for mapping data between DTOs and entities related to system configuration.
 */
public class SystemConfigMapper {

  /**
   * A utility class for converting between {@link SystemConfigDto} and {@link SystemConfigEntity}.
   * This class provides methods for mapping data between DTOs and entities related to system configuration.
   */
  public static SystemConfigEntity toEntity(SystemConfigDto systemConfigDto) {
    return new SystemConfigEntity(systemConfigDto.id(), systemConfigDto.configName(),
        systemConfigDto.configValue());
  }

  /**
   * Converts a {@link SystemConfigEntity} to a {@link SystemConfigDto}.
   *
   * @param savedEntity the {@link SystemConfigEntity} object to be converted
   * @return the corresponding {@link SystemConfigDto} object
   */
  public static SystemConfigDto toDto(SystemConfigEntity savedEntity) {
    return new SystemConfigDto(savedEntity.getId(), savedEntity.getConfigName(),
        savedEntity.getConfigValue());
  }

  /**
   * Converts a list of {@link SystemConfigEntity} objects to a list of {@link SystemConfigDto} objects.
   *
   * @param processWeights the list of {@link SystemConfigEntity} objects to be converted
   * @return a list of {@link SystemConfigDto} objects
   */
  public static List<SystemConfigDto> toDtoList(List<SystemConfigEntity> processWeights) {
    return processWeights.stream().map(SystemConfigMapper::toDto).toList();
  }
}
