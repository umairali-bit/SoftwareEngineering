-- Insert Professors
INSERT INTO professor (name)
VALUES
  ('Dr. Alan Turing'),
  ('Dr. Marie Curie'),
  ('Dr. Isaac Newton');

-- Insert Admission Records first
INSERT INTO admission_record (id, fees, admission_date)
VALUES
  (1, 5000, '2025-01-10 00:00:00'),
  (2, 5500, '2025-02-15 00:00:00'),
  (3, 5300, '2025-03-12 00:00:00');

-- Insert Students linked to Admission Records
INSERT INTO student (name, admission_record_id)
VALUES
  ('Alice Johnson', 1),
  ('Brian Smith',   2),
  ('Chloe Davis',   3);

-- Insert Subjects (professor_id must exist in professor table)
INSERT INTO subject (name, professor_id)
VALUES
  ('Computer Science', 1),
  ('Physics', 2),
  ('Mathematics', 3),
  ('Quantum Mechanics', 2),
  ('Artificial Intelligence', 1);

