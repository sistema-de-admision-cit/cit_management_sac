-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_cit_test
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_cit_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_cit_test` ;

-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_students`
-- id_type
	-- CC: cedula
	-- DI: dimex
	-- PA: passport
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_students` (
  `student_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `first_surname` VARCHAR(32) NOT NULL,
  `second_surname` VARCHAR(32) NULL DEFAULT NULL,
  `birth_date` DATE NOT NULL,
  `id_type` ENUM('CC', 'DI', 'PA') NOT NULL,
  `id_number` VARCHAR(20) NOT NULL,
  `previous_school` VARCHAR(100) NULL DEFAULT NULL,
  `has_accommodations` BOOLEAN NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE INDEX `UQ_Students_IdNumber` (`id_number` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_enrollments`
-- status:
	-- P: initial state when student is enrolled
	-- E: state when the student is accepted to do the exam
	-- I: state when the student is rejected to do the exam
	-- A: state when the student passed successfully the entire process
	-- R: state when the student fail the admision process.
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
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_enrollments` (
  `enrollment_id` INT NOT NULL AUTO_INCREMENT,
  `student_id` INT NOT NULL,
  `status` ENUM('P', 'E', 'I', 'A', 'R') NOT NULL,
  `enrollment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `grade_to_enroll` ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10') NOT NULL,
  `known_through` ENUM('SM', 'OH', 'FD', 'FM', 'OT') NOT NULL,
  `exam_date` DATE NOT NULL,
  `consent_given` BOOLEAN NOT NULL,
  `whatsapp_notification` BOOLEAN NOT NULL,
  PRIMARY KEY (`enrollment_id`),
  INDEX `FK_Enrollments_Students` (`student_id` ASC) VISIBLE,
  CONSTRAINT `FK_Enrollments_Students`
    FOREIGN KEY (`student_id`)
    REFERENCES `db_cit_test`.`tbl_students` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `exam_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `grade` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`exam_id`),
  INDEX `FK_AcademicExams_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_AcademicExams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicquestions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_grade` ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10') NOT NULL,
  `question_text` VARCHAR(255) NOT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  `option_a` VARCHAR(255) NOT NULL,
  `option_b` VARCHAR(255) NOT NULL,
  `option_c` VARCHAR(255) NOT NULL,
  `option_d` VARCHAR(255) NOT NULL,
  `correct_option` CHAR(1) NOT NULL,
  PRIMARY KEY (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_academicexamquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_academicexamquestions` (
  `exam_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `student_answer` CHAR(1) NULL DEFAULT NULL,
  PRIMARY KEY (`exam_id`, `question_id`),
  INDEX `FK_AcademicExamQuestions_AcademicQuestions` (`question_id` ASC) VISIBLE,
  INDEX `FK_AcademicExamQuestions_AcademicExams` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `FK_AcademiExamQuestions_AcademicExams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_academicexams` (`exam_id`),
  CONSTRAINT `FK_AcademiExamQuestions_AcademicQuestions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_academicquestions` (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daigrades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daigrades` (
  `daigrades_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  PRIMARY KEY (`daigrades_id`),
  INDEX `FK_DAIGrades_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIGrades_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `daigrades_id` INT NOT NULL,
  `exam_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `grade` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`exam_id`),
  INDEX `FK_DAIExams_DAIGrades` (`daigrades_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIExams_DAIGrades`
    FOREIGN KEY (`daigrades_id`)
    REFERENCES `db_cit_test`.`tbl_daigrades` (`daigrades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiquestions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `question_grade` ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10') NOT NULL,
  `question_text` VARCHAR(255) NOT NULL,
  `image_url` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiexamquestions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiexamquestions` (
  `exam_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `student_answer` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`exam_id`, `question_id`),
  INDEX `FK_DAIExamQuestions_DAIQuestions` (`question_id` ASC) INVISIBLE,
  INDEX `FK_DAIExamQuestions_DAIExams` (`exam_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIExamQuestions_DAIExams`
    FOREIGN KEY (`exam_id`)
    REFERENCES `db_cit_test`.`tbl_daiexams` (`exam_id`),
  CONSTRAINT `FK_DAIExamQuestions_DAIQuestions`
    FOREIGN KEY (`question_id`)
    REFERENCES `db_cit_test`.`tbl_daiquestions` (`question_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_daiinterview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_daiinterview` (
  `interview_id` INT NOT NULL AUTO_INCREMENT,
  `daigrades_id` INT NOT NULL,
  `interview_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `grade` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`interview_id`),
  INDEX `FK_DAIInterview_DAIGrades` (`daigrades_id` ASC) VISIBLE,
  CONSTRAINT `FK_DAIInterview_DAIGrades`
    FOREIGN KEY (`daigrades_id`)
    REFERENCES `db_cit_test`.`tbl_daigrades` (`daigrades_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_documents`
-- document_type
	-- HC: when a student requires any acommodation, 
	-- has to provide a psicological letter to certificate the condition.
    -- OT: other type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_documents` (
  `document_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `document_type` ENUM('HC', 'OT') NOT NULL,
  `document_url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`document_id`),
  INDEX `FK_Documents_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_Documents_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_englishexams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_englishexams` (
  `exam_id` INT NOT NULL AUTO_INCREMENT,
  `enrollment_id` INT NOT NULL,
  `exam_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `grade` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`exam_id`),
  INDEX `FK_EnglishExams_Enrollments` (`enrollment_id` ASC) VISIBLE,
  CONSTRAINT `FK_EnglishExams_Enrollments`
    FOREIGN KEY (`enrollment_id`)
    REFERENCES `db_cit_test`.`tbl_enrollments` (`enrollment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_examperiods`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_examperiods` (
  `exam_period_id` INT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  PRIMARY KEY (`exam_period_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_examdays`
-- exam_day:
	-- M: monday
	-- K: tuesday
	-- W: wednesday
	-- T: thursday
	-- F: friday
	-- S: saturday
	-- SS: sunday
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_examdays` (
  `exam_day_id` INT NOT NULL AUTO_INCREMENT,
  `exam_period_id` INT NOT NULL,
  `exam_day` ENUM('M', 'K', 'W', 'T', 'F', 'S', 'SS') NOT NULL,
  `start_time` TIME NOT NULL,
  PRIMARY KEY (`exam_day_id`),
  INDEX `FK_ExamDays_ExamPeriods` (`exam_period_id` ASC) VISIBLE,
  CONSTRAINT `FK_ExamDays_ExamPeriods`
    FOREIGN KEY (`exam_period_id`)
    REFERENCES `db_cit_test`.`tbl_examperiods` (`exam_period_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_log` (
  `log_id` INT NOT NULL AUTO_INCREMENT,
  `table_name` VARCHAR(50) NOT NULL,
  `column_name` VARCHAR(50) NOT NULL,
  `old_value` TEXT NOT NULL,
  `new_value` TEXT NOT NULL,
  `changed_by` INT NOT NULL,
  `changed_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `query` TEXT NULL DEFAULT NULL,
  `comment` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`log_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_parentsguardians`
-- id_type
	-- CC: cedula
	-- DI: dimex
	-- PA: passport
-- relationship
	-- M: mother
	-- F: father
    -- G: guardian
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_parentsguardians` (
  `parent_guardian_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `first_surname` VARCHAR(32) NOT NULL,
  `second_surname` VARCHAR(32) NULL DEFAULT NULL,
  `id_type` ENUM('CC', 'DI', 'PA') NOT NULL,
  `id_number` VARCHAR(20) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `home_address` VARCHAR(100) NOT NULL,
  `relationship` ENUM('M', 'F', 'G') NOT NULL,
  PRIMARY KEY (`parent_guardian_id`),
  UNIQUE INDEX `UQ_ParentsGuardians_IdNumber` (`id_number` ASC) VISIBLE,
  UNIQUE INDEX `UQ_ParentsGuardians_PhoneNumber` (`phone_number` ASC) VISIBLE,
  UNIQUE INDEX `UQ_ParentsGuardians_Email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_systemconfig`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_systemconfig` (
  `config_id` INT NOT NULL AUTO_INCREMENT,
  `config_name` VARCHAR(100) NOT NULL,
  `config_value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`config_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_users`
-- role
	-- S: superuser
    -- A: admin
    -- T: teacher
    -- P: psychologist
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(25) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `role` ENUM('S', 'A', 'T', 'P') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `UQ_Users_Email` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



-- -----------------------------------------------------
-- Table `db_cit_test`.`tbl_parentguardianstudents`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_cit_test`.`tbl_parentguardianstudents` (
  `student_id` INT NOT NULL,
  `parentguardian_id` INT NOT NULL,
  PRIMARY KEY (`student_id`, `parentguardian_id`),
  INDEX `FK_ParentGuardianStudents_ParentGuardian` (`parentguardian_id` ASC) INVISIBLE,
  INDEX `FK_ParentGuardianStudents_Student` (`student_id` ASC) INVISIBLE,
  CONSTRAINT `FK_ParentGuardianStudents_Student`
    FOREIGN KEY (`student_id`)
    REFERENCES `db_cit_test`.`tbl_students` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ParentGuardianStudents_ParentGuardian`
    FOREIGN KEY (`parentguardian_id`)
    REFERENCES `db_cit_test`.`tbl_parentsguardians` (`parent_guardian_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
