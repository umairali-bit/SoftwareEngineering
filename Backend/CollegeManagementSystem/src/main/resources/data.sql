-- Clean up existing data first (important for dev restarts)
DELETE FROM student;
DELETE FROM subject;
DELETE FROM professor;
DELETE FROM admission_record;

-- Reset sequences so IDs start from 1 again
ALTER SEQUENCE professor_id_seq RESTART WITH 1;
ALTER SEQUENCE admission_record_id_seq RESTART WITH 1;
ALTER SEQUENCE student_id_seq RESTART WITH 1;
ALTER SEQUENCE subject_id_seq RESTART WITH 1;

-- Insert Professors (explicit IDs)
INSERT INTO professor (id, name)
VALUES
  (1, 'Dr. Alan Turing'),
  (2, 'Dr. Marie Curie'),
  (3, 'Dr. Isaac Newton');

-- Insert Admission Records (explicit IDs)
INSERT INTO admission_record (id, fees, admission_date)
VALUES
  (1, 5000, '2025-01-10 00:00:00'),
  (2, 5500, '2025-02-15 00:00:00'),
  (3, 5300, '2025-03-12 00:00:00');

-- Insert Students linked to Admission Records
INSERT INTO student (id, name, admission_record_id)
VALUES
  (1, 'Alice Johnson', 1),
  (2, 'Brian Smith',   2),
  (3, 'Chloe Davis',   3);

-- Insert Subjects linked to Professors
INSERT INTO subject (id, name, professor_id)
VALUES
  (1, 'Computer Science', 1),
  (2, 'Physics', 2),
  (3, 'Mathematics', 3),
  (4, 'Quantum Mechanics', 2),
  (5, 'Artificial Intelligence', 1);


  -- Sync sequences with the highest ID in each table
  SELECT setval('professor_id_seq',        COALESCE((SELECT MAX(id) FROM professor), 0) + 1, false);
  SELECT setval('admission_record_id_seq', COALESCE((SELECT MAX(id) FROM admission_record), 0) + 1, false);
  SELECT setval('student_id_seq',          COALESCE((SELECT MAX(id) FROM student), 0) + 1, false);
  SELECT setval('subject_id_seq',          COALESCE((SELECT MAX(id) FROM subject), 0) + 1, false);

