DELETE
FROM `tbl_documents`
WHERE `enrollment_id` IN (SELECT `enrollment_id`
                         FROM `tbl_enrollments`
                         WHERE `student_id` IN (SELECT `student_id`
                                               FROM `tbl_students`
                                               WHERE `birth_date` = '2007-12-03' OR `birth_date` = '2000-12-31'
                                                 AND `previous_school` = 'Manhattan School'));

DELETE
FROM `tbl_enrollments`
WHERE `student_id` IN (SELECT `student_id`
                      FROM `tbl_students`
                      WHERE `birth_date` = '2007-12-03' OR `birth_date` = '2000-12-31'
                        AND `previous_school` = 'Manhattan School');

DELETE
FROM `tbl_address`
WHERE `parent_id` IN (SELECT `parent_id`
                     FROM `tbl_parents`
                     WHERE `phone_number` = '88889999');

DELETE
FROM `tbl_parents_students`
WHERE `student_id` IN (SELECT `student_id`
                      FROM `tbl_students`
                      WHERE `birth_date` = '2007-12-03' OR `birth_date` = '2000-12-31'
                        AND `previous_school` = 'Manhattan School');

DELETE
FROM `tbl_students`
WHERE `birth_date` = '2007-12-03' OR `birth_date` = '2000-12-31'
  AND `previous_school` = 'Manhattan School';

DELETE
FROM `tbl_parents`
WHERE `phone_number` = '88889999';

DELETE
FROM `tbl_persons`
WHERE `id_number` = '123456789' OR `id_number` = '987654321' OR `id_number` = '605530232';
