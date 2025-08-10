-- Insert Professors
INSERT INTO professor (name)
VALUES
  ('Dr. Alan Turing'),
  ('Dr. Marie Curie'),
  ('Dr. Isaac Newton');

-- Insert Students
INSERT INTO student (name, professor_removed, admission_record_id) VALUES
('Alice Johnson', false, NULL),
('Brian Smith',  false, NULL),
('Chloe Davis',  false, NULL);

-- Insert Subjects
INSERT INTO subject (name, professor_id, professor_removed) VALUES
  ('Computer Science', 1, false),
  ('Physics', 2, false),
  ('Mathematics', 3, false),
  ('Quantum Mechanics', 2, false),
  ('Artificial Intelligence', 1, false);









--INSERT INTO admission_record (id, fees, student_id) VALUES
--(1, 5000, 1),
--(2, 5500, 2),
--(3, 5300, 3),
--(4, 5200, 4),
--(5, 5100, 5),
--(6, 5800, 6);
--
--
