DROP PROCEDURE IF EXISTS usp_process_english_exam_and_log;

DELIMITER $$

CREATE PROCEDURE usp_process_english_exam_and_log(
    IN p_first_name VARCHAR(32),
    IN p_last_names VARCHAR(64),
    IN p_exam_date DATE,
    IN p_tracktest_id INT,
    IN p_new_score DECIMAL(5,2),
    IN p_level VARCHAR(50),
    IN p_process_id INT
)
proc_label: BEGIN
    DECLARE v_enrollment_id INT;
    DECLARE v_previous_score DECIMAL(5,2) DEFAULT NULL;
    DECLARE v_status ENUM('success', 'error', 'warning') DEFAULT 'error';
    DECLARE v_error_message TEXT DEFAULT NULL;

    -- Iniciar una transacción
    START TRANSACTION;

    -- Buscar la inscripción (enrollment)
    SELECT e.enrollment_id
    INTO v_enrollment_id
    FROM tbl_enrollments e
    INNER JOIN tbl_students s 
        ON e.student_id = s.student_id
    WHERE s.first_name COLLATE utf8mb4_unicode_ci = p_first_name
    AND CONCAT(s.first_surname, ' ', IFNULL(s.second_surname, '')) COLLATE utf8mb4_unicode_ci = p_last_names
    AND e.exam_date = p_exam_date
    LIMIT 1;

    -- Verificar si se encontró la inscripción
    IF v_enrollment_id IS NULL THEN
        -- Si no se encontró, registrar en los logs con mensaje de error
    SET v_error_message = CONCAT('Hubo un error al procesar el examen con ID de tracktest ', p_tracktest_id, 
        '. No se encontró la inscripción para el estudiante ', 
        UPPER(LEFT(p_first_name, 1)), LOWER(SUBSTRING(p_first_name, 2)), ' ', 
        UPPER(LEFT(p_last_names, 1)), LOWER(SUBSTRING(p_last_names, 2)), 
        ' en la fecha ', p_exam_date, '.');


        INSERT INTO tbl_logsscore (process_id, tracktest_id, enrollment_id, previous_score, new_score, exam_date, timestamp, status, error_message)
        VALUES (p_process_id, p_tracktest_id, NULL, NULL, p_new_score, p_exam_date, NOW(), v_status, v_error_message);

        -- Finalizar el procedimiento sin continuar procesando el examen
        LEAVE proc_label;
    END IF;

    -- Obtener la nota anterior, si existe
    SELECT grade INTO v_previous_score
    FROM tbl_englishexams
    WHERE enrollment_id = v_enrollment_id
    AND tracktest_id = p_tracktest_id
    LIMIT 1;

    -- Actualizar o insertar el nuevo examen
    IF v_previous_score IS NOT NULL THEN
        -- Si existe, actualizamos el registro
        UPDATE tbl_englishexams
        SET grade = p_new_score, exam_date = p_exam_date, level = p_level
        WHERE enrollment_id = v_enrollment_id
        AND tracktest_id = p_tracktest_id;
        
        SET v_status = 'success';
    ELSE
        -- Si no existe, insertamos un nuevo registro
        INSERT INTO tbl_englishexams (enrollment_id, tracktest_id, grade, exam_date, level)
        VALUES (v_enrollment_id, p_tracktest_id, p_new_score, p_exam_date, p_level);
        
        SET v_status = 'success';
    END IF;

    -- Registrar el proceso en la tabla de logs
    INSERT INTO tbl_logsscore (process_id, tracktest_id, enrollment_id, previous_score, new_score, exam_date, timestamp, status, error_message)
    VALUES (p_process_id, p_tracktest_id, v_enrollment_id, v_previous_score, p_new_score, p_exam_date, NOW(), v_status, v_error_message);

    -- Confirmar la transacción
    COMMIT;

END$$

DELIMITER ;