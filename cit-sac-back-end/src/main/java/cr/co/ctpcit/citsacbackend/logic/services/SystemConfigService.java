package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;

import java.util.List;

public interface SystemConfigService {
  SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto);

  List<SystemConfigEntity> getExamsPercentages(String configName);

  void updateExamsPercentages(double academicWeight, double daiWeight, double englishWeight);
}
