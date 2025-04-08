UPDATE `tbl_enrollments`
SET `exam_date`             = '2025-03-14',
    `status`                = 'PENDING',
    `whatsapp_notification` = 1
WHERE `enrollment_id` = 1;
