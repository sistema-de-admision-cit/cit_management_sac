-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_cit_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_cit` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE `db_cit`;

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
    ON DELETE NO ACTION,
  UNIQUE INDEX `UQ_Parents_PhoneNumber` (`phone_number` ASC),
  UNIQUE INDEX `UQ_Parents_Email` (`email` ASC))
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
  `country` VARCHAR(16) NOT NULL,
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
  -- ELIGIBLE: student is elegible to go through the admission process
  -- INELIGIBLE: student is not elegible to go through the admission process
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
  `document_name` VARCHAR(32) NOT NULL,
  `document_type` ENUM('AC', 'OT') NOT NULL,
  `document_url` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`document_id`,`enrollment_id`),
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
  -- UNIQUE: unique answer
  -- MULTIPLE: multiple answer
--------------------------------------------------------
-- `question_type`:
  -- ACA: academic
  -- DAI: psiometric
--------------------------------------------------------
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
  `selection_type` ENUM('UNIQUE', 'MULTIPLE', 'PARAGRAPH') NOT NULL DEFAULT 'PARAGRAPH',
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
  PRIMARY KEY (`option_id`, `question_id`),
  INDEX `IDX_QuestionOptions_QuestionID` (`question_id` ASC),
  INDEX `IDX_QuestionOptions_OptionID` (`option_id` ASC),

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
    ON DELETE NO ACTION)
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
  `recommendation` ENUM('ADMIT', 'REJECT') NOT NULL,
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
    REFERENCES `tbl_Exam_Periods` (`exam_period_id`))
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
  `changed_by` INT NOT NULL,
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
  `enrollment_id` INT NULL DEFAULT NULL,
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
DROP TABLE IF EXISTS `tbl_System_Config`;

CREATE TABLE IF NOT EXISTS `tbl_System_Config` (
  `config_id` INT NOT NULL AUTO_INCREMENT,
  `config_name` VARCHAR(32) NOT NULL,
  `config_value` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE INDEX `UQ_Config_Name` (`config_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `tbl_Users`
