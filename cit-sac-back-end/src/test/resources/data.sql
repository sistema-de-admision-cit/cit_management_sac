-- Insert values into tbl_parentsguardians
INSERT INTO `tbl_parentsguardians`
(`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `relationship`)
VALUES
    ('Carlos', 'Rodríguez', 'Morales', 'CC', '900321654', '876543210', 'carlos.rod@example.com', 'F'),
    ('María', 'Jiménez', 'González', 'DI', '930122457', '856742013', 'maria.jimenez@example.com', 'M'),
    ('Luis', 'Alvarado', 'Martínez', 'PA', 'A12345678', '876520398', 'luis.alva@example.com', 'G'),
    ('Sofía', 'Vargas', 'Rojas', 'CC', '870231945', '834762019', 'sofia.vargas@example.com', 'M'),
    ('Jorge', 'Fernández', 'Solís', 'DI', '950456789', '896754320', 'jorge.fernandez@example.com', 'F');

-- Insert values into tbl_address
INSERT INTO `tbl_address`
(`country`, `province`, `city`, `district`, `address_info`, `parent_guardian_id`)
VALUES
    ('Costa Rica', 'San José', 'San José', 'Carmen', 'Avenida Central 100', 1),
    ('Costa Rica', 'Heredia', 'Heredia', 'San Francisco', 'Calle 8 #12', 2),
    ('Costa Rica', 'Alajuela', 'Alajuela', 'San Rafael', 'Calle la Paz 23', 3),
    ('Costa Rica', 'Cartago', 'Cartago', 'Dulce Nombre', 'Frente al parque', 4),
    ('Costa Rica', 'Puntarenas', 'Puntarenas', 'Barranca', '200m sur del hospital', 5);

-- Insert values into tbl_students
INSERT INTO `tbl_students`
(`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`, `has_accommodations`)
VALUES
    ('Andrés', 'Rodríguez', 'Morales', '2010-03-12', 'CC', '200123654', 'Escuela La Sabana', 0),
    ('Sofía', 'Jiménez', 'González', '2011-05-18', 'DI', '230459876', 'Escuela Central', 1),
    ('Felipe', 'Alvarado', 'Martínez', '2010-11-30', 'PA', 'P89012345', 'Escuela Monte Verde', 0),
    ('Lucía', 'Vargas', 'Rojas', '2009-07-25', 'CC', '190231475', 'Escuela El Carmen', 0),
    ('Javier', 'Fernández', 'Solís', '2008-09-16', 'DI', '260478951', 'Escuela Nueva Esperanza', 1);

-- Insert values into tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents`
(`student_id`, `parentguardian_id`)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Insert values into tbl_enrollments
INSERT INTO `tbl_enrollments`
(`status`, `enrollment_date`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`, `student_id`)
VALUES
    ('P', '2024-01-15 10:00:00', '6', 'SM', '2024-01-10', 1, 1, 1),
    ('E', '2024-01-16 11:30:00', '5', 'FM', '2024-01-11', 1, 0, 2),
    ('A', '2024-01-17 09:45:00', '7', 'OH', '2024-01-12', 1, 1, 3),
    ('R', '2024-01-18 08:15:00', '8', 'FD', '2024-01-13', 0, 1, 4),
    ('I', '2024-01-19 12:00:00', '9', 'OT', '2024-01-14', 1, 0, 5);

-- Insert values into tbl_documents
INSERT INTO `tbl_documents`
(`document_name`, `document_type`, `document_url`, `enrollment_id`)
VALUES
    ('Health Certificate Andrés', 'HC', '/home/user/docs/andres_health.pdf', 1),
    ('Other Document Sofía', 'OT', '/home/user/docs/sofia_other.pdf', 2),
    ('Health Certificate Felipe', 'HC', '/home/user/docs/felipe_health.pdf', 3),
    ('Health Certificate Lucía', 'HC', '/home/user/docs/lucia_health.pdf', 4),
    ('Other Document Javier', 'OT', '/home/user/docs/javier_other.pdf', 5);
