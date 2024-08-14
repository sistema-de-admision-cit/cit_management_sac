-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema db_cit_test
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_cit_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_cit_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
SHOW WARNINGS;
USE `db_cit_test` ;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicquestions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_text` VARCHAR(255) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `option_a` VARCHAR(255) NOT NULL,
  `option_b` VARCHAR(255) NOT NULL,
  `option_c` VARCHAR(255) NOT NULL,
  `option_d` VARCHAR(255) NOT NULL,
  `correct_option` CHAR(1) NOT NULL,
  CONSTRAINT PK_AcademicQuestions PRIMARY KEY (`question_id`),
  UNIQUE INDEX `UQ_AcademicQuestions_Question` (`question_text` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_parentsguardians`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_parentsguardians` (
  `parent_guardian_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `id_type` ENUM('cc', 'di', 'pa') NOT NULL,
  `id_number` VARCHAR(20) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `home_address` VARCHAR(100) NOT NULL,
  `relationship` ENUM('mother', 'father', 'guardian') NOT NULL,
  CONSTRAINT PK_ParentsGuardians PRIMARY KEY (`parent_guardian_id`),
  UNIQUE INDEX `UQ_ParentsGuardians_IdNumber` (`id_number` ASC) VISIBLE,
  UNIQUE INDEX `UQ_ParentsGuardians_PhoneNumber` (`phone_number` ASC) VISIBLE,
  UNIQUE INDEX `UQ_ParentsGuardians_Email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_students` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `parent_guardian_id` INT NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `birth_date` DATE NOT NULL,
  `id_type` ENUM('cc', 'di', 'pa') NOT NULL,
  `id_number` VARCHAR(20) NOT NULL,
  `previous_school` VARCHAR(100) NULL DEFAULT NULL,
  `has_accommodations` TINYINT(1) NOT NULL,
  CONSTRAINT PK_Students PRIMARY KEY (`student_id`, `parent_guardian_id`),
  UNIQUE INDEX `UQ_Students_IdNumber` (`id_number` ASC) VISIBLE,
  INDEX `FK_Students_ParentsGuardians` (`parent_guardian_id` ASC) VISIBLE,
  CONSTRAINT `FK_Students_ParentsGuardians`
    FOREIGN KEY (`parent_guardian_id`)
    REFERENCES `db_cit_test`.`tbl_parentsguardians` (`parent_guardian_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_enrollments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_enrollments` (
  `enrollment_id` INT NOT NULL AUTO_INCREMENT,
  `student_id` INT NOT NULL,
  `status` ENUM('pending', 'approved', 'rejected', 'passed', 'failed') NOT NULL,
  `enrollment_date` DATE NOT NULL,
  `grade_to_enroll` ENUM('2', '3', '4', '5', '6', '7', '8', '9') NOT NULL,
  `known_through` ENUM('facebook', 'instagram', 'whatsapp', 'friend', 'family', 'other') NOT NULL,
  `exam_date` DATE NOT NULL,
  `consent_given` TINYINT(1) NOT NULL,
  `whatsapp_notification` TINYINT(1) NOT NULL,
  CONSTRAINT PK_Enrollments PRIMARY KEY (`enrollment_id`, `student_id`),
  INDEX `FK_Enrollments_Students` (`student_id` ASC) VISIBLE,
  CONSTRAINT `FK_Enrollments_Students`
    FOREIGN KEY (`student_id`)
    REFERENCES `db_cit_test`.`tbl_students` (`student_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `exam_date` DATE NOT NULL,
  `grade` DECIMAL(5,2) NOT NULL,
  CONSTRAINT PK_AcademicExams PRIMARY KEY (`exam_id`, `enrollment_id`),
  INDEX `FK_AcademicExams_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_AcademicExams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicanswers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicanswers` (
  `answer_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `exam_id` INT NOT NULL,
  `student_answer` CHAR(1) NOT NULL,
  CONSTRAINT PK_AcademicAnswers PRIMARY KEY (`answer_id`, `question_id`, `exam_id`),
  INDEX `FK_AcademicAnswers_Questions` (`question_id` ASC) VISIBLE,
  INDEX `fk_tbl_academicanswers_tbl_academicexams1_idx` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `FK_AcademicAnswers_Questions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_academicquestions` (`question_id`),
  CONSTRAINT `fk_tbl_academicanswers_tbl_academicexams1`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_academicexams` (`exam_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicexamquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicexamquestions` (
  `exam_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  CONSTRAINT PK_AcademicExamQuestions PRIMARY KEY (`exam_id`, `question_id`),
  INDEX `FK_AcademicExamQuestions_AcademicQuestions` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FK_AcademicExamQuestions_AcademicExams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_academicexams` (`exam_id`),
  CONSTRAINT `FK_AcademicExamQuestions_AcademicQuestions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_academicquestions` (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiquestions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_text` VARCHAR(255) NULL DEFAULT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  CONSTRAINT PK_DAIQuestions PRIMARY KEY (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `exam_date` DATE NOT NULL,
  `grade` DECIMAL(5,2) NOT NULL,
  CONSTRAINT PK_DAIExams PRIMARY KEY (`exam_id`, `enrollment_id`),
  INDEX `FK_DAIExams_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIExams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daianswers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daianswers` (
  `answer_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `exam_id` INT NOT NULL,
  `student_answer` TEXT NOT NULL,
  CONSTRAINT PK_DAIAnswers PRIMARY KEY (`answer_id`, `question_id`, `exam_id`),
  INDEX `FK_DAIAnswers_Questions` (`question_id` ASC) VISIBLE,
  INDEX `fk_tbl_daianswers_tbl_daiexamens1_idx` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIAnswers_Questions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_daiquestions` (`question_id`),
  CONSTRAINT `fk_tbl_daianswers_tbl_daiexamens1`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_daiexams` (`exam_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiexamquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiexamquestions` (
  `exam_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  CONSTRAINT PK_DAIExamQuestions PRIMARY KEY (`exam_id`, `question_id`),
  INDEX `FK_DAIExamQuestions_DAIQuestions` (`question_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIExamQuestions_DAIExamens`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_daiexams` (`exam_id`),
  CONSTRAINT `FK_DAIExamQuestions_DAIQuestions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_daiquestions` (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_documents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_documents` (
  `document_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `document_type` ENUM('cc', 'di', 'pa', 'health_certificate', 'other') NOT NULL,
  `document_url` VARCHAR(255) NOT NULL,
  CONSTRAINT PK_Documents PRIMARY KEY (`document_id`, `enrollment_id`),
  UNIQUE INDEX `UQ_Documents_Enrollment_DocumentType` (`enrollment_id` ASC, `document_type` ASC) VISIBLE,
  CONSTRAINT `FK_Documents_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_examperiods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_examperiods` (
  `exam_period_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  CONSTRAINT PK_ExamPeriods PRIMARY KEY (`exam_period_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_examdays`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_examdays` (
  `exam_day_id` INT NOT NULL AUTO_INCREMENT,
  `exam_period_id` INT NOT NULL,
  `exam_day` ENUM('monday', 'tuesday', 'wednesday', 'thursday', 'friday') NOT NULL,
  `start_time` TIME NOT NULL,
  CONSTRAINT PK_ExamDays PRIMARY KEY (`exam_day_id`, `exam_period_id`),
  INDEX `FK_ExamDays_ExamPeriods` (`exam_period_id` ASC) VISIBLE,
  CONSTRAINT `FK_ExamDays_ExamPeriods`
    FOREIGN KEY (`exam_period_id`)
    REFERENCES `db_cit_test`.`tbl_examperiods` (`exam_period_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_englishexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_englishexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `exam_date` DATE NOT NULL,
  `grade` DECIMAL(5,2) NOT NULL,
  CONSTRAINT PK_EnglishExams PRIMARY KEY (`exam_id`, `enrollment_id`),
  INDEX `FK_EnglishExams_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_EnglishExams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_log` (
  `log_id` INT NOT NULL AUTO_INCREMENT,
  `table_name` VARCHAR(50) NOT NULL,
  `column_name` VARCHAR(50) NOT NULL,
  `old_value` TEXT NULL DEFAULT NULL,
  `new_value` TEXT NULL DEFAULT NULL,
  `changed_by` INT NOT NULL,
  `changed_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `query` TEXT NULL,
  CONSTRAINT PK_Log PRIMARY KEY (`log_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_systemconfig`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_systemconfig` (
  `config_id` INT NOT NULL AUTO_INCREMENT,
  `config_name` VARCHAR(100) NOT NULL,
  `config_value` VARCHAR(255) NOT NULL,
  CONSTRAINT PK_SystemConfig PRIMARY KEY (`config_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(25) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `role` ENUM('admin', 'teacher', 'psychologist') NOT NULL,
  CONSTRAINT PK_Users PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UQ_Users_Email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
USE `db_cit_test` ;

-- -----------------------------------------------------
-- Placeholder table for view `db_cit_test`.`VW_grades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`VW_grades` (`enrollment_id` INT, `exam_type` INT, `grade` INT);
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `db_cit_test`.`VW_grades`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_cit_test`.`VW_grades`;
SHOW WARNINGS;
USE `db_cit_test`;
CREATE  OR REPLACE VIEW `VW_grades` AS
SELECT enrollment_id, 'academic' AS exam_type, grade FROM tbl_academicexams
UNION ALL 
SELECT enrollment_id, 'dai' AS exam_type, grade FROM tbl_daiexams
UNION ALL
SELECT enrollment_id, 'english' AS exam_type, grade FROM tbl_englishexams;
SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
