-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_cit_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS db_cit DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE db_cit;

-- -----------------------------------------------------
-- Table `tbl_person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Persons`;

CREATE TABLE IF NOT EXISTS `tbl_Persons` (
  `person_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `first_surname` VARCHAR(32) NOT NULL,
  `second_surname` VARCHAR(32) NULL DEFAULT NULL,
  `id_type` ENUM('CC', 'DI', 'PA') NOT NULL, -- CC: Cedula, DI: DIMEX, PA: Pasaporte
  `id_number` VARCHAR(32) NOT NULL,
  `full_surname` VARCHAR(64) AS (CONCAT(`first_surname`, ' ', `second_surname`)) STORED,
  PRIMARY KEY (`person_id`),

  UNIQUE INDEX `UQ_Persons_IdNumber` (`id_number` ASC),
  
  INDEX `IDX_Persons_Fullname` (`full_surname` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Parents`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Parents`;

CREATE TABLE IF NOT EXISTS `tbl_Parents` (
  `parent_id` INT UNSIGNED NOT NULL,
  `phone_number` VARCHAR(32) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `relationship` ENUM('M', 'F', 'G') NOT NULL,
  `dai_exam` JSON DEFAULT NULL,
  PRIMARY KEY (`parent_id`),
  CONSTRAINT `FK_Parents_Persons`
    FOREIGN KEY (`parent_id`) 
    REFERENCES `tbl_Persons`(`person_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Address` ;

CREATE TABLE IF NOT EXISTS `tbl_Address` (
  `address_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` INT UNSIGNED NOT NULL,
  `country` VARCHAR(32) NOT NULL,
  `province` VARCHAR(32) NOT NULL,
  `city` VARCHAR(32) NOT NULL,
  `district` VARCHAR(32) NOT NULL,
  `address_info` VARCHAR(128) NOT NULL DEFAULT 'N/A',
  PRIMARY KEY (`address_id`),
  INDEX `IDX_Addresses_ParentID` (`parent_id` ASC),
  CONSTRAINT `FK_Addresses_Parents`
    FOREIGN KEY (`parent_id`)
    REFERENCES `tbl_Parents` (`parent_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Students`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Students`;

CREATE TABLE IF NOT EXISTS `tbl_Students` (
  `student_id` INT UNSIGNED NOT NULL,
  `birth_date` DATE NOT NULL,
  `previous_school` VARCHAR(128) NULL DEFAULT NULL,
  `previous_grades` DECIMAL(5,2) DEFAULT 0.00,
  `has_accommodations` BOOLEAN NOT NULL,
  PRIMARY KEY (`student_id`),
  CONSTRAINT `FK_Students_Persons`
    FOREIGN KEY (`student_id`) 
    REFERENCES `tbl_Persons`(`person_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Parents_Students`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Parents_Students` ;

CREATE TABLE IF NOT EXISTS `tbl_Parents_Students` (
  `student_id` INT UNSIGNED NOT NULL,
  `parent_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`student_id`, `parent_id`),
  INDEX `IDX_Parents_Students_ParentID` (`parent_id` ASC) INVISIBLE,
  INDEX `IDX_Parents_Students_StudentID` (`student_id` ASC) INVISIBLE,

  CONSTRAINT `FK_Parents_Students_Parents`
    FOREIGN KEY (`parent_id`)
    REFERENCES `tbl_Parents` (`parent_id`),

  CONSTRAINT `FK_Parents_Students_Students`
    FOREIGN KEY (`student_id`)
    REFERENCES `tbl_Students` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_Enrollments`
-- status:
	-- PENDING: waiting for approval
  -- ELIGIBLE: student is eligible to go through the admission process
  -- INELIGIBLE: student is not eligible to go through the admission process
  -- ACCEPTED: student is accepted and can enroll
  -- REJECTED: student is rejected and cannot enroll
  -- -----------------------------------------------------
-- grade_to_entoll
  -- level in which the student is supposed to enter when approved.
-- -----------------------------------------------------
-- known_through
	-- SM: social media
	-- OH: open house
	-- FD: friend
	-- FM: family
	-- OT: other
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Enrollments`;

CREATE TABLE IF NOT EXISTS `tbl_Enrollments` (
  `enrollment_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` INT UNSIGNED NOT NULL,
  `status` ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED') NOT NULL,
  `enrollment_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grade_to_enroll` ENUM('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH') NOT NULL,
  `known_through` ENUM('SM', 'OH', 'FD', 'FM', 'OT') NOT NULL DEFAULT 'OT',
  `exam_date` DATE NOT NULL,
  `consent_given` BOOLEAN NOT NULL DEFAULT 0,
  `whatsapp_notification` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY (`enrollment_id`),
  INDEX `IDX_Enrollments_StudentID` (`student_id` ASC),

  CONSTRAINT `FK_Enrollments_Students`
    FOREIGN KEY (`student_id`)
    REFERENCES `tbl_Students` (`student_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Documents`
-- document_type:
  -- AC: adequacies certificate
  -- OT: other
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Documents`;

CREATE TABLE IF NOT EXISTS `tbl_Documents` (
  `document_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT UNSIGNED NOT NULL,
  `document_name` VARCHAR(64) NOT NULL,
  `document_type` ENUM('AC', 'OT') NOT NULL,
  `document_url` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`document_id`),
  INDEX `IDX_Documents_EnrollmentID` (`enrollment_id` ASC),
  
  CONSTRAINT `FK_Documents_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `tbl_Enrollments` (`enrollment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Questions`
-- `selection_type`:
  -- SINGLE: unique answer
  -- MULTIPLE: multiple answer
  -- PARAGRAPH: table has no choises
-- ------------------------------------------------------
-- `question_type`:
  -- ACA: academic
  -- DAI: psiometric
-- ------------------------------------------------------
-- `question_grade`:
  -- The enrollment's grade in which the question is supposed to be asked
-- -----------------------------------------------------
-- `question_level`:
  -- EASY: easy question
  -- MEDIUM: medium question
  -- HARD: hard question
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Questions`;

CREATE TABLE IF NOT EXISTS `tbl_Questions` (
  `question_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_type` ENUM('ACA', 'DAI') NOT NULL,
  `question_text` VARCHAR(512) NOT NULL,
  `image_url` VARCHAR(255) DEFAULT NULL,
  `question_grade` ENUM('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH') NOT NULL,
  `question_level` ENUM('EASY', 'MEDIUM', 'HARD') NOT NULL,
  `selection_type` ENUM('SINGLE', 'MULTIPLE', 'PARAGRAPH') NOT NULL DEFAULT 'PARAGRAPH',
  `deleted` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY (`question_id`),
  INDEX `IDX_Questions_QuestionType_ACA` ((`question_type` = 'ACA')),
  INDEX `IDX_Questions_QuestionType_DAI` ((`question_type` = 'DAI')))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Question_Options`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Question_Options` ;

CREATE TABLE IF NOT EXISTS `tbl_Question_Options` (
  `option_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_id` INT UNSIGNED NOT NULL,
  `is_correct` BOOLEAN NOT NULL DEFAULT 0,
  `option` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`option_id`),
  INDEX `IDX_QuestionOptions_QuestionID` (`question_id` ASC),

  CONSTRAINT `FK_AcademicQuestions_QuestionOption`
    FOREIGN KEY (`question_id`)
    REFERENCES `tbl_Questions` (`question_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Exams`
-- `exam_type`:
  -- ACA: academic
  -- DAI: psiometric
  -- ENG: english
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Exams`;

CREATE TABLE IF NOT EXISTS `tbl_Exams` (
  `exam_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT UNSIGNED NOT NULL,
  `exam_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `exam_type` ENUM('ACA', 'DAI', 'ENG') NOT NULL,
  `responses` JSON DEFAULT NULL,
  PRIMARY KEY (`exam_id`),
  INDEX `IDX_Exams_EnrollmentID` (`enrollment_id` ASC),

  CONSTRAINT `FK_Exams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `tbl_Enrollments` (`enrollment_id`)
    ON DELETE NO ACTION,
    
  CONSTRAINT `UQ_ExamType_Enrollment` UNIQUE (`enrollment_id`, `exam_type`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Academic_Exams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Academic_Exams`;

CREATE TABLE IF NOT EXISTS `tbl_Academic_Exams` (
  `exam_id` INT UNSIGNED NOT NULL,
  `grade` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`exam_id`),

  CONSTRAINT `FK_AcademicExams_Exams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `tbl_Exams` (`exam_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Dai_Exams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Dai_Exams` ;

CREATE TABLE IF NOT EXISTS `tbl_Dai_Exams` (
  `exam_id` INT UNSIGNED NOT NULL,
  `comment` VARCHAR(255) DEFAULT '',
  `recommendation` ENUM('ADMIT', 'REJECT') DEFAULT NULL,
  `reviewed` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY (`exam_id`),

  CONSTRAINT `FK_DaiExams_Exams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `tbl_Exams` (`exam_id`)
    ON DELETE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_English_Exams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_English_Exams` ;

CREATE TABLE IF NOT EXISTS `tbl_English_Exams` (
  `exam_id` INT UNSIGNED NOT NULL,
  `tracktest_id` INT UNSIGNED NOT NULL,
  `level` ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') NOT NULL,
  `core` TINYINT NOT NULL,
  PRIMARY KEY (`exam_id`),
  UNIQUE INDEX `UQ_EnglishExams_TrackTestID` (`tracktest_id` ASC),

  CONSTRAINT `FK_EnglishExams_Exams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `tbl_Exams` (`exam_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_Exam_Periods`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Exam_Periods` ;

CREATE TABLE IF NOT EXISTS `tbl_Exam_Periods` (
  `exam_period_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`exam_period_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_Exam_Days`
-- `exam_day`:
  -- M: Monday
  -- K: Tuesday
  -- W: Wednesday
  -- T: Thursday
  -- F: Friday
  -- S: Saturday
  -- SS: Sunday

-- `start_time` format: HH:MM:SS
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Exam_Days` ;

CREATE TABLE IF NOT EXISTS `tbl_Exam_Days` (
  `exam_day_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `exam_period_id` INT UNSIGNED NOT NULL,
  `exam_day` ENUM('M', 'K', 'W', 'T', 'F', 'S', 'SS') NOT NULL,
  `start_time` TIME NOT NULL,
  PRIMARY KEY (`exam_day_id`),
  INDEX `IDX_Exam_Days_ExamPeriodsID` (`exam_period_id` ASC),

  CONSTRAINT `FK_ExamDays_ExamPeriods`
    FOREIGN KEY (`exam_period_id`)
    REFERENCES `tbl_Exam_Periods` (`exam_period_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_Logs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Logs` ;

CREATE TABLE IF NOT EXISTS `tbl_Logs` (
  `log_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `table_name` VARCHAR(50) NOT NULL,
  `column_name` VARCHAR(50) NOT NULL,
  `old_value` TEXT NOT NULL,
  `new_value` TEXT NOT NULL,
  `changed_by` INT UNSIGNED NOT NULL,
  `changed_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `query` TEXT NULL DEFAULT NULL,
  `comment` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`log_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_Logs_Score`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Logs_Score` ;

CREATE TABLE IF NOT EXISTS `tbl_Logs_Score` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `process_id` INT NULL DEFAULT NULL,
  `tracktest_id` INT NULL DEFAULT NULL,
  `enrollment_id` INT UNSIGNED NULL DEFAULT NULL,
  `previous_score` INT NULL DEFAULT NULL,
  `new_score` INT NULL DEFAULT NULL,
  `exam_date` DATE NULL DEFAULT NULL,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` ENUM('SUCCESS', 'ERROR', 'WARNING') NULL DEFAULT NULL,
  `error_message` TINYTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `tbl_System_Config`

-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_system_config`;

CREATE TABLE IF NOT EXISTS `tbl_system_config` (
  `config_id` INT NOT NULL AUTO_INCREMENT,
  `config_name` ENUM(
						'EMAIL_CONTACT',                   	-- Email for the people to ask any question 
                        'EMAIL_NOTIFICATION_CONTACT', 		-- Email for the system to send notifications
                        'EMAIL_PASSWORD',					-- Email password to send notifications
						'WHATSAPP_CONTACT',					-- Whatsapp phone number to send notifications
                        'WHATSAPP_API_KEY',					-- Whatsapp api key to send notifications
                        'OFFICE_CONTACT', 					-- Office phone number
                        'FACEBOOK_CONTACT', 				-- Facebook profile name
                        'INSTAGRAM_CONTACT', 				-- Instagram profile name
                        'PREV_GRADES_WEIGHT', 				-- Weight of the previous grades in the process
                        'ACADEMIC_WEIGHT', 					-- Weight of the academic exam in the process 
                        'ENGLISH_WEIGHT', 					-- Weight of the english exam in the process
                        'ACADEMIC_EXAM_QUESTIONS_QUANTITY', -- Academic exam question quantity 
                        'DAI_EXAM_QUESTIONS_QUANTITY'		-- DAI exam question quantity
					) NOT NULL,
  `config_value` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE INDEX `uq_config_name` (`config_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_users`
-- `role`:
  -- SYS: system administrator
  -- ADMIN: administrator
  -- TEACHER: teacher
  -- PSI: psicologist
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_users` ;

CREATE TABLE IF NOT EXISTS `tbl_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `user_password` VARCHAR(128) NOT NULL,
  `role` ENUM('SYS', 'ADMIN', 'TEACHER', 'PSYCHOLOGIST') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `uq_users_email` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- Stored Procedures
-- -----------------------------------------------------
DELIMITER //
-- -----------------------------------------------------
-- Procedure: usp_Update_Enrollment_And_Log
-- Description: Updates the status, exam date, and whatsapp permission of an enrollment and logs the changes

DROP PROCEDURE IF EXISTS usp_update_enrollment_and_log//

CREATE PROCEDURE usp_update_enrollment_and_log ( 
    IN p_enrollment_id INT,
    IN p_new_status ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED'),
    IN p_new_exam_date DATE,
    IN p_new_whatsapp_permission BOOLEAN,
    IN p_new_previous_grades DECIMAL(5,2),
    IN p_comment VARCHAR(255),
    IN p_changed_by VARCHAR(128)
)
BEGIN
    DECLARE v_old_status ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED');
    DECLARE v_old_exam_date DATE;
    DECLARE v_old_whatsapp_permission BOOLEAN;
    DECLARE v_old_previous_grades DECIMAL(5,2);
    DECLARE v_student_id INT;
    DECLARE v_changed_by_id INT UNSIGNED;

    -- Obtener datos actuales de la inscripción
    SELECT `status`, `exam_date`, `whatsapp_notification`, `student_id`
    INTO v_old_status, v_old_exam_date, v_old_whatsapp_permission, v_student_id
    FROM `tbl_enrollments`
    WHERE `enrollment_id` = p_enrollment_id;

    -- Obtener la nota previa del estudiante
    SELECT `previous_grades`
    INTO v_old_previous_grades
    FROM `tbl_students`
    WHERE `student_id` = v_student_id;
    
    -- Obtener el id del usuario que actualiza
    SELECT `user_id`
    INTO v_changed_by_id
    FROM `tbl_users`
    WHERE `email` = p_changed_by;

    -- Actualizar inscripción
    UPDATE `tbl_enrollments`
    SET `status` = p_new_status,
        `exam_date` = p_new_exam_date,
        `whatsapp_notification` = p_new_whatsapp_permission
    WHERE `enrollment_id` = p_enrollment_id;

    -- Actualizar calificaciones previas si es diferente
    IF v_old_previous_grades != p_new_previous_grades THEN
        UPDATE `tbl_students`
        SET `previous_grades` = p_new_previous_grades
        WHERE `student_id` = v_student_id;

        INSERT INTO `tbl_logs` 
            (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES 
            ('tbl_students', 'previous_grades', v_old_previous_grades, p_new_previous_grades, v_changed_by_id, 
             CONCAT('UPDATE tbl_students SET previous_grades = ', p_new_previous_grades, ' WHERE student_id = ', v_student_id), 
             p_comment);
    END IF;

    -- Registrar cambios si los valores fueron modificados
    IF v_old_status != p_new_status THEN
        INSERT INTO `tbl_logs` 
            (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES 
            ('tbl_enrollments', 'status', v_old_status, p_new_status, v_changed_by_id, 
             CONCAT('UPDATE tbl_enrollments SET status = ', p_new_status, ' WHERE enrollment_id = ', p_enrollment_id), 
             p_comment);
    END IF;

    IF v_old_exam_date != p_new_exam_date THEN
        INSERT INTO `tbl_logs` 
            (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES 
            ('tbl_enrollments', 'exam_date', v_old_exam_date, p_new_exam_date, v_changed_by_id, 
             CONCAT('UPDATE tbl_enrollments SET exam_date = ', p_new_exam_date, ' WHERE enrollment_id = ', p_enrollment_id), 
             p_comment);
    END IF;
    
    IF v_old_whatsapp_permission != p_new_whatsapp_permission THEN
		INSERT INTO `tbl_logs` 
			(`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
		VALUES
			('tbl_enrollments', 'whatsapp_notification', v_old_whatsapp_permission, p_new_whatsapp_permission, v_changed_by_id, 
             CONCAT('UPDATE tbl_enrollments SET whatsapp_notification = ', p_new_whatsapp_permission, ' WHERE enrollment_id = ', p_enrollment_id), 
             p_comment);
	END IF;

    -- No se registra el cambio de whatsapp_notification en logs
END//

-- -----------------------------------------------------
-- Procedure: usp_systemconfig_notifications_update
-- Description: Updates the system configuration for notifications

DROP PROCEDURE IF EXISTS usp_systemconfig_notifications_update//

CREATE PROCEDURE usp_systemconfig_notifications_update(
    IN p_email_contact VARCHAR(128),
    IN p_email_notifications_contact VARCHAR(128),
    IN p_whatsapp_contact VARCHAR(128),
    IN p_office_contact VARCHAR(128),
    IN p_instagram_contact VARCHAR(128),
    IN p_facebook_contact VARCHAR(128)
)

BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    
    BEGIN
        -- rollback the transaction
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error en la transacción';
    END;

    START TRANSACTION;

    -- call the procedure to update or insert
    CALL usp_Update_Or_Insert_Config('email_contact', p_email_contact);
    CALL usp_Update_Or_Insert_Config('email_notifications_contact', p_email_notifications_contact);
    CALL usp_Update_Or_Insert_Config('whatsapp_contact', p_whatsapp_contact);
    CALL usp_Update_Or_Insert_Config('office_contact', p_office_contact);
    CALL usp_Update_Or_Insert_Config('instagram_contact', p_instagram_contact);
    CALL usp_Update_Or_Insert_Config('facebook_contact', p_facebook_contact);

    COMMIT;
END//

-- -----------------------------------------------------
-- Procedure: usp_update_or_insert_config
-- Description: Updates or inserts a system configuration

DROP PROCEDURE IF EXISTS usp_update_or_insert_config//

-- procedure to update or insert a system config
CREATE PROCEDURE usp_update_or_insert_config(
    IN p_config_name VARCHAR(32),
    IN p_config_value VARCHAR(128)
)

BEGIN
    IF NOT EXISTS (SELECT 1 FROM tbl_system_config WHERE config_name = p_config_name) THEN
        INSERT INTO tbl_system_config (config_name, config_value) VALUES (p_config_name, p_config_value);
    ELSE
        UPDATE tbl_system_config SET config_value = p_config_value WHERE config_name = p_config_name;
    END IF;
END//

-- -----------------------------------------------------
-- Procedure: usp_Process_English_Exam_And_Log
-- Description: Processes an English exam and logs the results

DROP PROCEDURE IF EXISTS usp_process_english_exam_and_log;

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
    DECLARE v_exam_id INT;
    DECLARE v_existing_exam_id INT;
    DECLARE v_previous_score DECIMAL(5,2) DEFAULT NULL;
    DECLARE v_status ENUM('SUCCESS', 'ERROR', 'WARNING') DEFAULT 'ERROR';
    DECLARE v_error_message TEXT DEFAULT NULL;

    -- Iniciar la transacción
    START TRANSACTION;

    -- Buscar la inscripción (enrollment) a partir del estudiante
    SELECT e.enrollment_id
    INTO v_enrollment_id
    FROM `tbl_enrollments` e
    INNER JOIN `tbl_persons` s 
        ON e.`student_id` = s.`person_id`
    WHERE s.`first_name` = p_first_name
    AND CONCAT(s.`first_surname`, ' ', IFNULL(s.`second_surname`, '')) = p_last_names
    AND e.`exam_date` = p_exam_date
    LIMIT 1;
  
    -- Si no se encontró la inscripción, registrar el error y salir
    IF v_enrollment_id IS NULL THEN
    SET v_error_message = CONCAT(
            'Error al procesar el examen con tracktest ID: ', p_tracktest_id,
            '. No se encontró la inscripción para el estudiante ', p_first_name, ' ', p_last_names,
            ' en la fecha ', p_exam_date, '.'
        );
        INSERT INTO `tbl_logs_score` 
            (`process_id`, `tracktest_id`, `enrollment_id`, `previous_score`, `new_score`, `exam_date`, `status`, `error_message`)
        VALUES 
            (p_process_id, p_tracktest_id, NULL, NULL, p_new_score, p_exam_date, v_status, v_error_message);
        LEAVE proc_label;
    END IF;

    -- Verificar si ya existe un registro en tbl_english_exams para el tracktest_id
    SELECT exam_id
      INTO v_existing_exam_id
      FROM tbl_english_exams
     WHERE tracktest_id = p_tracktest_id
     LIMIT 1;
    
    IF v_existing_exam_id IS NOT NULL THEN
        -- Ya existe: utilizar ese exam_id para actualizar el registro existente
        SET v_exam_id = v_existing_exam_id;
        SELECT core
          INTO v_previous_score
          FROM tbl_english_exams
         WHERE exam_id = v_exam_id
           AND tracktest_id = p_tracktest_id
         LIMIT 1;

        UPDATE tbl_english_exams
           SET core = p_new_score,
               level = p_level
         WHERE exam_id = v_exam_id
           AND tracktest_id = p_tracktest_id;

        UPDATE tbl_exams
           SET exam_date = p_exam_date
         WHERE exam_id = v_exam_id;
         
        SET v_status = 'SUCCESS';
    ELSE
        -- No existe registro en tbl_english_exams para este tracktest_id
        -- Primero, buscar si ya existe un examen de tipo ENG para la inscripción
        SELECT exam_id
          INTO v_exam_id
          FROM tbl_exams
         WHERE enrollment_id = v_enrollment_id
           AND exam_type = 'ENG'
         LIMIT 1;
        
        IF v_exam_id IS NOT NULL THEN
            -- Existe el examen; se inserta el registro en tbl_english_exams
            INSERT INTO tbl_english_exams (exam_id, tracktest_id, core, level)
            VALUES (v_exam_id, p_tracktest_id, p_new_score, p_level);
        ELSE
            -- No existe un examen ENG: se crea uno nuevo en ambas tablas
            INSERT INTO tbl_exams (enrollment_id, exam_date, exam_type)
            VALUES (v_enrollment_id, p_exam_date, 'ENG');
            SET v_exam_id = LAST_INSERT_ID();
            INSERT INTO tbl_english_exams (exam_id, tracktest_id, core, level)
            VALUES (v_exam_id, p_tracktest_id, p_new_score, p_level);
        END IF;
        SET v_status = 'SUCCESS';
    END IF;
    
    -- Registrar el proceso en la tabla de logs
    INSERT INTO tbl_logs_score 
        (process_id, tracktest_id, enrollment_id, previous_score, new_score, exam_date, status, error_message)
    VALUES
        (p_process_id, p_tracktest_id, v_enrollment_id, v_previous_score, p_new_score, p_exam_date, v_status, v_error_message);
    
    COMMIT;
END//


-- This procedure groups the enrollments by the known_through field
-- and returns the count of students for each group

DELIMITER //
DROP PROCEDURE IF EXISTS usp_get_students_by_exam_source_filters //
CREATE PROCEDURE usp_get_students_by_exam_source_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    CASE e.known_through
      WHEN 'OH' THEN 'OpenHouse'
      WHEN 'SM' THEN 'Redes Sociales'
      WHEN 'FD' THEN 'Amigo'
      WHEN 'FM' THEN 'Familiar'
      ELSE 'Otros'
    END AS examSource,
    COUNT(*) AS studentCount
  FROM tbl_enrollments e
  WHERE
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All' OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY e.known_through;
END //
DELIMITER ;

DELIMITER //

DROP PROCEDURE IF EXISTS usp_get_enrollment_attendance_stats_filters //
CREATE PROCEDURE usp_get_enrollment_attendance_stats_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT, 
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    DATE(e.enrollment_date)          AS enrollmentDate,
    e.grade_to_enroll                AS grade,
    CASE
      WHEN e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH')
        THEN 'Primaria'
      ELSE 'Secundaria'
    END                               AS sector,
    COUNT(*)                         AS totalEnrolled,
    COUNT(DISTINCT ex.enrollment_id) AS totalAttended
  FROM tbl_enrollments e
  LEFT JOIN tbl_exams ex
    ON ex.enrollment_id = e.enrollment_id
    /* opcionalmente: AND ex.exam_type IN ('ACA','DAI') */
  WHERE 
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All'
      OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY enrollmentDate, grade, sector
  ORDER BY enrollmentDate, grade;
END //
DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS usp_get_admission_final_stats_filters //
CREATE PROCEDURE usp_get_admission_final_stats_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,    -- CSV of grades or 'All'
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    DATE(e.enrollment_date) AS enrollmentDate,
    e.grade_to_enroll       AS grade,
    CASE
      WHEN e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH') THEN 'Primaria'
      ELSE 'Secundaria'
    END                     AS sector,
    SUM(e.status = 'ACCEPTED') AS totalAccepted,
    SUM(e.status = 'REJECTED') AS totalRejected
  FROM tbl_Enrollments e
  WHERE
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All' OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY enrollmentDate, grade, sector
  ORDER BY enrollmentDate, grade;
END //
DELIMITER ;


DELIMITER //

-- 5a) Distribución de notas por dificultad (Academic Exam)
DROP PROCEDURE IF EXISTS usp_get_academic_exam_distribution_filters //
CREATE PROCEDURE usp_get_academic_exam_distribution_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    CASE q.question_level
      WHEN 'EASY'   THEN 'Fácil'
      WHEN 'MEDIUM' THEN 'Medio'
      WHEN 'HARD'   THEN 'Difícil'
    END                           AS difficulty,
    CAST(r.score AS DECIMAL(5,2)) AS examScore
  FROM tbl_exams e
  JOIN tbl_enrollments en
    ON en.enrollment_id = e.enrollment_id
  JOIN JSON_TABLE(
    e.responses,
    '$[*]'
    COLUMNS (
      questionId INT    PATH '$.questionId',
      score      DECIMAL(5,2) PATH '$.score'
    )
  ) AS r
    ON TRUE
  JOIN tbl_questions q
    ON q.question_id   = r.questionId
   AND q.question_type = 'ACA'
  WHERE e.exam_type = 'ACA'
    AND (p_start_date IS NULL OR DATE(e.exam_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.exam_date) <= p_end_date)
    AND (p_grades = 'All' OR FIND_IN_SET(en.grade_to_enroll, p_grades))
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND en.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND en.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    );
END //

DELIMITER ;

DELIMITER //
  
-- 5b) Media de puntajes por grado (Academic Exam)
DROP PROCEDURE IF EXISTS usp_get_academic_exam_grade_average_filters //
CREATE PROCEDURE usp_get_academic_exam_grade_average_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    en.grade_to_enroll AS grade,
    AVG(ae.grade)      AS averageScore
  FROM tbl_exams e
  JOIN tbl_academic_exams ae
    ON ae.exam_id = e.exam_id
  JOIN tbl_enrollments en
    ON en.enrollment_id = e.enrollment_id
  WHERE e.exam_type = 'ACA'
    AND (p_start_date IS NULL OR DATE(e.exam_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.exam_date) <= p_end_date)
    AND (p_grades = 'All' OR FIND_IN_SET(en.grade_to_enroll, p_grades))
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND en.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND en.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY en.grade_to_enroll
  ORDER BY en.grade_to_enroll;
END //

DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS usp_get_admission_funnel_trend_filters //
CREATE PROCEDURE usp_get_admission_funnel_trend_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,                   -- CSV list of grades or 'All'
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  /*
    Business question – 
    Are we moving prospects efficiently from “interested” to “accepted”, 
    and where are they getting stuck?
    
    Funnel stages:
      • Interested (status = 'PENDING')
      • Eligible   (status = 'ELIGIBLE')
      • Accepted   (status = 'ACCEPTED')
  */

  SELECT
    DATE(e.enrollment_date) AS enrollmentDate,

    -- raw counts at each stage
    COUNT(*)                                   AS interestedCount,
    SUM(e.status = 'ELIGIBLE')                 AS eligibleCount,
    SUM(e.status = 'ACCEPTED')                 AS acceptedCount,

    -- drop-off metrics
    ROUND(
      IF(COUNT(*) = 0, NULL, 
         SUM(e.status = 'ELIGIBLE') / COUNT(*) * 100
      ), 2
    ) AS pct_interested_to_eligible,
    ROUND(
      IF(SUM(e.status = 'ELIGIBLE') = 0, NULL, 
         SUM(e.status = 'ACCEPTED') / SUM(e.status = 'ELIGIBLE') * 100
      ), 2
    ) AS pct_eligible_to_accepted

  FROM tbl_enrollments e
  WHERE
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All'
      OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY DATE(e.enrollment_date)
  ORDER BY DATE(e.enrollment_date);
END //
DELIMITER ;

DELIMITER //

DROP PROCEDURE IF EXISTS `usp_get_lead_source_effectiveness_filters`//

CREATE PROCEDURE `usp_get_lead_source_effectiveness_filters`(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,                   -- CSV of grades or 'All'
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    CASE e.known_through
      WHEN 'OH' THEN 'OpenHouse'
      WHEN 'SM' THEN 'Redes Sociales'
      WHEN 'FD' THEN 'Amigo'
      WHEN 'FM' THEN 'Familiar'
      ELSE 'Otros'
    END                          AS examSource,
    COUNT(*)                     AS studentCount,
    ROUND(SUM(e.status = 'ACCEPTED')/COUNT(*) * 100, 2)    AS acceptanceRate,
    ROUND(AVG(ae.grade),2)       AS avgExamScore
  FROM tbl_enrollments e
  LEFT JOIN tbl_exams ex
    ON ex.enrollment_id = e.enrollment_id
    AND ex.exam_type = 'ACA'
  LEFT JOIN tbl_academic_exams ae
    ON ae.exam_id = ex.exam_id
  WHERE
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All'
      OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'   AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria' AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY e.known_through
  ORDER BY studentCount DESC;
END //
DELIMITER ;

DELIMITER //

DROP PROCEDURE IF EXISTS `usp_get_previous_grades_by_status_filters`//

CREATE PROCEDURE `usp_get_previous_grades_by_status_filters`(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,                   -- CSV of grades or 'All'
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    s.previous_grades     AS previousGrades,
    e.status              AS status
  FROM tbl_Students s
  JOIN tbl_Enrollments e
    ON e.student_id = s.student_id
  WHERE
    (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (p_grades = 'All' OR FIND_IN_SET(e.grade_to_enroll, p_grades))
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'
          AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria'
          AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    );
END //
DELIMITER ;

DELIMITER //

DROP PROCEDURE IF EXISTS usp_get_cefr_distribution_filters//

CREATE PROCEDURE usp_get_cefr_distribution_filters(
  IN p_start_date DATE,
  IN p_end_date   DATE,
  IN p_grades     TEXT,                     -- CSV or 'All'
  IN p_sector     ENUM('All','Primaria','Secundaria')
)
BEGIN
  SELECT
    ee.`level`   AS level,
    COUNT(*)     AS count
  FROM tbl_English_Exams ee
  JOIN tbl_Exams ex
    ON ee.exam_id = ex.exam_id
  JOIN tbl_Enrollments e
    ON ex.enrollment_id = e.enrollment_id
  WHERE e.status = 'ACCEPTED'
    AND (p_start_date IS NULL OR DATE(e.enrollment_date) >= p_start_date)
    AND (p_end_date   IS NULL OR DATE(e.enrollment_date) <= p_end_date)
    AND (
      p_grades = 'All'
      OR FIND_IN_SET(e.grade_to_enroll, p_grades)
    )
    AND (
      p_sector = 'All'
      OR (p_sector = 'Primaria'
          AND e.grade_to_enroll IN ('FIRST','SECOND','THIRD','FOURTH','FIFTH','SIXTH'))
      OR (p_sector = 'Secundaria'
          AND e.grade_to_enroll IN ('SEVENTH','EIGHTH','NINTH','TENTH'))
    )
  GROUP BY ee.`level`
  -- ensure A1→C2 ordering:
  ORDER BY FIELD(ee.`level`,'A1','A2','B1','B2','C1','C2');
END//
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;