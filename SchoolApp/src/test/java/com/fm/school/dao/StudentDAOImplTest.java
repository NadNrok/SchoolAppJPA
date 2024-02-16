package com.fm.school.dao;

import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentDAOImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private StudentDAO studentDAO;

	@Test
	@Sql(statements = {
			"INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'John', 'Doe')",
			"INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (2, 1, 'Jane', 'Smith')" })
	public void testGetAllStudents() {
		List<Student> students = studentDAO.getAllStudents();
		assertThat(students).isNotNull();
		assertThat(students).hasSize(2);
		assertThat(students.get(0).getFirstName()).isEqualTo("John");
		assertThat(students.get(1).getFirstName()).isEqualTo("Jane");
	}

	@Test
	@Sql(statements = "INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'John', 'Doe')")
	public void testGetStudentById() {
		Student student = studentDAO.getStudentById(1);
		assertThat(student).isNotNull();
		assertThat(student.getStudentId()).isEqualTo(1);
		assertThat(student.getFirstName()).isEqualTo("John");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddStudent() {
		Student newStudent = new Student();
		newStudent.setGroupId(1);
		newStudent.setFirstName("Alice");
		newStudent.setLastName("Johnson");
		studentDAO.addStudent(newStudent);

		Student retrievedStudent = jdbcTemplate.queryForObject("SELECT * FROM students WHERE first_name = ?",
				new Object[] { "Alice" }, (rs, rowNum) -> {
					Student s = new Student();
					s.setStudentId(rs.getInt("student_id"));
					s.setGroupId(rs.getInt("group_id"));
					s.setFirstName(rs.getString("first_name"));
					s.setLastName(rs.getString("last_name"));
					return s;
				});

		assertThat(retrievedStudent).isNotNull();
		assertThat(retrievedStudent.getFirstName()).isEqualTo("Alice");
	}

	@Test
	@Sql(statements = {
			"INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'John', 'Doe')",
			"INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)" })
	public void testDeleteStudentById() {
		studentDAO.deleteStudentById(1);

		List<Student> students = studentDAO.getAllStudents();
		assertThat(students).isNotNull();
		assertThat(students).hasSize(0);

		List<StudentCourse> studentCourses = studentDAO.getStudentCoursesByStudentId(1);
		assertThat(studentCourses).isEmpty();
	}

	@Test
	@Sql(statements = {
			"INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'John', 'Doe')",
			"INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)" })
	public void testDeleteStudentCourse() {
		studentDAO.deleteStudentCourse(1, 1);

		List<StudentCourse> studentCourses = studentDAO.getStudentCoursesByStudentId(1);
		assertThat(studentCourses).isEmpty();
	}

	@Test
	@Sql(statements = {
			"INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (1, 1, 'John', 'Doe')",
			"INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)" })
	public void testGetAllStudentsByCourseName() {
		List<Student> students = studentDAO.getAllStudentsByCourseName("Math");
		assertThat(students).isNotNull();
		assertThat(students).hasSize(1);
		assertThat(students.get(0).getFirstName()).isEqualTo("John");
	}
}
