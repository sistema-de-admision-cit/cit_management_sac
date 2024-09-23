package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.config.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.config.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.config.SystemConfigMapper;
import cr.co.ctpcit.citsacbackend.logic.services.config.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SystemConfigServiceImplementation implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;

  @Override
  public SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto) {
    SystemConfigEntity systemConfigEntity = SystemConfigMapper.toEntity(systemConfigDto);
    SystemConfigEntity savedEntity = systemConfigRepository.save(systemConfigEntity);
    return SystemConfigMapper.toDto(savedEntity);
  }

  @Override
  public List<SystemConfigEntity> getExamsPercentages(String configName) {
    return systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);
  }

  @Override
  public void updateExamsPercentages(double academicWeight, double daiWeight,
      double englishWeight) {
    try {
      systemConfigRepository.updateExamsPercentages(academicWeight, daiWeight, englishWeight);
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar los porcentajes de los exámenes");
    }
  }

}
