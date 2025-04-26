USE `db_cit`;

-- Insert values into tbl_persons
INSERT INTO `tbl_persons`
(`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`)
VALUES ('Carlos', 'Rodríguez', 'Morales', 'CC', '900321654'),
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
VALUES (1, '876543210', 'carlos.rod@example.com', 'F'),
       (2, '856742013', 'maria.jimenez@example.com', 'M'),
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
VALUES ('Costa Rica', 'San José', 'San José', 'Carmen', 'Avenida Central 100', 1),
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
    (`student_id`, `birth_date`, `previous_school`, `previous_grades`, `has_accommodations`)
VALUES (11, '2010-03-12', 'Escuela La Sabana',88.75, 0),
       (12, '2011-05-18', 'Escuela Central',88.75, 1),
       (13, '2010-11-30', 'Escuela Monte Verde',88.75, 0),
       (14, '2009-07-25', 'Escuela El Carmen',88.75, 0),
       (15, '2008-09-16', 'Escuela Nueva Esperanza',88.75, 1),
       (16, '2012-04-21', 'Escuela La Paz',88.75, 1),
       (17, '2013-06-10', 'Escuela Nueva Vida',88.75, 0),
       (18, '2011-09-30', 'Escuela San Isidro',88.75, 0),
       (19, '2009-05-05', 'Escuela Santa Marta',88.75, 1),
       (20, '2008-10-14', 'Escuela San Lucas',88.75, 0);

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
VALUES ('PENDING', 'TENTH', 'SM', '2024-01-10', 1, 1, 11),
       ('ELIGIBLE', 'FIFTH', 'FM', '2024-01-11', 1, 0, 11),
       ('ACCEPTED', 'SEVENTH', 'OH', '2024-01-12', 1, 1, 13),
       ('REJECTED', 'EIGHTH', 'FD', '2024-01-13', 0, 1, 14),
       ('INELIGIBLE', 'NINTH', 'OT', '2024-01-14', 1, 0, 15),
       ('PENDING', 'FOURTH', 'SM', '2024-01-28', 1, 0, 16),
       ('ELIGIBLE', 'THIRD', 'FM', '2024-01-29', 0, 1, 17),
       ('ACCEPTED', 'FIFTH', 'OH', '2024-01-30', 1, 0, 18),
       ('REJECTED', 'SIXTH', 'FD', '2024-01-31', 1, 1, 19),
       ('ELIGIBLE', 'SEVENTH', 'OT', '2024-02-01', 0, 1, 20);

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
VALUES ('2025-01-01', '2025-03-15'),
       ('2025-04-01', '2025-06-15'),
       ('2025-07-01', '2025-09-15');

-- Insert value into tbl_examdays
INSERT INTO `tbl_exam_days` (exam_period_id, exam_day, start_time)
VALUES (1, 'M', '08:00:00'),
       (1, 'K', '08:00:00'),
       (1, 'F', '08:00:00'),
       (2, 'M', '08:00:00'),
       (2, 'K', '08:00:00'),
       (2, 'F', '08:00:00'),
       (3, 'M', '08:00:00'),
       (3, 'K', '08:00:00'),
       (3, 'F', '08:00:00');
-- Lunes del primer periodo

-- Insert value into tbl_questions
INSERT INTO `tbl_questions` (`question_type`, `question_text`, `image_url`, `question_grade`, `question_level`,
                             `selection_type`)
