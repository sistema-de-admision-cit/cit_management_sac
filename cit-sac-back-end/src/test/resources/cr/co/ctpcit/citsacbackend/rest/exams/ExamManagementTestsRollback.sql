UPDATE `tbl_dai_exams`
SET `recommendation` = NULL,
    `comment` = '',
    `reviewed` = 0
WHERE exam_id = 2;

DELETE FROM `tbl_english_exams`
WHERE `tracktest_id` = 1;

DELETE FROM `tbl_logs_score`
WHERE `tracktest_id` = 1;

DELETE FROM `tbl_exams`
WHERE `enrollment_id` = 10 AND `exam_type` = 'ENG';
