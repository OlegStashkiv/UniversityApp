INSERT INTO departments (id, name, head_of_department_id)
VALUES (4, "History", 4);

INSERT INTO lectors (id, first_name, last_name, salary, degree)
VALUES
    (4, "Anna", "Wing", 2500, "PROFESSOR"),
    (10, "David", "Davies", 900, "ASSISTANT");

INSERT INTO departments_lectors(department_id, lector_id)
VALUES
    (4, 4),
    (4, 10)

