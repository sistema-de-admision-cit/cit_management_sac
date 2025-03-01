package cr.co.ctpcit.citsacbackend.data.repositories.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {
}
