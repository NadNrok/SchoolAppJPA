package com.fm.school.dao;

import com.fm.school.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDAOImpl implements CourseDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Course> getAllCourses() {
		return jdbcTemplate.query("SELECT * FROM courses", new BeanPropertyRowMapper<>(Course.class));
	}

	@SuppressWarnings("deprecation")
	@Override
	public Course getCourseById(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_id = ?", new Object[] { id },
				new BeanPropertyRowMapper<>(Course.class));
	}

	@Override
	public void addCourse(Course course) {
		jdbcTemplate.update("INSERT INTO courses (course_name, course_description) VALUES (?, ?)",
				course.getCourseName(), course.getCourseDescription());
	}

	@Override
	public void updateCourse(Course course) {
		jdbcTemplate.update("UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?",
				course.getCourseName(), course.getCourseDescription(), course.getCourseId());
	}

	@Override
	public void deleteCourse(int id) {
		jdbcTemplate.update("DELETE FROM courses WHERE course_id = ?", id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Course getCourseByName(String courseName) {
		return jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_name = ?", new Object[] { courseName },
				new BeanPropertyRowMapper<>(Course.class));
	}
}
