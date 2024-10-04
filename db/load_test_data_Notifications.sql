use db_cit_test;

INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('email_contact', 'contactocit@ctpcit.co.cr');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('email_notifications_contact', 'notificaciones@ctpcit.co.cr');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('whatsapp_contact', '88090041');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('office_contact', '22370186');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('instagram_contact', 'ComplejoEducativoCIT');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('facebook_contact', 'ComplejoEducativoCIT');


SELECT * FROM tbl_systemconfig;