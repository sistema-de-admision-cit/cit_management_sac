USE `db_cit`;

-- Insert values into tbl_persons
INSERT INTO `tbl_persons`
    (`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`)
VALUES
    ('Carlos', 'Rodríguez', 'Morales', 'CC', '900321654'),
    ('María', 'Jiménez', 'González', 'DI', '930122457'),
    ('Luis', 'Alvarado', 'Martínez', 'PA', 'A12345678'),
    ('Sofía', 'Vargas', 'Rojas', 'CC', '870231945'),
    ('Jorge', 'Fernández', 'Solís', 'DI', '950456789'),
    ('Ana', 'Martínez', 'Lopez', 'CC', '900654321'),
    ('Ricardo', 'Gómez', 'Ramírez', 'DI', '920312678'),
    ('Laura', 'Pérez', 'Castro', 'PA', 'A76543210'),
    ('Gabriel', 'Sánchez', 'Hernández', 'CC', '870567890'),
    ('Elena', 'Cordero', 'Solano', 'DI', '940123789'),
    ('Andrés', 'Rodríguez', 'Morales', 'CC', '200123654'), -- Student
    ('Sofía', 'Jiménez', 'González', 'DI', '230459876'),
    ('Felipe', 'Alvarado', 'Martínez', 'PA', 'P89012345'),
    ('Lucía', 'Vargas', 'Rojas', 'CC', '190231475'),
    ('Javier', 'Fernández', 'Solís', 'DI', '260478951'),
    ('Daniela', 'Martínez', 'Lopez', 'CC', '201654987'),
    ('Pablo', 'Gómez', 'Ramírez', 'DI', '230987654'),
    ('Camila', 'Pérez', 'Castro', 'PA', 'P87654321'),
    ('Mateo', 'Sánchez', 'Hernández', 'CC', '191234567'),
    ('Valeria', 'Cordero', 'Solano', 'DI', '270456789');


INSERT INTO `tbl_parents`
    (`parent_id`, `phone_number`, `email`, `relationship`)
VALUES
    (1, '876543210', 'carlos.rod@example.com', 'F'),
    (2,'856742013', 'maria.jimenez@example.com', 'M'),
    (3, '876520398', 'luis.alva@example.com', 'G'),
    (4, '834762019', 'sofia.vargas@example.com', 'M'),
    (5, '896754320', 'jorge.fernandez@example.com', 'F'),
    (6, '876542019', 'ana.martinez@example.com', 'M'),
    (7, '865742013', 'ricardo.gomez@example.com', 'F'),
    (8, '876590348', 'laura.perez@example.com', 'G'),
    (9, '834792019', 'gabriel.sanchez@example.com', 'F'),
    (10, '896734320', 'elena.cordero@example.com', 'M');

-- Insert values into tbl_address
INSERT INTO `tbl_address`
    (`country`, `province`, `city`, `district`, `address_info`, `parent_id`)
VALUES
    ('Costa Rica', 'San José', 'San José', 'Carmen', 'Avenida Central 100', 1),
    ('Costa Rica', 'Heredia', 'Heredia', 'San Francisco', 'Calle 8 #12', 2),
    ('Costa Rica', 'Alajuela', 'Alajuela', 'San Rafael', 'Calle la Paz 23', 3),
    ('Costa Rica', 'Cartago', 'Cartago', 'Dulce Nombre', 'Frente al parque', 4),
    ('Costa Rica', 'Puntarenas', 'Puntarenas', 'Barranca', '200m sur del hospital', 5),
    ('Costa Rica', 'Guanacaste', 'Liberia', 'Curubandé', '200m norte del supermercado', 6),
    ('Costa Rica', 'San José', 'Desamparados', 'San Rafael', 'Casa 45, Calle Principal', 7),
    ('Costa Rica', 'Heredia', 'Barva', 'San Pablo', 'Calle 10 #3', 8),
    ('Costa Rica', 'Cartago', 'Paraíso', 'Orosi', '50m sur del parque', 9),
    ('Costa Rica', 'Puntarenas', 'Esparza', 'Macacona', 'Detrás de la iglesia', 10);

-- Insert values into tbl_students
INSERT INTO `tbl_students`
    (`student_id`, `birth_date`, `previous_school`, `has_accommodations`)