VALUES ('DAI', '¿Cómo te sientes el día de hoy?', NULL, 'SECOND', 'EASY', 'PARAGRAPH'),
       ('ACA', '¿Como se calcula el area de un circulo?', NULL, 'FIRST', 'EASY', 'SINGLE'),
       ('DAI', '¿Qué tipo de música prefieres?', NULL, 'THIRD', 'MEDIUM', 'PARAGRAPH'),
       ('ACA', '¿Qué estación del año sigue despues del verano?', NULL, 'FOURTH', 'HARD', 'SINGLE'),
       ('DAI', '¿Te gusta practicar deportes?', NULL, 'FIFTH', 'EASY', 'PARAGRAPH'),
       ('ACA', '¿Cual es la temperatura de ebullición del agua?', NULL, 'SIXTH', 'MEDIUM', 'SINGLE'),
       ('DAI', '¿Prefieres la playa o la montaña?', NULL, 'SEVENTH', 'HARD', 'PARAGRAPH'),
       ('ACA', '¿Cual es la formula de la teoría de la relatividad?', NULL, 'EIGHTH', 'EASY', 'SINGLE'),
       ('DAI', '¿Cuál es tu película favorita?', NULL, 'NINTH', 'MEDIUM', 'PARAGRAPH'),
       ('ACA',
        'Dos descuentos sucesivos de 10% y 20% aplicados a un cierto artículo, equivalen a un único descuento de', NULL,
        'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Juan y Pedro se reparten un pastel. Pedro se quedó con 1/3 y Juan con el resto. Para ser más equitativos, Juan cortó la cuarta parte de su porción y se la dio a Pedro. Entonces la cantidad de pastel que tiene Juan es',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Un frasco contiene cincuenta monedas de 100 colones y pesa 1400g, si el frasco vacío pesa 250g entones el peso, en gramos, de una moneda es',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA', 'Si ayer cumplí 20 años y el próximo año cumpliré 22 años ¿Cuál es la fecha de mi cumpleaños?', NULL,
        'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Luisa gastó 2400 colones de sus ahorros en una entrada al cine, si esta cantidad representa las 4/7 partes de sus ahorros, determine cuánto dinero tenía ahorrado Luisa.',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Cinco extranjeros están colocados en fila. El mexicano está después del italiano. El argentino está antes del mexicano y justo después del jamaiquino. El jamaiquino no es el primero de la fila y está antes del italiano. Entonces, el costarricense está antes del',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Una cocina eléctrica, con dos discos encendidos consume 1200 colones al estar encendidos por 3 horas. El gasto, en colones, si se encienden los 4 discos por dos horas corresponde a',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'A realizar el examen de admisión de CTP CIT llegaron 600 estudiantes, los cuales corresponden al 75% de los estudiantes que se esperaban. De los estudiantes que hicieron la prueba, 30% corresponden a lugares fuera de Heredia. Si se esperaban 250 estudiantes que viven en lugares fuera de Heredia, el porcentaje de estudiantes que no viven en Heredia y llegaron a hacer la prueba es de',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Hoy es domingo y Karla inicia la lectura de un libro de 290 páginas. Ella lee sólo 4 páginas cada día, excepto los domingos que lee 25 páginas. La cantidad de días que le tomará a Karla leer el libro por completo es de',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'En un terreno de forma triangular, dos ángulos miden 80° y 64° respectivamente. Si la medida del lado común entre ellos mide 38 m, ¿Cuál es la medida aproximada en metros del lado menor  del terreno?',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'En una pirámide triangular cuya base es una triángulo equilátero de área, que se detalla luego, el lado de la base corresponde con',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'En un prisma cuadrangular cuyo lado de la base es 13 cm y la altura del prisma es 16 cm, el área lateral corresponde con',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'En un prisma cuadrangular cuyo lado mide  21 cm  y la altura del prisma el doble que el lado, el área lateral corresponde con',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'La representación algebraica de la expresión “ el cuadrado de un número aumentado en seis” corresponde con',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SIDA : SANGRE',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. MONÁRQUICO : DESORDENADO',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SUMAR : MULTIPLICAR',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA',
        'Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. HISTORIA : SOCIEDAD',
        NULL, 'TENTH', 'HARD', 'SINGLE'),
       ('ACA', 'Se puede argumentar que la televisión es, principalmente, beneficiosa porque', NULL, 'TENTH', 'HARD',
        'SINGLE'),
       ('ACA', 'Quienes hayan leído grandes obras literarias, haciendo de ellas parte de su vida,', NULL, 'TENTH',
        'HARD', 'SINGLE')
;

-- Insert value into tbl_question_options
INSERT INTO `tbl_question_options` (`question_id`, `option`, `is_correct`)
VALUES
-- Para la pregunta 1 (¿Como se calcula el área de un círculo?)
(2, 'π * radio^2', 1),           -- Correcta
(2, '2 * radio', 0),
(2, 'π * diámetro', 0),
(2, 'radio * altura', 0),

