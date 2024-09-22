package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository  extends JpaRepository<SystemConfigEntity, Integer> {
}
