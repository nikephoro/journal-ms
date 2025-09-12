-- src/main/resources/data.sql
-- Make sure the schema exists (harmless if already created)
CREATE SCHEMA IF NOT EXISTS journal_database;

INSERT INTO journal_database.users (first_name, last_name, email, password, username, role)
VALUES ('Alice', 'Admin', 'alice@example.com', '{noop}password', 'stalin', 'ADMIN');

INSERT INTO journal_database.users (first_name, last_name, email, password, username, role)
VALUES ('Bob', 'User', 'bob@example.com', '{noop}password', 'giovanni', 'USER');

-- Insert Journal Entries (χρησιμοποιώντας τα user IDs από πάνω)
INSERT INTO journal_database.journal_entries (content, created_at, user_id)
VALUES
('Today was a great day! I learned about Spring Boot JPA relationships and finally understood how OneToMany and ManyToOne work together. The key is the mappedBy attribute and proper JoinColumn setup.', '2025-09-12 10:30:00', 1),

('Working on my journal application. Still struggling with Hibernate mappings but making progress. The error messages are becoming more helpful as I understand the framework better.', '2025-09-11 15:45:00', 1),

('Had an amazing weekend trip to the mountains. The fresh air and beautiful scenery really helped clear my mind. Sometimes stepping away from coding is exactly what you need to solve problems.', '2025-09-10 09:15:00', 2),

('First entry in my new digital journal! Excited to track my thoughts and progress as I learn new technologies. Spring Boot seems powerful but has a steep learning curve.', '2025-09-09 20:00:00', 2)


