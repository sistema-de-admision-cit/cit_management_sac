package cr.co.ctpcit.citsacbackend.logic.mappers.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;

import java.util.List;

public class SystemConfigMapper {
  public static SystemConfigEntity toEntity(SystemConfigDto systemConfigDto) {
    return new SystemConfigEntity(systemConfigDto.id(), systemConfigDto.configName(),
        systemConfigDto.configValue());
  }

  public static SystemConfigDto toDto(SystemConfigEntity savedEntity) {
    return new SystemConfigDto(savedEntity.getId(), savedEntity.getConfigName(),
        savedEntity.getConfigValue(), isSensible(savedEntity.getConfigName()));
  }

  public static List<SystemConfigDto> toDtoList(List<SystemConfigEntity> processWeights) {
    return processWeights.stream().map(SystemConfigMapper::toDto).toList();
  }

  private static boolean isSensible(Configurations configurations) {
    switch (configurations) {
      case EMAIL_PASSWORD, WHATSAPP_API_KEY-> {
            return true;
        }
        default -> {
            return false;
        }
    }
  }
}
