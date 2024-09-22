package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.SystemConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
