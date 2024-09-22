package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE EnrollmentEntity e SET e.whatsappNotification = :whatsappNotification WHERE e.id = :id")
    void updateWhatsappNotificationById(@NotNull Long id, @NotNull Boolean whatsappNotification);

    @Modifying
    @Transactional
    @Query("UPDATE EnrollmentEntity e SET e.status = :status WHERE e.id = :id")
    void updateStatusById(Long id, ProcessStatus status);

    @Procedure(name = "usp_update_enrollment_and_log")
    void usp_update_enrollment_and_log(
            @Param("p_enrollment_id") Long enrollmentId,
            @Param("p_new_status") String newStatus,
            @Param("p_new_exam_date") Date newExamDate,
            @Param("p_new_whatsapp_permission") Boolean newWhatsappPermission,
            @Param("p_comment") String comment,
            @Param("p_changed_by") Integer changedBy
    );
}
