package com.fm.school.dao;

import com.fm.school.model.Course;
import org.junit.jupiter.api.BeforeEach;
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
public class CourseDAOImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CourseDAO courseDAO;

	@BeforeEach
	void setUp() {
		courseDAO = new CourseDAOImpl(jdbcTemplate);
	}

	@Test
	@Sql(statements = {
			"INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')",
			"INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics', 'Physics course')" })
	public void testGetAllCourses() {
		List<Course> courses = courseDAO.getAllCourses();
		assertThat(courses).isNotNull();
		assertThat(courses).hasSize(2);
	}

	@Test
	@Sql(statements = "INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')")
	public void testGetCourseById() {
		Course course = courseDAO.getCourseById(1);
		assertThat(course).isNotNull();
		assertThat(course.getCourseName()).isEqualTo("Math");
		assertThat(course.getCourseDescription()).isEqualTo("Mathematics course");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testAddCourse() {
		Course course = new Course();
		course.setCourseName("Chemistry");
		course.setCourseDescription("Chemistry course");
		courseDAO.addCourse(course);

		Course retrievedCourse = jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_name = ?",
				new Object[] { "Chemistry" }, (rs, rowNum) -> new Course(rs.getInt("course_id"),
						rs.getString("course_name"), rs.getString("course_description")));

		assertThat(retrievedCourse).isNotNull();
		assertThat(retrievedCourse.getCourseName()).isEqualTo("Chemistry");
		assertThat(retrievedCourse.getCourseDescription()).isEqualTo("Chemistry course");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateCourse() {
		jdbcTemplate.update("INSERT INTO courses (course_id, course_name, course_description) VALUES (?, ?, ?)", 1,
				"Math", "Mathematics course");

		Course updatedCourse = new Course(1, "Mathematics", "Mathematics course updated");
		courseDAO.updateCourse(updatedCourse);

		Course retrievedCourse = jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_id = ?",
				new Object[] { 1 }, (rs, rowNum) -> new Course(rs.getInt("course_id"), rs.getString("course_name"),
						rs.getString("course_description")));

		assertThat(retrievedCourse).isNotNull();
		assertThat(retrievedCourse.getCourseName()).isEqualTo("Mathematics");
		assertThat(retrievedCourse.getCourseDescription()).isEqualTo("Mathematics course updated");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDeleteCourse() {
		jdbcTemplate.update("INSERT INTO courses (course_id, course_name, course_description) VALUES (?, ?, ?)", 1,
				"Math", "Mathematics course");

		courseDAO.deleteCourse(1);

		Course deletedCourse = jdbcTemplate.queryForObject("SELECT * FROM courses WHERE course_id = ?",
				new Object[] { 1 }, (rs, rowNum) -> new Course(rs.getInt("course_id"), rs.getString("course_name"),
						rs.getString("course_description")));

		assertThat(deletedCourse).isNull();
	}

	@Test
	@Sql(statements = {
			"INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')",
			"INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics', 'Physics course')" })
	public void testGetCourseByName() {
		Course course = courseDAO.getCourseByName("Math");
		assertThat(course).isNotNull();
		assertThat(course.getCourseId()).isEqualTo(1);
	}
}
