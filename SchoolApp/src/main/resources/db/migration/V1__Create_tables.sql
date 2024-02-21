-- db/migration/V1__Сreate_tables.sql

--DROP TABLE IF EXISTS student_courses, students, courses, groups;

CREATE TABLE IF NOT EXISTS groups (
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    student_id SERIAL PRIMARY KEY,
    group_id INT REFERENCES groups(group_id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student_courses (
	id SERIAL PRIMARY KEY,
    student_id INT REFERENCES students(student_id) NOT NULL,
    course_id INT REFERENCES courses(course_id) NOT NULL
);
