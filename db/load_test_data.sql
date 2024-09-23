use db_cit_test;
INSERT INTO db_cit_test.tbl_examperiods (start_date, end_date) 
VALUES ('2024-09-01', '2024-09-30'); 
INSERT INTO db_cit_test.tbl_examdays (exam_period_id, exam_day, start_time) 
VALUES (1, 'M', '08:00:00'); -- Lunes del primer periodo

INSERT INTO tbl_daiquestions (question_grade, question_text, image_url)
VALUES 
('2', '¿Como te sientes el dia de hoy?',NULL);


INSERT INTO tbl_academicquestions (question_grade, option_a, option_b, option_c, option_d, correct_option, question_text)
VALUES ('2', 'Paris', 'Madrid', 'Londres', 'San Jose', 'A', '¿Cual es la capital de Francia?');