DELETE
FROM `tbl_documents`
WHERE `enrollment_id` IN (SELECT `enrollment_id`
                          FROM `tbl_enrollments`
                          WHERE `student_id` IN (SELECT `student_id`
                                                 FROM `tbl_students`
                                                 WHERE `birth_date` = '1989-12-16'
                                                    OR `birth_date` = '2017-12-31'
                                                     AND `previous_school` = 'Escuela de Getsemaní'
                                                    OR `previous_school` = 'Escuela Jose Figueres Ferrer'));

DELETE
FROM `tbl_documents`
WHERE `enrollment_id` = (SELECT `enrollment_id`
                         FROM `tbl_enrollments`
                         WHERE `student_id` = '20'
                             AND `exam_date` = '2024-12-15'
                            OR `exam_date` = '2024-12-16');


DELETE
FROM `tbl_enrollments`
WHERE `student_id` IN (SELECT `student_id`
                       FROM `tbl_students`
                       WHERE `birth_date` = '1989-12-16'
                          OR `birth_date` = '2017-12-31'
                           AND `previous_school` = 'Escuela de Getsemaní'
                          OR `previous_school` = 'Escuela Jose Figueres Ferrer');

DELETE
FROM `tbl_enrollments`
WHERE `student_id` = '20'
  AND `exam_date` = '2024-12-15' OR `exam_date` = '2024-12-16';

DELETE
FROM `tbl_parents_students`
WHERE `student_id` IN (SELECT `student_id`
                       FROM `tbl_students`
                       WHERE `birth_date` = '1989-12-16'
                          OR `birth_date` = '2017-12-31'
                           AND `previous_school` = 'Escuela de Getsemaní'
                          OR `previous_school` = 'Escuela Jose Figueres Ferrer');

DELETE
FROM `tbl_students`
WHERE `birth_date` = '1989-12-16'
   OR `birth_date` = '2017-12-31'
    AND `previous_school` = 'Escuela de Getsemaní'
   OR `previous_school` = 'Escuela Jose Figueres Ferrer';

DELETE
FROM `tbl_address`
WHERE `parent_id` = (SELECT `person_id`
                     FROM `tbl_persons`
                     WHERE `id_number` = '100030683');

DELETE
FROM `tbl_parents`
WHERE `parent_id` = (SELECT `person_id`
                     FROM `tbl_persons`
                     WHERE `id_number` = '100030683');

DELETE
FROM `tbl_persons`
WHERE `id_number` = '603540987'
   OR `id_number` = '100030683'
   OR `id_number` = '503690412';