-- Para la pregunta 2 (¿Qué estación del año sigue después del verano?)
(4, 'Invierno', 0),
(4, 'Otoño', 1),                 -- Correcta
(4, 'Primavera', 0),
(4, 'Verano', 0),

-- Para la pregunta 3 (¿Cual es la temperatura de ebullición del agua?)
(6, '100°C', 1),                 -- Correcta
(6, '90°C', 0),
(6, '150°C', 0),
(6, '50°C', 0),

-- Para la pregunta 4 (¿Cuál es la fórmula de la teoría de la relatividad?)
(8, 'E = mc^2', 1),              -- Correcta
(8, 'F = ma', 0),
(8, 'P = mv', 0),
(8, 'a^2 + b^2 = c^2', 0),

-- Para la pregunta 11 (Dos descuentos sucesivos de 10% y 20% aplicados a un cierto artículo, equivalen a un único descuento de)
(10, '15%', 0),
(10, '28%', 1),                  -- Correcta
(10, '30%', 0),
(10, '32%', 0),

-- Para la pregunta 12 (Juan y Pedro se reparten un pastel. Pedro se quedó con 1/3 y Juan con el resto. Para ser más equitativos, Juan cortó la cuarta parte de su porción y se la dio a Pedro. Entonces la cantidad de pastel que tiene Juan es)
(11, '1/4', 0),
(11, '1/3', 0),
(11, '1/2', 1),                  -- Correcta
(11, '2/3', 0),

-- Para la pregunta 13 (Un frasco contiene cincuenta monedas de 100 colones y pesa 1400g, si el frasco vacío pesa 250g entonces el peso, en gramos, de una moneda es)
(12, '20g', 0),
(12, '25g', 0),
(12, '23g', 1),                  -- Correcta
(12, '35g', 0),

-- Para la pregunta 14 (Si ayer cumplí 20 años y el próximo año cumpliré 22 años ¿Cuál es la fecha de mi cumpleaños?)
(13, '31 de diciembre', 1),      -- Correcta
(13, '1 de enero', 0),
(13, '31 de enero', 0),
(13, '1 de febrero', 0),

-- Para la pregunta 15 (Luisa gastó 2400 colones de sus ahorros en una entrada al cine, si esta cantidad representa las 4/7 partes de sus ahorros, determine cuánto dinero tenía ahorrado Luisa.)
(14, '4200 colones', 0),
(14, '2800 colones', 0),
(14, '5600 colones', 1),         -- Correcta
(14, '7000 colones', 0),

-- Para la pregunta 16 (Cinco extranjeros están colocados en fila. El mexicano está después del italiano. El argentino está antes del mexicano y justo después del jamaiquino. El jamaiquino no es el primero de la fila y está antes del italiano. Entonces, el costarricense está antes del)
(15, 'Mexicano', 0),
(15, 'Argentino', 0),
(15, 'Jamaiquino', 0),
(15, 'Italiano', 1),             -- Correcta

-- Para la pregunta 17 (Una cocina eléctrica, con dos discos encendidos consume 1200 colones al estar encendidos por 3 horas. El gasto, en colones, si se encienden los 4 discos por dos horas corresponde a)
(16, '1200 colones', 0),
(16, '1600 colones', 1),         -- Correcta
(16, '2400 colones', 0),
(16, '3200 colones', 0),

-- Para la pregunta 18 (A realizar el examen de admisión de CTP CIT llegaron 600 estudiantes, los cuales corresponden al 75% de los estudiantes que se esperaban. De los estudiantes que hicieron la prueba, 30% corresponden a lugares fuera de Heredia. Si se esperaban 250 estudiantes que viven en lugares fuera de Heredia, el porcentaje de estudiantes que no viven en Heredia y llegaron a hacer la prueba es de)
(17, '20%', 0),
(17, '25%', 0),
(17, '30%', 0),
(17, '40%', 1),                  -- Correcta

-- Para la pregunta 19 (Hoy es domingo y Karla inicia la lectura de un libro de 290 páginas. Ella lee sólo 4 páginas cada día, excepto los domingos que lee 25 páginas. La cantidad de días que le tomará a Karla leer el libro por completo es de)
(18, '36 días', 0),
(18, '40 días', 0),
(18, '41 días', 1),              -- Correcta
(18, '46 días', 0),

