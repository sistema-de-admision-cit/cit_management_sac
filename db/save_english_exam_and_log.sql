DELIMITER $$

CREATE PROCEDURE usp_process_english_exam(
    IN p_enrollment_id INT,
    IN p_track_test_exam_id INT,
    IN p_new_score DECIMAL(5,2),
    IN p_exam_date DATE,
    IN p_level VARCHAR(50),
    IN p_process_id INT
)
BEGIN
    DECLARE v_previous_score DECIMAL(5,2);
    DECLARE v_status ENUM('success', 'error', 'warning');
    DECLARE v_error_message TEXT DEFAULT NULL;
    
    -- Iniciar una transacción
    START TRANSACTION;
    
    -- Obtener la nota anterior, si existe
    SELECT grade INTO v_previous_score
    FROM tbl_englishexams
    WHERE enrollment_id = p_enrollment_id
    AND track_test_exam_id = p_track_test_exam_id
    LIMIT 1;
    
    -- Actualizar o insertar el nuevo examen
    IF v_previous_score IS NOT NULL THEN
        -- Si existe, actualizamos el registro
        UPDATE tbl_englishexams
        SET grade = p_new_score, exam_date = p_exam_date, level = p_level
        WHERE enrollment_id = p_enrollment_id
        AND track_test_exam_id = p_track_test_exam_id;
        
        SET v_status = 'success';
    ELSE
        -- Si no existe, insertamos un nuevo registro
        INSERT INTO tbl_englishexams (enrollment_id, track_test_exam_id, grade, exam_date, level)
        VALUES (p_enrollment_id, p_track_test_exam_id, p_new_score, p_exam_date, p_level);
        
        SET v_status = 'success';
    END IF;

    -- Registrar el proceso en la tabla de logs
    INSERT INTO tbl_logsscore (process_id, track_test_exam_id, enrollment_id, previous_score, new_score, exam_date, timestamp, status, error_message)
    VALUES (p_process_id, p_track_test_exam_id, p_enrollment_id, v_previous_score, p_new_score, p_exam_date, NOW(), v_status, v_error_message);
    
    -- Confirmar la transacción
    COMMIT;

END$$

DELIMITER ;
