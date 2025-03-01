package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {
  Optional<SystemConfigEntity> findByConfigName(Configurations configName);

  @Query(
      "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'ACADEMIC_WEIGHT' OR s.configName = 'PREV_GRADES_WEIGHT' OR s.configName = 'ENGLISH_WEIGHT'")
  List<SystemConfigEntity> getProcessWeights();
}
