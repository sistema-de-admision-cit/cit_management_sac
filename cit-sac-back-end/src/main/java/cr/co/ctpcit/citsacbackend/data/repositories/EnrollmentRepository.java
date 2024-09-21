package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE EnrollmentEntity e SET e.whatsappNotification = :whatsappNotification WHERE e.id = :id")
    void updateWhatsappNotificationById(@NotNull Long id, @NotNull Boolean whatsappNotification);
}
