UPDATE `tbl_enrollments`
SET `exam_date`             = '2024-01-10',
    `status`                = 'PENDING',
    `whatsapp_notification` = 1
WHERE `enrollment_id` = 1;
