DELETE
FROM `tbl_exams`
WHERE `enrollment_id` IN (SELECT `enrollment_id`
                          FROM `tbl_enrollments`
                          WHERE `student_id` = 11);
