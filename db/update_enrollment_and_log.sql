DROP PROCEDURE IF EXISTS usp_update_enrollment_and_log;

DELIMITER $$

CREATE PROCEDURE usp_update_enrollment_and_log ( 
    IN p_enrollment_id INT,
    IN p_new_status ENUM('P', 'E', 'I', 'A', 'R'),
    IN p_new_exam_date DATE,
    IN p_new_whatsapp_permission BOOLEAN,
    IN p_comment VARCHAR(255),
    IN p_changed_by INT
)
BEGIN
    DECLARE v_old_status ENUM('P', 'E', 'I', 'A', 'R');
    DECLARE v_old_exam_date DATE;
    DECLARE v_old_whatsapp_permission BOOLEAN;

    -- Obtener los valores actuales
    SELECT `status`, `exam_date`, `whatsapp_notification`
    INTO v_old_status, v_old_exam_date, v_old_whatsapp_permission
    FROM `db_cit_test`.`tbl_enrollments`
    WHERE `enrollment_id` = p_enrollment_id;

    -- Actualizar los valores
    UPDATE `db_cit_test`.`tbl_enrollments`
    SET `status` = p_new_status,
        `exam_date` = p_new_exam_date,
        `whatsapp_notification` = p_new_whatsapp_permission
    WHERE `enrollment_id` = p_enrollment_id;


    -- Si el status ha cambiado, registrar en la tabla de logs
    IF v_old_status != p_new_status THEN
        INSERT INTO `db_cit_test`.`tbl_log` (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES ('tbl_enrollments', 'status', v_old_status, p_new_status, p_changed_by, queryForNewExamDate, CONCAT('UPDATE tbl_enrollments SET status = ', p_new_status, ' WHERE enrollment_id = ', p_enrollment_id),p_comment);
    END IF;

    -- Si la fecha de examen ha cambiado, registrar en la tabla de logs
    IF v_old_exam_date != p_new_exam_date THEN
        INSERT INTO `db_cit_test`.`tbl_log` (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES ('tbl_enrollments', 'exam_date', v_old_exam_date, p_new_exam_date, p_changed_by, queryForNewExamDate, CONCAT('UPDATE tbl_enrollments SET exam_date = ', p_new_exam_date, ' WHERE enrollment_id = ', p_enrollment_id), p_comment);
    END IF;
    
    -- No se requiere log para whatsapp_permission
END$$

DELIMITER ;
