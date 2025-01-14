DELETE
FROM `tbl_documents`
WHERE `enrollment_id` = (SELECT `enrollment_id`
                         FROM `tbl_enrollments`
                         WHERE `student_id` = (SELECT `student_id`
                                               FROM `tbl_students`
                                               WHERE `birth_date` = '2007-12-03'
                                                 AND `previous_school` = 'Manhattan School'));

DELETE
FROM `tbl_enrollments`
WHERE `student_id` = (SELECT `student_id`
                      FROM `tbl_students`
                      WHERE `birth_date` = '2007-12-03'
                        AND `previous_school` = 'Manhattan School');

DELETE
FROM `tbl_address`
WHERE `parent_id` = (SELECT `parent_id`
                     FROM `tbl_parents`
                     WHERE `phone_number` = '88889999');

DELETE
FROM `tbl_parents_students`
WHERE `student_id` = (SELECT `student_id`
                      FROM `tbl_students`
                      WHERE `birth_date` = '2007-12-03'
                        AND `previous_school` = 'Manhattan School');

DELETE
FROM `tbl_students`
WHERE `birth_date` = '2007-12-03'
  AND `previous_school` = 'Manhattan School';

DELETE
FROM `tbl_parents`
WHERE `phone_number` = '88889999';

DELETE
FROM `tbl_persons`
WHERE `id_number` = '123456789' OR `id_number` = '987654321';