-- Para la pregunta 20 (En un terreno de forma triangular, dos ángulos miden 80° y 64° respectivamente. Si la medida del lado común entre ellos mide 38 m, ¿Cuál es la medida aproximada en metros del lado menor  del terreno?)
(19, '25 m', 0),
(19, '30 m', 0),
(19, '35 m', 1),                 -- Correcta
(19, '40 m', 0),

-- Para la pregunta 21 (En una pirámide triangular cuya base es un triángulo equilátero de área, que se detalla luego, el lado de la base corresponde con)
(20, '1/2', 0),
(20, '1/3', 0),
(20, '1/4', 1),                  -- Correcta
(20, '1/5', 0),

-- Para la pregunta 22 (En un prisma cuadrangular cuyo lado de la base es 13 cm y la altura del prisma es 16 cm, el área lateral corresponde con)
(21, '208 cm^2', 0),
(21, '312 cm^2', 0),
(21, '416 cm^2', 1),             -- Correcta
(21, '520 cm^2', 0),

-- Para la pregunta 23 (En un prisma cuadrangular cuyo lado mide 21 cm y la altura del prisma el doble que el lado, el área lateral corresponde con)
(22, '252 cm^2', 0),
(22, '294 cm^2', 0),
(22, '336 cm^2', 1),             -- Correcta
(22, '378 cm^2', 0),

-- Para la pregunta 24 (La representación algebraica de la expresión “el cuadrado de un número aumentado en seis” corresponde con)
(23, 'x^2 + 6', 0),
(23, 'x^2 + 6x', 0),
(23, 'x^2 + 12x + 36', 1),       -- Correcta
(23, 'x^2 + 12', 0),

-- Para la pregunta 25 (Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SIDA : SANGRE)
(24, 'Cerebro : Cabeza', 0),
(24, 'Rabia : Saliva', 1),       -- Correcta
(24, 'Pulmón : Respiración', 0),
(24, 'Estómago : Digestión', 0),

-- Para la pregunta 26 (Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. MONÁRQUICO : DESORDENADO)
(25, 'Dictador : Violento', 0),
(25, 'Anarquía : Orden', 0),
(25, 'Democrático : Justo', 0),
(25, 'Anarquía : Desorden', 1),  -- Correcta

-- Para la pregunta 27 (Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SUMAR : MULTIPLICAR)
(26, 'Restar : Dividir', 1),     -- Correcta
(26, 'Sumar : Restar', 0),
(26, 'Multiplicar : Dividir', 0),
(26, 'Sumar : Multiplicar', 0),

-- Para la pregunta 28 (Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. HISTORIA : SOCIEDAD)
(27, 'Geografía : Tierra', 0),
(27, 'Biología : Cuerpo', 0),
(27, 'Matemáticas : Números', 0),
(27, 'Literatura : Cultura', 1), -- Correcta

-- Para la pregunta 29 (Se puede argumentar que la televisión es, principalmente, beneficiosa porque)
(28, 'Es un medio de comunicación', 0),
(28, 'Es un medio de entretenimiento', 0),
(28, 'Es un medio de información', 0),
(28, 'Todas las anteriores', 1), -- Correcta

-- Para la pregunta 30 (Quienes hayan leído grandes obras literarias, haciendo de ellas parte de su vida,)
(29, 'Son personas cultas', 0),
(29, 'Son personas sabias', 0),
(29, 'Son personas educadas', 0),
(29, 'Son personas que han enriquecido su vida', 1) -- Correcta
;

