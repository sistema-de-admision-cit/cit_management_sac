-- Insert for tbl_parentsguardians
INSERT INTO `tbl_parentsguardians` (`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `relationship`)
VALUES
    ('Carlos', 'Jimenez', 'Solis', 'CC', '12345678', '50680001111', 'carlos.jimenez@mail.com', 'F'),
    ('Ana', 'Martinez', 'Lopez', 'DI', '98765432', '50680002222', 'ana.martinez@mail.com', 'M'),
    ('Pedro', 'Rojas', 'Quesada', 'PA', 'A1234567', '50680003333', 'pedro.rojas@mail.com', 'G'),
    ('Maria', 'Garcia', 'Vargas', 'CC', '56789012', '50680004444', 'maria.garcia@mail.com', 'M'),
    ('Juan', 'Fernandez', 'Mora', 'DI', '12398765', '50680005555', 'juan.fernandez@mail.com', 'F');

-- Insert for tbl_address (all addresses in Costa Rica)
INSERT INTO `tbl_address` (`country`, `province`, `city`, `district`, `address_info`, `parent_guardian_id`)
VALUES
    ('Costa Rica', 'San Jose', 'San Jose', 'Catedral', 'Calle 1, Avenida 2', 1),
    ('Costa Rica', 'Heredia', 'Heredia', 'San Francisco', 'Calle 10, Casa 23', 2),
    ('Costa Rica', 'Cartago', 'Cartago', 'Oriental', 'Avenida 3, Casa 5', 3),
    ('Costa Rica', 'Alajuela', 'Alajuela', 'San Jose', 'Calle Principal, Edificio Azul', 4),
    ('Costa Rica', 'Puntarenas', 'Esparza', 'Esparza', 'Calle Larga, Casa 7', 5);

-- Insert for tbl_students
INSERT INTO `tbl_students` (`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`, `has_accommodations`)
VALUES
    ('Luis', 'Jimenez', 'Solis', '2010-05-15', 'CC', 'S123456', 'Escuela Central', 0),
    ('Sofia', 'Martinez', 'Lopez', '2011-09-20', 'DI', 'S987654', 'Escuela del Norte', 1),
    ('Daniel', 'Rojas', 'Quesada', '2009-03-22', 'PA', 'S345678', 'Escuela del Este', 0),
    ('Camila', 'Garcia', 'Vargas', '2012-01-18', 'CC', 'S876543', 'Escuela del Sur', 1),
    ('Miguel', 'Fernandez', 'Mora', '2011-12-05', 'DI', 'S456789', 'Escuela Oeste', 0);

-- Insert for tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents` (`student_id`, `parentguardian_id`)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Insert for tbl_enrollments
INSERT INTO `tbl_enrollments` (`status`, `enrollment_date`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`, `student_id`)
VALUES
    ('P', '2024-06-10 10:00:00', '7', 'SM', '2024-06-15', 1, 1, 1),
    ('E', '2024-06-11 10:30:00', '8', 'OH', '2024-06-16', 1, 1, 2),
    ('I', '2024-06-12 11:00:00', '9', 'FD', '2024-06-17', 1, 0, 3),
    ('A', '2024-06-13 11:30:00', '10', 'FM', '2024-06-18', 1, 1, 4),
    ('R', '2024-06-14 12:00:00', '6', 'OT', '2024-06-19', 0, 0, 5);
