use db_cit_test;

DELIMITER //

CREATE PROCEDURE usp_SystemConfig_Notifications_Update(
    IN p_email_contact VARCHAR(255),
    IN p_email_notifications_contact VARCHAR(255),
    IN p_whatsapp_contact VARCHAR(255),
    IN p_office_contact VARCHAR(255),
    IN p_instagram_contact VARCHAR(255),
    IN p_facebook_contact VARCHAR(255)
)
BEGIN
    -- Verificar si los registros existen y si no, insertarlos
    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'email_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('email_contact', p_email_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_email_contact WHERE config_name = 'email_contact';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'email_notifications_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('email_notifications_contact', p_email_notifications_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_email_notifications_contact WHERE config_name = 'email_notifications_contact';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'whatsapp_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('whatsapp_contact', p_whatsapp_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_whatsapp_contact WHERE config_name = 'whatsapp_contact';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'office_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('office_contact', p_office_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_office_contact WHERE config_name = 'office_contact';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'instagram_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('instagram_contact', p_instagram_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_instagram_contact WHERE config_name = 'instagram_contact';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = 'facebook_contact') THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES ('facebook_contact', p_facebook_contact);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_facebook_contact WHERE config_name = 'facebook_contact';
    END IF;
END //

DELIMITER ;


INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('email_contact', 'contactocit@ctpcit.co.cr');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('email_notifications_contact', 'notificaciones@ctpcit.co.cr');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('whatsapp_contact', '88090041');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('office_contact', '22370186');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('instagram_contact', 'ComplejoEducativoCIT');
INSERT INTO `db_cit_test`.`tbl_systemconfig` (`config_name`, `config_value`) VALUES ('facebook_contact', 'ComplejoEducativoCIT');



SELECT * FROM tbl_systemconfig;