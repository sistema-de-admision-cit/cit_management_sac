package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;

import java.util.List;

public interface SystemConfigService {

  List<SystemConfigDto> getQuestionsQuantity();

  void updateQuantity(int daiQuantity, int academicQuantity);

  List<SystemConfigDto> getProcessWeights();

  void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight);

  List<SystemConfigDto> getContactInfo();

  void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto);

  ExamPeriodDto getExamPeriod(Long id);

  List<ExamPeriodDto> getCurrentExamPeriods();

  List<ExamPeriodDto> getExamPeriodsByYear(int year);

  void createExamPeriod(ExamPeriodDto examPeriodDto);

  void updateExamQuestionsQuantity(Configurations config, Integer quantity);

  String getConfigValue(Configurations configName, boolean isSensible);

  void deleteExamPeriod(Long id);
}
