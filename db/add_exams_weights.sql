-- ejecutar este alter si la tabla ya existe, si no, ejecutar el script de creación de la tabla
-- ALTER TABLE `db_cit_test`.`tbl_systemconfig`
-- ADD CONSTRAINT `uq_config_name` UNIQUE (`config_name`);

INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`)
VALUES ('dai_weight', 0.4),
       ('academic_weight', 0.4),
       ('english_weight', 0.2);

-- Select all weight related to exams
SELECT config_name, config_value FROM `db_cit_test`.`tbl_systemconfig` WHERE config_name LIKE '%weight%';