INSERT INTO `tbl_exams` (`exam_id`, `enrollment_id`, `exam_date`, `exam_type`, `responses`)
VALUES (1, 10, '2025-03-25 17:54:12', 'ACA', '{
  "exam": "[{\\"id\\":26,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SUMAR : MULTIPLICAR\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":81,\\"isCorrect\\":true,\\"option\\":\\"Restar : Dividir\\",\\"selected\\":true},{\\"id\\":82,\\"isCorrect\\":false,\\"option\\":\\"Sumar : Restar\\",\\"selected\\":false},{\\"id\\":83,\\"isCorrect\\":false,\\"option\\":\\"Multiplicar : Dividir\\",\\"selected\\":false},{\\"id\\":84,\\"isCorrect\\":false,\\"option\\":\\"Sumar : Multiplicar\\",\\"selected\\":false}]},{\\"id\\":29,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Quienes hayan leído grandes obras literarias, haciendo de ellas parte de su vida,\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":93,\\"isCorrect\\":false,\\"option\\":\\"Son personas cultas\\",\\"selected\\":false},{\\"id\\":94,\\"isCorrect\\":false,\\"option\\":\\"Son personas sabias\\",\\"selected\\":false},{\\"id\\":95,\\"isCorrect\\":false,\\"option\\":\\"Son personas educadas\\",\\"selected\\":false},{\\"id\\":96,\\"isCorrect\\":true,\\"option\\":\\"Son personas que han enriquecido su vida\\",\\"selected\\":true}]},{\\"id\\":12,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Un frasco contiene cincuenta monedas de 100 colones y pesa 1400g, si el frasco vacío pesa 250g entones el peso, en gramos, de una moneda es\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":25,\\"isCorrect\\":false,\\"option\\":\\"20g\\",\\"selected\\":false},{\\"id\\":26,\\"isCorrect\\":false,\\"option\\":\\"25g\\",\\"selected\\":false},{\\"id\\":27,\\"isCorrect\\":true,\\"option\\":\\"23g\\",\\"selected\\":true},{\\"id\\":28,\\"isCorrect\\":false,\\"option\\":\\"35g\\",\\"selected\\":false}]},{\\"id\\":25,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. MONÁRQUICO : DESORDENADO\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":77,\\"isCorrect\\":false,\\"option\\":\\"Dictador : Violento\\",\\"selected\\":false},{\\"id\\":78,\\"isCorrect\\":false,\\"option\\":\\"Anarquía : Orden\\",\\"selected\\":false},{\\"id\\":79,\\"isCorrect\\":false,\\"option\\":\\"Democrático : Justo\\",\\"selected\\":false},{\\"id\\":80,\\"isCorrect\\":true,\\"option\\":\\"Anarquía : Desorden\\",\\"selected\\":true}]},{\\"id\\":16,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Una cocina eléctrica, con dos discos encendidos consume 1200 colones al estar encendidos por 3 horas. El gasto, en colones, si se encienden los 4 discos por dos horas corresponde a\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":41,\\"isCorrect\\":false,\\"option\\":\\"1200 colones\\",\\"selected\\":false},{\\"id\\":42,\\"isCorrect\\":true,\\"option\\":\\"1600 colones\\",\\"selected\\":true},{\\"id\\":43,\\"isCorrect\\":false,\\"option\\":\\"2400 colones\\",\\"selected\\":false},{\\"id\\":44,\\"isCorrect\\":false,\\"option\\":\\"3200 colones\\",\\"selected\\":false}]},{\\"id\\":15,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Cinco extranjeros están colocados en fila. El mexicano está después del italiano. El argentino está antes del mexicano y justo después del jamaiquino. El jamaiquino no es el primero de la fila y está antes del italiano. Entonces, el costarricense está antes del\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":37,\\"isCorrect\\":false,\\"option\\":\\"Mexicano\\",\\"selected\\":false},{\\"id\\":38,\\"isCorrect\\":false,\\"option\\":\\"Argentino\\",\\"selected\\":false},{\\"id\\":39,\\"isCorrect\\":false,\\"option\\":\\"Jamaiquino\\",\\"selected\\":false},{\\"id\\":40,\\"isCorrect\\":true,\\"option\\":\\"Italiano\\",\\"selected\\":true}]},{\\"id\\":17,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"A realizar el examen de admisión de CTP CIT llegaron 600 estudiantes, los cuales corresponden al 75% de los estudiantes que se esperaban. De los estudiantes que hicieron la prueba, 30% corresponden a lugares fuera de Heredia. Si se esperaban 250 estudiantes que viven en lugares fuera de Heredia, el porcentaje de estudiantes que no viven en Heredia y llegaron a hacer la prueba es de\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":45,\\"isCorrect\\":false,\\"option\\":\\"20%\\",\\"selected\\":false},{\\"id\\":46,\\"isCorrect\\":false,\\"option\\":\\"25%\\",\\"selected\\":false},{\\"id\\":47,\\"isCorrect\\":false,\\"option\\":\\"30%\\",\\"selected\\":false},{\\"id\\":48,\\"isCorrect\\":true,\\"option\\":\\"40%\\",\\"selected\\":true}]},{\\"id\\":10,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Dos descuentos sucesivos de 10% y 20% aplicados a un cierto artículo, equivalen a un único descuento de\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":17,\\"isCorrect\\":false,\\"option\\":\\"15%\\",\\"selected\\":false},{\\"id\\":18,\\"isCorrect\\":true,\\"option\\":\\"28%\\",\\"selected\\":true},{\\"id\\":19,\\"isCorrect\\":false,\\"option\\":\\"30%\\",\\"selected\\":false},{\\"id\\":20,\\"isCorrect\\":false,\\"option\\":\\"32%\\",\\"selected\\":false}]},{\\"id\\":27,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. HISTORIA : SOCIEDAD\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":85,\\"isCorrect\\":false,\\"option\\":\\"Geografía : Tierra\\",\\"selected\\":false},{\\"id\\":86,\\"isCorrect\\":false,\\"option\\":\\"Biología : Cuerpo\\",\\"selected\\":false},{\\"id\\":87,\\"isCorrect\\":false,\\"option\\":\\"Matemáticas : Números\\",\\"selected\\":false},{\\"id\\":88,\\"isCorrect\\":true,\\"option\\":\\"Literatura : Cultura\\",\\"selected\\":true}]},{\\"id\\":18,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Hoy es domingo y Karla inicia la lectura de un libro de 290 páginas. Ella lee sólo 4 páginas cada día, excepto los domingos que lee 25 páginas. La cantidad de días que le tomará a Karla leer el libro por completo es de\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":49,\\"isCorrect\\":false,\\"option\\":\\"36 días\\",\\"selected\\":false},{\\"id\\":50,\\"isCorrect\\":false,\\"option\\":\\"40 días\\",\\"selected\\":false},{\\"id\\":51,\\"isCorrect\\":true,\\"option\\":\\"41 días\\",\\"selected\\":true},{\\"id\\":52,\\"isCorrect\\":false,\\"option\\":\\"46 días\\",\\"selected\\":false}]},{\\"id\\":19,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"En un terreno de forma triangular, dos ángulos miden 80° y 64° respectivamente. Si la medida del lado común entre ellos mide 38 m, ¿Cuál es la medida aproximada en metros del lado menor  del terreno?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":53,\\"isCorrect\\":false,\\"option\\":\\"25 m\\",\\"selected\\":false},{\\"id\\":54,\\"isCorrect\\":false,\\"option\\":\\"30 m\\",\\"selected\\":false},{\\"id\\":55,\\"isCorrect\\":true,\\"option\\":\\"35 m\\",\\"selected\\":true},{\\"id\\":56,\\"isCorrect\\":false,\\"option\\":\\"40 m\\",\\"selected\\":false}]},{\\"id\\":24,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Teniendo como referencia la relación del par base, elija la alternativa que mantiene dicha relación análoga. SIDA : SANGRE\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":73,\\"isCorrect\\":false,\\"option\\":\\"Cerebro : Cabeza\\",\\"selected\\":false},{\\"id\\":74,\\"isCorrect\\":true,\\"option\\":\\"Rabia : Saliva\\",\\"selected\\":true},{\\"id\\":75,\\"isCorrect\\":false,\\"option\\":\\"Pulmón : Respiración\\",\\"selected\\":false},{\\"id\\":76,\\"isCorrect\\":false,\\"option\\":\\"Estómago : Digestión\\",\\"selected\\":false}]},{\\"id\\":11,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Juan y Pedro se reparten un pastel. Pedro se quedó con 1/3 y Juan con el resto. Para ser más equitativos, Juan cortó la cuarta parte de su porción y se la dio a Pedro. Entonces la cantidad de pastel que tiene Juan es\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":21,\\"isCorrect\\":false,\\"option\\":\\"1/4\\",\\"selected\\":false},{\\"id\\":22,\\"isCorrect\\":false,\\"option\\":\\"1/3\\",\\"selected\\":false},{\\"id\\":23,\\"isCorrect\\":true,\\"option\\":\\"1/2\\",\\"selected\\":true},{\\"id\\":24,\\"isCorrect\\":false,\\"option\\":\\"2/3\\",\\"selected\\":false}]},{\\"id\\":28,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Se puede argumentar que la televisión es, principalmente, beneficiosa porque\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":89,\\"isCorrect\\":false,\\"option\\":\\"Es un medio de comunicación\\",\\"selected\\":false},{\\"id\\":90,\\"isCorrect\\":false,\\"option\\":\\"Es un medio de entretenimiento\\",\\"selected\\":false},{\\"id\\":91,\\"isCorrect\\":false,\\"option\\":\\"Es un medio de información\\",\\"selected\\":false},{\\"id\\":92,\\"isCorrect\\":true,\\"option\\":\\"Todas las anteriores\\",\\"selected\\":true}]},{\\"id\\":14,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Luisa gastó 2400 colones de sus ahorros en una entrada al cine, si esta cantidad representa las 4/7 partes de sus ahorros, determine cuánto dinero tenía ahorrado Luisa.\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":33,\\"isCorrect\\":false,\\"option\\":\\"4200 colones\\",\\"selected\\":false},{\\"id\\":34,\\"isCorrect\\":false,\\"option\\":\\"2800 colones\\",\\"selected\\":false},{\\"id\\":35,\\"isCorrect\\":true,\\"option\\":\\"5600 colones\\",\\"selected\\":true},{\\"id\\":36,\\"isCorrect\\":false,\\"option\\":\\"7000 colones\\",\\"selected\\":false}]},{\\"id\\":20,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"En una pirámide triangular cuya base es una triángulo equilátero de área, que se detalla luego, el lado de la base corresponde con\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":57,\\"isCorrect\\":false,\\"option\\":\\"1/2\\",\\"selected\\":false},{\\"id\\":58,\\"isCorrect\\":false,\\"option\\":\\"1/3\\",\\"selected\\":false},{\\"id\\":59,\\"isCorrect\\":true,\\"option\\":\\"1/4\\",\\"selected\\":true},{\\"id\\":60,\\"isCorrect\\":false,\\"option\\":\\"1/5\\",\\"selected\\":false}]},{\\"id\\":21,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"En un prisma cuadrangular cuyo lado de la base es 13 cm y la altura del prisma es 16 cm, el área lateral corresponde con\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":61,\\"isCorrect\\":false,\\"option\\":\\"208 cm^2\\",\\"selected\\":false},{\\"id\\":62,\\"isCorrect\\":false,\\"option\\":\\"312 cm^2\\",\\"selected\\":false},{\\"id\\":63,\\"isCorrect\\":true,\\"option\\":\\"416 cm^2\\",\\"selected\\":true},{\\"id\\":64,\\"isCorrect\\":false,\\"option\\":\\"520 cm^2\\",\\"selected\\":false}]},{\\"id\\":23,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"La representación algebraica de la expresión “ el cuadrado de un número aumentado en seis” corresponde con\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":69,\\"isCorrect\\":false,\\"option\\":\\"x^2 + 6\\",\\"selected\\":false},{\\"id\\":70,\\"isCorrect\\":false,\\"option\\":\\"x^2 + 6x\\",\\"selected\\":false},{\\"id\\":71,\\"isCorrect\\":true,\\"option\\":\\"x^2 + 12x + 36\\",\\"selected\\":true},{\\"id\\":72,\\"isCorrect\\":false,\\"option\\":\\"x^2 + 12\\",\\"selected\\":false}]},{\\"id\\":22,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"En un prisma cuadrangular cuyo lado mide  21 cm  y la altura del prisma el doble que el lado, el área lateral corresponde con\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":65,\\"isCorrect\\":false,\\"option\\":\\"252 cm^2\\",\\"selected\\":false},{\\"id\\":66,\\"isCorrect\\":false,\\"option\\":\\"294 cm^2\\",\\"selected\\":false},{\\"id\\":67,\\"isCorrect\\":true,\\"option\\":\\"336 cm^2\\",\\"selected\\":true},{\\"id\\":68,\\"isCorrect\\":false,\\"option\\":\\"378 cm^2\\",\\"selected\\":false}]},{\\"id\\":13,\\"questionType\\":\\"ACA\\",\\"questionText\\":\\"Si ayer cumplí 20 años y el próximo año cumpliré 22 años ¿Cuál es la fecha de mi cumpleaños?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"SINGLE\\",\\"deleted\\":false,\\"questionOptions\\":[{\\"id\\":29,\\"isCorrect\\":true,\\"option\\":\\"31 de diciembre\\",\\"selected\\":true},{\\"id\\":30,\\"isCorrect\\":false,\\"option\\":\\"1 de enero\\",\\"selected\\":false},{\\"id\\":31,\\"isCorrect\\":false,\\"option\\":\\"31 de enero\\",\\"selected\\":false},{\\"id\\":32,\\"isCorrect\\":false,\\"option\\":\\"1 de febrero\\",\\"selected\\":false}]}]"
}'),
       (2, 10, '2025-03-25 17:54:12', 'DAI', '{
         "exam": "[{\\"id\\":1,\\"questionType\\":\\"DAI\\",\\"questionText\\":\\"¿Cómo te sientes el día de hoy?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"PARAGRAPH\\",\\"deleted\\":false,\\"response\\":\\"Bien aunque un poco cansado.\\"},{\\"id\\":3,\\"questionType\\":\\"DAI\\",\\"questionText\\":\\"¿Qué tipo de música prefieres?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"PARAGRAPH\\",\\"deleted\\":false,\\"response\\":\\"El rock es mi favorito.\\"},{\\"id\\":5,\\"questionType\\":\\"DAI\\",\\"questionText\\":\\"¿Te gusta practicar deportes?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"PARAGRAPH\\",\\"deleted\\":false,\\"response\\":\\"Sí, me gusta mucho el fútbol.\\"},{\\"id\\":7,\\"questionType\\":\\"DAI\\",\\"questionText\\":\\"¿Prefieres la playa o la montaña?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"PARAGRAPH\\",\\"deleted\\":false,\\"response\\":\\"Prefiero la montaña.\\"},{\\"id\\":9,\\"questionType\\":\\"DAI\\",\\"questionText\\":\\"¿Cuál es tu película favorita?\\",\\"imageUrl\\":null,\\"selectionType\\":\\"PARAGRAPH\\",\\"deleted\\":false,\\"response\\":\\"Mi película favorita es El Padrino.\\"}]"
       }');
       
