package com.fm.school.dao;

import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentCourseDAOImpl implements StudentCourseDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public StudentCourseDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Course> getStudentEnrolledCourses(int studentId) {
		return jdbcTemplate.query(
				"SELECT c.* FROM courses c INNER JOIN student_courses sc ON c.course_id = sc.course_id WHERE sc.student_id = ?",
				new Object[] { studentId }, new BeanPropertyRowMapper<>(Course.class));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
		List<StudentCourse> studentCourses = jdbcTemplate.query(
				"SELECT * FROM student_courses WHERE student_id = ? AND course_id = ?",
				new Object[] { studentId, courseId },
				(rs, rowNum) -> new StudentCourse(rs.getInt("student_id"), rs.getInt("course_id")));

		return !studentCourses.isEmpty();
	}

	@Override
	public List<StudentCourse> getAllStudentCourses() {
		return jdbcTemplate.query("SELECT * FROM student_courses", (rs, rowNum) -> {
			StudentCourse studentCourse = new StudentCourse();
			studentCourse.setStudentId(rs.getInt("studentId"));
			studentCourse.setCourseId(rs.getInt("courseId"));
			return studentCourse;
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<StudentCourse> getStudentCoursesByStudentId(int studentId) {
		return jdbcTemplate.query("SELECT * FROM student_courses WHERE student_id = ?", new Object[] { studentId },
				(rs, rowNum) -> {
					StudentCourse studentCourse = new StudentCourse();
					studentCourse.setStudentId(rs.getInt("student_id"));
					studentCourse.setCourseId(rs.getInt("course_id"));
					return studentCourse;
				});
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<StudentCourse> getStudentCoursesByCourseId(int courseId) {
		return jdbcTemplate.query("SELECT * FROM student_courses WHERE course_id = ?", new Object[] { courseId },
				(rs, rowNum) -> {
					StudentCourse studentCourse = new StudentCourse();
					studentCourse.setStudentId(rs.getInt("student_id"));
					studentCourse.setCourseId(rs.getInt("course_id"));
					return studentCourse;
				});
	}

	@Override
	public void addStudentCourse(StudentCourse studentCourse) {
		jdbcTemplate.update("INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)",
				studentCourse.getStudentId(), studentCourse.getCourseId());
	}

	@Override
	public void deleteStudentCourse(int studentId, int courseId) {
		jdbcTemplate.update("DELETE FROM student_courses WHERE student_id = ? AND course_id = ?", studentId, courseId);
	}
}
