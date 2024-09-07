INSERT INTO `tbl_parentsguardians` (`first_name`, `first_surname`, `second_surname`, `id_type`, `id_number`, `phone_number`, `email`, `home_address`, `relationship`)
VALUES
('John', 'Doe', 'Smith', 'CC', '123456789', '3001234567', 'john.doe@example.com', '123 Main St', 'F'),
('Jane', 'Doe', 'Johnson', 'DI', '987654321', '3009876543', 'jane.doe@example.com', '456 Oak St', 'M'),
('Robert', 'Smith', 'Doe', 'PA', '192837465', '3001928374', 'robert.smith@example.com', '789 Pine St', 'G'),
('Maria', 'Johnson', 'Martinez', 'CC', '564738291', '3005647382', 'maria.johnson@example.com', '321 Maple St', 'M'),
('Carlos', 'Martinez', 'Lopez', 'DI', '102938475', '3001029384', 'carlos.martinez@example.com', '654 Cedar St', 'F');

-- Insert values into tbl_students
INSERT INTO `tbl_students` (`first_name`, `first_surname`, `second_surname`, `birth_date`, `id_type`, `id_number`, `previous_school`, `has_accommodations`)
VALUES
('Michael', 'Doe', 'Smith', '2010-05-10', 'CC', '321654987', 'Springfield High', 0),
('Emily', 'Doe', 'Johnson', '2011-07-12', 'DI', '456789123', 'Greenfield School', 1),
('William', 'Smith', 'Doe', '2009-09-15', 'PA', '789456123', 'Hill Valley School', 0),
('Sophia', 'Johnson', 'Martinez', '2012-02-20', 'CC', '654321789', 'Rivertown Elementary', 0),
('David', 'Martinez', 'Lopez', '2010-11-05', 'DI', '987123654', 'Bayside Middle School', 1);

-- Insert values into tbl_parentguardianstudents
INSERT INTO `tbl_parentguardianstudents` (`student_id`, `parentguardian_id`)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insert values into tbl_enrollments
INSERT INTO `tbl_enrollments` (`student_id`, `status`, `grade_to_enroll`, `known_through`, `exam_date`, `consent_given`, `whatsapp_notification`)
VALUES
(1, 'P', '5', 'SM', '2024-06-15', 1, 1),
(2, 'E', '4', 'OH', '2024-06-16', 1, 0),
(3, 'I', '3', 'FD', '2024-06-17', 0, 1),
(4, 'A', '2', 'FM', '2024-06-18', 1, 0),
(5, 'R', '1', 'OT', '2024-06-19', 0, 1);