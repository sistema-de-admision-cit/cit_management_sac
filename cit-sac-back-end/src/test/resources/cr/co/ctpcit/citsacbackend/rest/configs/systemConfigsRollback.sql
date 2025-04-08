UPDATE tbl_system_config
SET config_value = '0.4'
WHERE config_name = 'PREV_GRADES_WEIGHT';

UPDATE tbl_system_config
SET config_value = '0.4'
WHERE config_name = 'ACADEMIC_WEIGHT';

UPDATE tbl_system_config
SET config_value = '0.2'
WHERE config_name = 'ENGLISH_WEIGHT';

UPDATE tbl_system_config
SET config_value = 'contactocit@ctpcit.co.cr'
WHERE config_name = 'EMAIL_CONTACT';

UPDATE tbl_system_config
SET config_value = 'notificaciones@ctpcit.co.cr'
WHERE config_name = 'EMAIL_NOTIFICATION_CONTACT';

UPDATE tbl_system_config
SET config_value = '88090041'
WHERE config_name = 'WHATSAPP_CONTACT';

UPDATE tbl_system_config
SET config_value = '22370186'
WHERE config_name = 'OFFICE_CONTACT';

UPDATE tbl_system_config
SET config_value = 'ComplejoEducativoCIT'
WHERE config_name = 'INSTAGRAM_CONTACT';

UPDATE tbl_system_config
SET config_value = 'ComplejoEducativoCIT'
WHERE config_name = 'FACEBOOK_CONTACT';

DELETE
FROM tbl_exam_days
WHERE exam_period_id = (SELECT exam_period_id
                        FROM tbl_exam_periods
                        WHERE start_date = '2021-01-01'
                          AND end_date = '2021-03-15');

DELETE
FROM tbl_exam_periods
WHERE start_date = '2021-01-01'
  AND end_date = '2021-03-15';
