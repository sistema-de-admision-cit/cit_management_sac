package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {
  List<SystemConfigEntity> getSystemConfigEntitiesByConfigNameContaining(String configName);

  @Transactional
  @Modifying
  @Query(
      "UPDATE SystemConfigEntity s SET s.configValue = CASE s.configName " + "WHEN 'academic_weight' THEN ?1 " + "WHEN 'dai_weight' THEN ?2 " + "WHEN 'english_weight' THEN ?3 " + "END")
  void updateExamsPercentages(double academicWeight, double daiWeight, double englishWeight);
}