INSERT INTO `tbl_academic_exams` (`exam_id`, `grade`)
VALUES (1, 100.00); -- Insert value into tbl_academic_exams

INSERT INTO `tbl_dai_exams` (`exam_id`,`comment`,`recommendation`,`reviewed`)
VALUES (2,'Buen desempeño','ADMIT', 1); -- Insert value into tbl_dai_exams

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
       ('FACEBOOK_CONTACT', 'ComplejoEducativoCIT'),
       ('ACADEMIC_EXAM_QUESTIONS_QUANTITY', '20'),
       ('DAI_EXAM_QUESTIONS_QUANTITY', '25');

-- Password: 'campus12' test sysadmin
INSERT INTO `tbl_users` (`email`, `username`, `user_password`, `role`)
VALUES ('sysadmin@cit.co.cr', 'Sysadmin', '$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S', 'SYS'),
       ('marta@cit.co.cr', 'Marta', '$2a$10$15bZTAy6CG3OlPgl3glJxuROyEajUOTdKX9qx43Pa0JkTPR2ga2He', 'ADMIN'),
       ('jorge@cit.co.cr', 'Jorge', '$2a$10$RFPObfy6ro87gLXQalrEiuGehgDsyWfETW4h9h51eg1ZUWlpMnrIG', 'PSYCHOLOGIST'),
       ('rocio@cit.co.cr', 'Rocio', '$2a$10$VjpaCQ9OkiyJYJ28kzGm1OSjKpKE337EdtJgUrq.aACa2JCJHLU7W',
        'TEACHER'); -- Password Rocío: 'Mate8520$'

