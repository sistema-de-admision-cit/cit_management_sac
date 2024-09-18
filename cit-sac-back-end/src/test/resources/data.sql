-- Insert into tbl_parentsguardians
INSERT INTO `tbl_parentsguardians`
(`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `relationship`)
VALUES
    ('Ana', 'Martinez', 'Lopez', 'CC', '403660523', '87654321', 'ana.martinez@mail.com', 'M'),
    ('Luis', 'Gomez', 'Perez', 'DI', '012345678901234', '87654322', 'luis.gomez@mail.com', 'F'),
    ('Maria', 'Rodriguez', 'Fernandez', 'PA', 'P1234567890CR', '87654323', 'maria.rodriguez@mail.com', 'M'),
    ('Carlos', 'Hernandez', 'Vargas', 'DI', '987654321012345', '87654324', 'carlos.hernandez@mail.com', 'G'),
    ('Sofia', 'Martinez', 'Castro', 'CC', '403660524', '87654325', 'sofia.jimenez@mail.com', 'F');

-- Insert into tbl_address
INSERT INTO `tbl_address`
(`country`, `province`, `city`, `district`, `address_info`, `parent_guardian_id`)
VALUES
    ('Costa Rica', 'San Jose', 'San Jose', 'Carmen', 'Calle 12, Casa 8', 1),
    ('Costa Rica', 'Heredia', 'Heredia', 'San Francisco', 'Avenida 5, Apartamento 3', 2),
    ('Costa Rica', 'Alajuela', 'Alajuela', 'Central', 'Calle 8, Casa 10', 3),
    ('Costa Rica', 'Cartago', 'Cartago', 'Oriental', 'Avenida 4, Casa 5', 4),
    ('Costa Rica', 'Limon', 'Limon', 'Central', 'Calle 1, Edificio A', 5),
    ('Costa Rica', 'Heredia', 'San Rafael', 'Concepción', 'Frente a plaza de Concepción', 4);

-- Insert into tbl_students
INSERT INTO `tbl_students`
(`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`, `has_accommodations`)
VALUES
    ('Pedro', 'Martinez', 'Lopez', '2010-04-15', 'CC', '504660525', 'Escuela Central', 0),
    ('Lucia', 'Gomez', 'Perez', '2011-06-20', 'DI', '223456789012345', 'Escuela Norte', 0),
    ('Jorge', 'Rodriguez', 'Fernandez', '2009-09-09', 'PA', 'P9876543210CR', 'Escuela Sur', 1),
    ('Carla', 'Martinez', 'Vargas', '2008-03-25', 'DI', '334567890123456', 'Escuela Este', 0),
    ('Juan', 'Jimenez', 'Castro', '2012-11-12', 'CC', '603660526', 'Escuela Oeste', 0);

-- Insert into tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents`
(`student_id`, `parentguardian_id`)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Insert into tbl_enrollments
INSERT INTO `tbl_enrollments`
(`status`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`, `student_id`)
VALUES
    ('P', '1', 'SM', '2024-09-01', 1, 1, 1),
    ('E', '2', 'OH', '2024-09-02', 1, 1, 2),
    ('I', '3', 'FD', '2024-09-03', 1, 0, 3),
    ('A', '4', 'FM', '2024-09-04', 1, 1, 4),
    ('R', '5', 'OT', '2024-09-05', 0, 1, 5);