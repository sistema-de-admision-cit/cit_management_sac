package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;

import java.util.Optional;

public interface SystemConfigService {
    SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto);

}
