-- Insert values into tbl_parentsguardians
INSERT INTO `tbl_parentsguardians`
(`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `relationship`)
VALUES ('Carlos', 'Rodríguez', 'Morales', 'CC', '900321654', '876543210', 'carlos.rod@example.com', 'F'),
       ('María', 'Jiménez', 'González', 'DI', '930122457', '856742013', 'maria.jimenez@example.com', 'M'),
       ('Luis', 'Alvarado', 'Martínez', 'PA', 'A12345678', '876520398', 'luis.alva@example.com', 'G'),
       ('Sofía', 'Vargas', 'Rojas', 'CC', '870231945', '834762019', 'sofia.vargas@example.com', 'M'),
       ('Jorge', 'Fernández', 'Solís', 'DI', '950456789', '896754320', 'jorge.fernandez@example.com', 'F');

-- Insert values into tbl_address
INSERT INTO `tbl_address`
(`country`, `province`, `city`, `district`, `address_info`, `parent_guardian_id`)
VALUES ('Costa Rica', 'San José', 'San José', 'Carmen', 'Avenida Central 100', 1),
       ('Costa Rica', 'Heredia', 'Heredia', 'San Francisco', 'Calle 8 #12', 2),
       ('Costa Rica', 'Alajuela', 'Alajuela', 'San Rafael', 'Calle la Paz 23', 3),
       ('Costa Rica', 'Cartago', 'Cartago', 'Dulce Nombre', 'Frente al parque', 4),
       ('Costa Rica', 'Puntarenas', 'Puntarenas', 'Barranca', '200m sur del hospital', 5);

-- Insert values into tbl_students
INSERT INTO `tbl_students`
(`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`,
 `has_accommodations`)
VALUES ('Andrés', 'Rodríguez', 'Morales', '2010-03-12', 'CC', '200123654', 'Escuela La Sabana', 0),
       ('Sofía', 'Jiménez', 'González', '2011-05-18', 'DI', '230459876', 'Escuela Central', 1),
       ('Felipe', 'Alvarado', 'Martínez', '2010-11-30', 'PA', 'P89012345', 'Escuela Monte Verde', 0),
       ('Lucía', 'Vargas', 'Rojas', '2009-07-25', 'CC', '190231475', 'Escuela El Carmen', 0),
       ('Javier', 'Fernández', 'Solís', '2008-09-16', 'DI', '260478951', 'Escuela Nueva Esperanza', 1);

-- Insert values into tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents`
    (`student_id`, `parentguardian_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);

-- Insert values into tbl_enrollments
INSERT INTO `tbl_enrollments`
(`status`, `enrollment_date`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`,
 `student_id`)
VALUES ('P', '2024-01-15 10:00:00', '6', 'SM', '2024-01-10', 1, 1, 1),
       ('E', '2024-01-16 11:30:00', '5', 'FM', '2024-01-11', 1, 0, 2),
       ('A', '2024-01-17 09:45:00', '7', 'OH', '2024-01-12', 1, 1, 3),
       ('R', '2024-01-18 08:15:00', '8', 'FD', '2024-01-13', 0, 1, 4),
       ('I', '2024-01-19 12:00:00', '9', 'OT', '2024-01-14', 1, 0, 5);

-- Insert values into tbl_documents
INSERT INTO `tbl_documents`
    (`document_name`, `document_type`, `document_url`, `enrollment_id`)
VALUES ('Health Certificate Andrés', 'HC', '/home/user/docs/andres_health.pdf', 1),
       ('Other Document Sofía', 'OT', '/home/user/docs/sofia_other.pdf', 2),
       ('Health Certificate Felipe', 'HC', '/home/user/docs/felipe_health.pdf', 3),
       ('Health Certificate Lucía', 'HC', '/home/user/docs/lucia_health.pdf', 4),
       ('Other Document Javier', 'OT', '/home/user/docs/javier_other.pdf', 5);


-- Insert values into tbl_parentsguardians
INSERT INTO `tbl_parentsguardians`
(`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `relationship`)
VALUES ('Ana', 'Martínez', 'Lopez', 'CC', '900654321', '876542019', 'ana.martinez@example.com', 'M'),
       ('Ricardo', 'Gómez', 'Ramírez', 'DI', '920312678', '865742013', 'ricardo.gomez@example.com', 'F'),
       ('Laura', 'Pérez', 'Castro', 'PA', 'A76543210', '876590348', 'laura.perez@example.com', 'G'),
       ('Gabriel', 'Sánchez', 'Hernández', 'CC', '870567890', '834792019', 'gabriel.sanchez@example.com', 'M'),
       ('Elena', 'Cordero', 'Solano', 'DI', '940123789', '896734320', 'elena.cordero@example.com', 'F');

-- Insert values into tbl_address
INSERT INTO `tbl_address`
(`country`, `province`, `city`, `district`, `address_info`, `parent_guardian_id`)
VALUES ('Costa Rica', 'Guanacaste', 'Liberia', 'Curubandé', '200m norte del supermercado', 6),
       ('Costa Rica', 'San José', 'Desamparados', 'San Rafael', 'Casa 45, Calle Principal', 7),
       ('Costa Rica', 'Heredia', 'Barva', 'San Pablo', 'Calle 10 #3', 8),
       ('Costa Rica', 'Cartago', 'Paraíso', 'Orosi', '50m sur del parque', 9),
       ('Costa Rica', 'Puntarenas', 'Esparza', 'Macacona', 'Detrás de la iglesia', 10);

-- Insert values into tbl_students
INSERT INTO `tbl_students`
(`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`,
 `has_accommodations`)
VALUES ('Daniela', 'Martínez', 'Lopez', '2012-04-21', 'CC', '201654987', 'Escuela La Paz', 1),
       ('Pablo', 'Gómez', 'Ramírez', '2013-06-10', 'DI', '230987654', 'Escuela Nueva Vida', 0),
       ('Camila', 'Pérez', 'Castro', '2011-09-30', 'PA', 'P87654321', 'Escuela San Isidro', 0),
       ('Mateo', 'Sánchez', 'Hernández', '2009-05-05', 'CC', '191234567', 'Escuela Santa Marta', 1),
       ('Valeria', 'Cordero', 'Solano', '2008-10-14', 'DI', '270456789', 'Escuela San Lucas', 0);

-- Insert values into tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents`
    (`student_id`, `parentguardian_id`)
VALUES (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Insert values into tbl_enrollments
INSERT INTO `tbl_enrollments`
(`status`, `enrollment_date`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`,
 `student_id`)
VALUES ('P', '2024-02-01 09:00:00', '4', 'SM', '2024-01-28', 1, 0, 6),
       ('E', '2024-02-02 10:15:00', '3', 'FM', '2024-01-29', 0, 1, 7),
       ('A', '2024-02-03 11:30:00', '5', 'OH', '2024-01-30', 1, 0, 8),
       ('R', '2024-02-04 08:45:00', '6', 'FD', '2024-01-31', 1, 1, 9),
       ('I', '2024-02-05 12:30:00', '7', 'OT', '2024-02-01', 0, 1, 10);

-- Insert values into tbl_documents
INSERT INTO `tbl_documents`
    (`document_name`, `document_type`, `document_url`, `enrollment_id`)
VALUES ('Health Certificate Daniela', 'HC', 'file_example.pdf', 6),
       ('Other Document Pablo', 'OT', 'file_example.pdf', 7),
       ('Health Certificate Camila', 'HC', 'file_example.pdf', 8),
       ('Health Certificate Mateo', 'HC', 'file_example.pdf', 9),
       ('Other Document Valeria', 'OT', 'file_example.pdf', 10);

-- Insert value into tbl_examperiods
INSERT INTO `tbl_examperiods` (start_date, end_date)
VALUES ('2024-09-01', '2024-09-30');

-- Insert value into tbl_examdays
INSERT INTO `tbl_examdays` (exam_period_id, exam_day, start_time)
VALUES (1, 'M', '08:00:00'); -- Lunes del primer periodo

-- Insert value into tbl_daiquestions
INSERT INTO `tbl_daiquestions` (question_grade, question_text, image_url)
VALUES ('2', '¿Como te sientes el dia de hoy?', NULL);

INSERT INTO `tbl_academicquestions` (question_grade, option_a, option_b, option_c, option_d, correct_option,
                                     question_text)
VALUES ('2', 'Paris', 'Madrid', 'Londres', 'San Jose', 'A', '¿Cual es la capital de Francia?');

INSERT INTO `tbl_systemconfig` (`config_name`, `config_value`)
VALUES ('dai_weight', 0.4),
       ('academic_weight', 0.4),
       ('english_weight', 0.2);

-- Password: 'campus12' test sysadmin
INSERT INTO `tbl_users` (`email`, `user_password`, `role`)
VALUES ('sysadmin@cit.co.cr', '$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S', 'S');

-- Password: 'campus' test admin
INSERT INTO `tbl_users` (`email`, `user_password`, `role`)
VALUES ('marta@cit.co.cr', '$2a$10$15bZTAy6CG3OlPgl3glJxuROyEajUOTdKX9qx43Pa0JkTPR2ga2He', 'A');

-- Password: 'Omera32' test psico
INSERT INTO `tbl_users` (`email`, `user_password`, `role`)
VALUES ('jorge@cit.co.cr', '$2a$10$RFPObfy6ro87gLXQalrEiuGehgDsyWfETW4h9h51eg1ZUWlpMnrIG', 'P');

-- Password: 'Mate8520' test teacher
INSERT INTO `tbl_users` (`email`, `user_password`, `role`)
VALUES ('rocio@cit.co.cr', '$2a$10$yNXsgRdu2T72V6xznUni7e6PDtcx4ZYq3XvNVAbIcRnhWBoIgGMTO', 'T');

-- Insert value into tbl_systemconfig, puntos de contacto
INSERT INTO `tbl_systemconfig` (`config_name`, `config_value`)
VALUES ('email_contact', 'contactocit@ctpcit.co.cr'),
       ('email_notifications_contact', 'notificaciones@ctpcit.co.cr'),
       ('whatsapp_contact', '88090041'),
       ('office_contact', '22370186'),
       ('instagram_contact', 'ComplejoEducativoCIT'),
       ('facebook_contact', 'ComplejoEducativoCIT');