-- `role`:
  -- SYS: system administrator
  -- ADMIN: administrator
  -- TEACHER: teacher
  -- PSI: psicologist
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tbl_Users` ;

CREATE TABLE IF NOT EXISTS `tbl_Users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(128) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `user_password` VARCHAR(128) NOT NULL,
  `role` ENUM('SYS', 'ADMIN', 'TEACHER', 'PSYCHOLOGIST') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UQ_Users_Email` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- Stored Procedures
-- -----------------------------------------------------
DELIMITER //
-- -----------------------------------------------------
-- Procedure: usp_Update_Enrollment_And_Log
-- Description: Updates the status, exam date, and whatsapp permission of an enrollment and logs the changes

DROP PROCEDURE IF EXISTS usp_Update_Enrollment_And_Log//

CREATE PROCEDURE usp_Update_Enrollment_And_Log ( 
    IN p_enrollment_id INT,
    IN p_new_status ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED'),
    IN p_new_exam_date DATE,
    IN p_new_whatsapp_permission BOOLEAN,
    IN p_comment VARCHAR(255),
    IN p_changed_by INT
)
BEGIN
    DECLARE v_old_status ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED');
    DECLARE v_old_exam_date DATE;
    DECLARE v_old_whatsapp_permission BOOLEAN;
    DECLARE v_valid_status ENUM('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED');

    -- Obtener los valores actuales de la inscripción
    SELECT `status`, `exam_date`, `whatsapp_notification`
      INTO v_old_status, v_old_exam_date, v_old_whatsapp_permission
      FROM `tbl_Enrollments`
     WHERE `enrollment_id` = p_enrollment_id;

    -- Validar el nuevo estado: si es válido se usa, de lo contrario se conserva el antiguo
    SET v_valid_status = CASE 
                           WHEN p_new_status IN ('PENDING', 'ELIGIBLE', 'INELIGIBLE', 'ACCEPTED', 'REJECTED')
                           THEN p_new_status
                           ELSE v_old_status
                         END;
                         
    -- Validar la nueva fecha de examen: si se pasa NULL se conserva la fecha anterior
    SET p_new_exam_date = CASE 
                            WHEN p_new_exam_date IS NOT NULL THEN p_new_exam_date 
                            ELSE v_old_exam_date 
                          END;

    -- Validar el nuevo permiso de WhatsApp: si se pasa NULL se conserva el valor anterior
    SET p_new_whatsapp_permission = CASE 
                                      WHEN p_new_whatsapp_permission IS NOT NULL THEN p_new_whatsapp_permission 
                                      ELSE v_old_whatsapp_permission 
                                    END;

    -- Actualizar solo los valores que resultaron válidos
    UPDATE `tbl_Enrollments`
       SET `status` = v_valid_status,
           `exam_date` = p_new_exam_date,
           `whatsapp_notification` = p_new_whatsapp_permission
     WHERE `enrollment_id` = p_enrollment_id;

    -- Registrar en la tabla de logs si el estado ha cambiado
    IF v_old_status != v_valid_status THEN
        INSERT INTO `tbl_Logs` 
            (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES 
            ('tbl_Enrollments', 'status', v_old_status, v_valid_status, p_changed_by, 
             CONCAT('UPDATE tbl_Enrollments SET status = ', v_valid_status, ' WHERE enrollment_id = ', p_enrollment_id), p_comment);
    END IF;

    -- Registrar en la tabla de logs si la fecha de examen ha cambiado
    IF v_old_exam_date != p_new_exam_date THEN
        INSERT INTO `tbl_Logs` 
            (`table_name`, `column_name`, `old_value`, `new_value`, `changed_by`, `query`, `comment`)
        VALUES 
            ('tbl_Enrollments', 'exam_date', v_old_exam_date, p_new_exam_date, p_changed_by, 
             CONCAT('UPDATE tbl_Enrollments SET exam_date = ', p_new_exam_date, ' WHERE enrollment_id = ', p_enrollment_id), p_comment);
    END IF;
    -- No se requiere log para whatsapp_notification
END//

-- -----------------------------------------------------
-- Procedure: usp_SystemConfig_Notifications_Update
-- Description: Updates the system configuration for notifications

DROP PROCEDURE IF EXISTS usp_SystemConfig_Notifications_Update//

CREATE PROCEDURE usp_SystemConfig_Notifications_Update(
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
-- Procedure: usp_Update_Or_Insert_Config
-- Description: Updates or inserts a system configuration

DROP PROCEDURE IF EXISTS usp_Update_Or_Insert_Config//

-- procedure to update or insert a system config
CREATE PROCEDURE usp_Update_Or_Insert_Config(
    IN p_config_name VARCHAR(32),
    IN p_config_value VARCHAR(128)
)

BEGIN
    IF NOT EXISTS (SELECT 1 FROM tbl_System_Config WHERE config_name = p_config_name) THEN
        INSERT INTO tbl_System_Config (config_name, config_value) VALUES (p_config_name, p_config_value);
    ELSE
        UPDATE tbl_System_Config SET config_value = p_config_value WHERE config_name = p_config_name;
    END IF;
END//

-- -----------------------------------------------------
-- Procedure: usp_Process_English_Exam_And_Log
-- Description: Processes an English exam and logs the results

DROP PROCEDURE IF EXISTS usp_Process_English_Exam_And_Log//

CREATE PROCEDURE usp_Process_English_Exam_And_Log(
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
    DECLARE v_status ENUM('SUCCESS', 'ERROR', 'WARNING') DEFAULT 'ERROR';
    DECLARE v_error_message TEXT DEFAULT NULL;

    -- Iniciar una transacción
    START TRANSACTION;

    -- Buscar la inscripción (enrollment)
    SELECT e.enrollment_id
    INTO v_enrollment_id
    FROM `tbl_Enrollments` e
    INNER JOIN `tbl_Persons` s 
        ON e.`student_id` = s.`person_id`
    WHERE s.`first_name` = p_first_name
    AND CONCAT(s.`first_surname`, ' ', IFNULL(s.`second_surname`, '')) = p_last_names
    AND e.`exam_date` = p_exam_date
    LIMIT 1;

    -- Verificar si se encontró la inscripción
    IF v_enrollment_id IS NULL THEN
        -- Si no se encontró, registrar en los logs con mensaje de error
    SET v_error_message = CONCAT('Hubo un error al procesar el examen con el ID de tracktest: ', p_tracktest_id, 
        '. No se encontró la inscripción para el estudiante ', 
        UPPER(LEFT(p_first_name, 1)), LOWER(SUBSTRING(p_first_name, 2)), ' ', 
        UPPER(LEFT(p_last_names, 1)), LOWER(SUBSTRING(p_last_names, 2)), 
        ' en la fecha ', p_exam_date, '.');


        INSERT INTO `tbl_Logs_Score` 
            (`process_id`, `tracktest_id`, `enrollment_id`, `previous_score`, `new_score`, `exam_date`, `status`, `error_message`)
        VALUES 
            (p_process_id, p_tracktest_id, NULL, NULL, p_new_score, p_exam_date, v_status, v_error_message);

        -- Finalizar el procedimiento sin continuar procesando el examen
        LEAVE proc_label;
    END IF;

    -- Obtener la nota anterior, si existe
    SELECT `core` INTO v_previous_score
    FROM `tbl_English_Exams`
    WHERE `exam_id` = (
        SELECT `exam_id` FROM `tbl_Exams`
        WHERE `enrollment_id` = v_enrollment_id
        AND `exam_type` = 'ENG'
        LIMIT 1
    )
    AND `tracktest_id` = p_tracktest_id
    LIMIT 1;

    -- Actualizar o insertar el nuevo examen
    IF v_previous_score IS NOT NULL THEN
        -- Si existe, actualizamos el registro
        UPDATE `tbl_English_Exams`
        SET `core` = p_new_score, `level` = p_level
        WHERE `exam_id` = v_enrollment_id
        AND `tracktest_id` = p_tracktest_id;

        UPDATE `tbl_Exams`
        SET `exam_date` = p_exam_date
        WHERE `enrollment_id` = v_enrollment_id
        AND `exam_type` = 'ENG';
        
        SET v_status = 'SUCCESS';
    ELSE
        -- Si no existe, insertamos un nuevo registro
        INSERT INTO `tbl_Exams` 
          (`enrollment_id`, `exam_date`, `exam_type`)
        VALUES 
          (v_enrollment_id, p_exam_date, 'ENG');

        INSERT INTO `tbl_English_Exams` 
          (`exam_id`, `tracktest_id`, `core`, `level`)
        VALUES 
          (LAST_INSERT_ID(), p_tracktest_id, p_new_score, p_level);
        
        SET v_status = 'SUCCESS';
    END IF;

    -- Registrar el proceso en la tabla de logs
    INSERT INTO `tbl_Logs_Score` 
        (`process_id`, `tracktest_id`, `enrollment_id`, `previous_score`, `new_score`, `exam_date`, `status`, `error_message`)
    VALUES
        (p_process_id, p_tracktest_id, v_enrollment_id, v_previous_score, p_new_score, p_exam_date, v_status, v_error_message);

    -- Confirmar la transacción
    COMMIT;

END//

-- End of the stored procedures
-- ----------------------------------------------------- 
DELIMITER ;
-- -----------------------------------------------------

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;