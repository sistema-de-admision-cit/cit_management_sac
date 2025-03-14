DELETE
FROM `tbl_academic_exams`
WHERE `exam_id` IN (SELECT `exam_id`
                    FROM `tbl_exams`
                    WHERE `enrollment_id` IN (SELECT `enrollment_id`
                                              FROM `tbl_enrollments`
                                              WHERE `student_id` = 11));

DELETE
FROM `tbl_dai_exams`
WHERE `exam_id` IN (SELECT `exam_id`
                    FROM `tbl_exams`
                    WHERE `enrollment_id` IN (SELECT `enrollment_id`
                                              FROM `tbl_enrollments`
                                              WHERE `student_id` = 11));

DELETE
FROM `tbl_english_exams`
WHERE `exam_id` IN (SELECT `exam_id`
                    FROM `tbl_exams`
                    WHERE `enrollment_id` IN (SELECT `enrollment_id`
                                              FROM `tbl_enrollments`
                                              WHERE `student_id` = 11));

DELETE
FROM `tbl_exams`
WHERE `enrollment_id` IN (SELECT `enrollment_id`
                          FROM `tbl_enrollments`
                          WHERE `student_id` = 11);
