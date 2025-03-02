package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;

import java.util.List;

public interface SystemConfigService {
  List<SystemConfigDto> getProcessWeights();

  void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight);

  List<SystemConfigDto> getContactInfo();

  void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto);

  List<ExamPeriodDto> getCurrentExamPeriods();
}
