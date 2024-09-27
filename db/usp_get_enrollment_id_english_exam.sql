DROP PROCEDURE IF EXISTS usp_get_enrollment_id_for_english_test;

DELIMITER $$

CREATE PROCEDURE usp_get_enrollment_id_for_english_test (
    IN p_first_name VARCHAR(32),
    IN p_last_names VARCHAR(64),
    IN p_exam_date DATE
)
BEGIN
    SELECT *
    FROM tbl_enrollments e
    INNER JOIN tbl_students s 
        ON e.student_id = s.student_id
    WHERE s.first_name COLLATE utf8mb4_unicode_ci = p_first_name
    AND CONCAT(s.first_surname, ' ', IFNULL(s.second_surname, '')) COLLATE utf8mb4_unicode_ci = p_last_names
    AND e.exam_date = p_exam_date;
END$$

DELIMITER ;