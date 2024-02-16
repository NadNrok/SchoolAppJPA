package com.fm.school.dao;

import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Student> getAllStudentsByCourseName(String courseName) {
		String sql = "SELECT s.* FROM students s " + "INNER JOIN student_courses sc ON s.student_id = sc.student_id "
				+ "INNER JOIN courses c ON sc.course_id = c.course_id " + "WHERE c.course_name = ?";
		return jdbcTemplate.query(sql, new Object[] { courseName }, new BeanPropertyRowMapper<>(Student.class));
	}

	@Override
	public void deleteStudentById(int studentId) {
		List<StudentCourse> studentCourses = getStudentCoursesByStudentId(studentId);
		if (!studentCourses.isEmpty()) {
			for (StudentCourse studentCourse : studentCourses) {
				deleteStudentCourse(studentCourse.getStudentId(), studentCourse.getCourseId());
			}
		}

		jdbcTemplate.update("DELETE FROM students WHERE student_id = ?", studentId);
	}
	@SuppressWarnings("deprecation")
	@Override
	public List<StudentCourse> getStudentCoursesByStudentId(int studentId) {
	    return jdbcTemplate.query("SELECT * FROM student_courses WHERE student_id = ?", 
	        new Object[] { studentId }, 
	        new BeanPropertyRowMapper<>(StudentCourse.class));
	}

	@Override
	public void deleteStudentCourse(int studentId, int courseId) {
	    jdbcTemplate.update("DELETE FROM student_courses WHERE student_id = ? AND course_id = ?", 
	        studentId, courseId);
	}

	@Override
	public List<Student> getAllStudents() {
		return jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<>(Student.class));
	}

	@SuppressWarnings("deprecation")
	@Override
	public Student getStudentById(int studentId) {
		return jdbcTemplate.queryForObject("SELECT * FROM students WHERE student_id = ?", new Object[] { studentId },
				new BeanPropertyRowMapper<>(Student.class));
	}

	@Override
	public void addStudent(Student student) {
		jdbcTemplate.update("INSERT INTO students (group_id, first_name, last_name) VALUES (?, ?, ?)",
				student.getGroupId(), student.getFirstName(), student.getLastName());
	}

	@Override
	public void updateStudent(Student student) {
		jdbcTemplate.update("UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?",
				student.getGroupId(), student.getFirstName(), student.getLastName(), student.getGroupId());
	}

	@Override
	public void deleteStudent(int studentId) {
		jdbcTemplate.update("DELETE FROM students WHERE student_id = ?", studentId);
	}
}
