package cr.co.ctpcit.citsacbackend.logic.mappers.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;

public class SystemConfigMapper {
  public static SystemConfigEntity toEntity(SystemConfigDto dto) {
    SystemConfigEntity entity = new SystemConfigEntity();
    if (dto.id() != null) {
      entity.setId(dto.id());
    }
    entity.setConfigName(dto.configName());
    entity.setConfigValue(dto.configValue());
    return entity;
  }

  public static SystemConfigDto toDto(SystemConfigEntity entity) {
    return new SystemConfigDto(entity.getId(), entity.getConfigName(), entity.getConfigValue());
  }
}
