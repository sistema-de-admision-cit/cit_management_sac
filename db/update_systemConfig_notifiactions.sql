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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        -- rollback the transaction
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error en la transacción';
    END;

    START TRANSACTION;

    -- call the procedure to update or insert
    CALL usp_UpdateOrInsertConfig('email_contact', p_email_contact);
    CALL usp_UpdateOrInsertConfig('email_notifications_contact', p_email_notifications_contact);
    CALL usp_UpdateOrInsertConfig('whatsapp_contact', p_whatsapp_contact);
    CALL usp_UpdateOrInsertConfig('office_contact', p_office_contact);
    CALL usp_UpdateOrInsertConfig('instagram_contact', p_instagram_contact);
    CALL usp_UpdateOrInsertConfig('facebook_contact', p_facebook_contact);

    COMMIT;
END //

DELIMITER ;

DELIMITER //

-- procedure to update or insert a system config
CREATE PROCEDURE usp_UpdateOrInsertConfig(
    IN p_config_name VARCHAR(255),
    IN p_config_value VARCHAR(255)
)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM tbl_systemconfig WHERE config_name = p_config_name) THEN
        INSERT INTO tbl_systemconfig (config_name, config_value) VALUES (p_config_name, p_config_value);
    ELSE
        UPDATE tbl_systemconfig SET config_value = p_config_value WHERE config_name = p_config_name;
    END IF;
END //

DELIMITER ;