VALUES
    (11,'2010-03-12', 'Escuela La Sabana', 0),
    (12,'2011-05-18', 'Escuela Central', 1),
    (13,'2010-11-30', 'Escuela Monte Verde', 0),
    (14,'2009-07-25', 'Escuela El Carmen', 0),
    (15,'2008-09-16', 'Escuela Nueva Esperanza', 1),
    (16,'2012-04-21', 'Escuela La Paz', 1),
    (17,'2013-06-10', 'Escuela Nueva Vida', 0),
    (18,'2011-09-30', 'Escuela San Isidro', 0),
    (19,'2009-05-05', 'Escuela Santa Marta', 1),
    (20,'2008-10-14', 'Escuela San Lucas', 0);

-- Insert values into tbl_parents_students
INSERT INTO `tbl_parents_students`
    (`parent_id`, `student_id`)
VALUES (1, 11),
       (2, 12),
       (3, 13),
       (4, 14),
       (5, 15),
       (6, 16),
       (7, 17),
       (8, 18),
       (9, 19),
       (10, 20);

-- Insert values into tbl_enrollments
INSERT INTO `tbl_enrollments`
(`status`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`, `student_id`)
VALUES ('PENDING','SIXTH', 'SM', '2024-01-10', 1, 1, 11),
       ('ELIGIBLE','FIFTH', 'FM', '2024-01-11', 1, 0, 11),
       ('ACCEPTED','SEVENTH', 'OH', '2024-01-12', 1, 1, 13),
       ('REJECTED','EIGHTH', 'FD', '2024-01-13', 0, 1, 14),
       ('INELIGIBLE','NINTH', 'OT', '2024-01-14', 1, 0, 15),
       ('PENDING','FOURTH', 'SM', '2024-01-28', 1, 0, 16),
       ('ELIGIBLE','THIRD', 'FM', '2024-01-29', 0, 1, 17),
       ('ACCEPTED','FIFTH', 'OH', '2024-01-30', 1, 0, 18),
       ('REJECTED','SIXTH', 'FD', '2024-01-31', 1, 1, 19),
       ('INELIGIBLE','SEVENTH', 'OT', '2024-02-01', 0, 1, 20);

-- Insert values into tbl_documents
INSERT INTO `tbl_documents`
    (`document_name`, `document_type`, `document_url`, `enrollment_id`)
VALUES ('Documento de Notas', 'OT', 'grades_200123654_856332114336.pdf', 1),
       ('Documento de Notas', 'OT', '/home/user/docs/sofia_other.pdf', 2),
       ('Documento de Adecuaciones', 'AC', '/home/user/docs/felipe_health.pdf', 3),
       ('Documento de Adecuaciones', 'AC', '/home/user/docs/lucia_health.pdf', 4),
       ('Documento de Notas', 'OT', '/home/user/docs/javier_other.pdf', 5),
       ('Documento de Adecuaciones', 'AC', 'file_example.pdf', 6),
       ('Documento de Notas', 'OT', 'file_example.pdf', 7),
       ('Documento de Adecuaciones', 'AC', 'file_example.pdf', 8),
       ('Documento de Adecuaciones', 'AC', 'file_example.pdf', 9),
       ('Documento de Notas', 'OT', 'file_example.pdf', 10);

-- Insert value into tbl_examperiods
INSERT INTO `tbl_exam_periods` (`start_date`, `end_date`)
VALUES ('2024-09-01', '2024-09-30');

-- Insert value into tbl_examdays
INSERT INTO `tbl_exam_days` (exam_period_id, exam_day, start_time)
VALUES (1, 'M', '08:00:00'); -- Lunes del primer periodo

-- Insert value into tbl_questions
INSERT INTO `tbl_questions` (`question_type`,`question_text`, `image_url`, `question_grade`, `question_level`, `selection_type`)
VALUES ('DAI', '¿Cómo te sientes el día de hoy?', NULL, 'SECOND', 'EASY', 'PARAGRAPH'),
       ('ACA', '¿Como se calcula el area de un circulo?', NULL, 'FIRST', 'EASY', 'SINGLE'),
       ('DAI', '¿Qué tipo de música prefieres?', NULL, 'THIRD', 'MEDIUM', 'PARAGRAPH'),
       ('ACA', '¿Qué estación del año sigue despues del verano?', NULL, 'FOURTH', 'HARD', 'SINGLE'),
       ('DAI', '¿Te gusta practicar deportes?', NULL, 'FIFTH', 'EASY', 'PARAGRAPH'),
       ('ACA', '¿Cual es la temperatura de ebullición del agua?', NULL, 'SIXTH', 'MEDIUM', 'SINGLE'),
       ('DAI', '¿Prefieres la playa o la montaña?', NULL, 'SEVENTH', 'HARD', 'PARAGRAPH'),
       ('ACA', '¿Cual es la formula de la teoría de la relatividad?', NULL, 'EIGHTH', 'EASY', 'SINGLE'),
       ('DAI', '¿Cuál es tu película favorita?', NULL, 'NINTH', 'MEDIUM', 'PARAGRAPH'),
       ('ACA', '¿Cual es la unidad de medida de los líquidos?', NULL, 'TENTH', 'HARD', 'SINGLE');

-- Insert value into tbl_question_options
INSERT INTO `tbl_question_options` (`question_id`, `option`, `is_correct`)
VALUES
-- Para la pregunta 1 (¿Como se calcula el área de un círculo?)
(2, 'π * radio^2', 1),  -- Correcta
(2, '2 * radio', 0),
(2, 'π * diámetro', 0),
(2, 'radio * altura', 0),

-- Para la pregunta 2 (¿Qué estación del año sigue después del verano?)
(4, 'Invierno', 0),
(4, 'Otoño', 1),  -- Correcta
(4, 'Primavera', 0),
(4, 'Verano', 0),

-- Para la pregunta 3 (¿Cual es la temperatura de ebullición del agua?)
(6, '100°C', 1),  -- Correcta
(6, '90°C', 0),
(6, '150°C', 0),
(6, '50°C', 0),

-- Para la pregunta 4 (¿Cuál es la fórmula de la teoría de la relatividad?)
(8, 'E = mc^2', 1),  -- Correcta
(8, 'F = ma', 0),
(8, 'P = mv', 0),
(8, 'a^2 + b^2 = c^2', 0),

-- Para la pregunta 5 (¿Cuál es la unidad de medida de los líquidos?)
(10, 'Litros', 1),  -- Correcta
(10, 'Metros', 0),
(10, 'Gramos', 0),
(10, 'Pulgadas', 0);

-- Insert value into tbl_system_config
INSERT INTO `tbl_system_config` (`config_name`, `config_value`)
VALUES ('PREV_GRADES_WEIGHT', 0.4),
       ('ACADEMIC_WEIGHT', 0.4),
       ('ENGLISH_WEIGHT', 0.2),
       ('EMAIL_CONTACT', 'contactocit@ctpcit.co.cr'),
       ('EMAIL_NOTIFICATION_CONTACT', 'notificaciones@ctpcit.co.cr'),
       ('WHATSAPP_CONTACT', '88090041'),
       ('OFFICE_CONTACT', '22370186'),
       ('INSTAGRAM_CONTACT', 'ComplejoEducativoCIT'),
       ('FACEBOOK_CONTACT', 'ComplejoEducativoCIT');

-- Password: 'campus12' test sysadmin
INSERT INTO `tbl_users` (`email`, `username`, `user_password`, `role`)
VALUES ('sysadmin@cit.co.cr', 'Sysadmin', '$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S', 'SYS'),
       ('marta@cit.co.cr', 'Marta', '$2a$10$15bZTAy6CG3OlPgl3glJxuROyEajUOTdKX9qx43Pa0JkTPR2ga2He', 'ADMIN'),
       ('jorge@cit.co.cr', 'Jorge', '$2a$10$RFPObfy6ro87gLXQalrEiuGehgDsyWfETW4h9h51eg1ZUWlpMnrIG', 'PSYCHOLOGIST'),
       ('rocio@cit.co.cr', 'Rocio', '$2a$10$yNXsgRdu2T72V6xznUni7e6PDtcx4ZYq3XvNVAbIcRnhWBoIgGMTO', 'TEACHER');
