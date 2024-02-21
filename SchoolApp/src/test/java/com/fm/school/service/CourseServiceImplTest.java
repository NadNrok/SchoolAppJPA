package com.fm.school.service;

import com.fm.school.dao.CourseRepository;
import com.fm.school.model.Course;
import com.fm.school.service.impl.CourseServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

	@Mock
	private CourseRepository courseDAO;

	@InjectMocks
	private CourseService courseService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllCourses() {
		List<Course> courses = Arrays.asList(new Course(1, "Math", "Mathematics"), new Course(2, "Physics", "Physics"));
		when(courseDAO.getAllCourses()).thenReturn(courses);

		List<Course> result = courseService.getAllCourses();

		assertEquals(courses.size(), result.size());
		assertEquals(courses.get(0), result.get(0));
		assertEquals(courses.get(1), result.get(1));
	}

	@Test
	public void testGetCourseById() {
		Course course = new Course(1, "Math", "Mathematics");
		when(courseDAO.getCourseById(1)).thenReturn(course);

		Course result = courseService.getCourseById(1);

		assertEquals(course, result);
	}

	@Test
	public void testAddCourse() {
		Course course = new Course(1, "Math", "Mathematics");

		courseService.addCourse(course);

		verify(courseDAO, times(1)).addCourse(course);
	}

	@Test
	public void testUpdateCourse() {
		Course course = new Course(1, "Math", "Mathematics");

		courseService.updateCourse(course);

		verify(courseDAO, times(1)).updateCourse(course);
	}

	@Test
	public void testDeleteCourse() {
		int courseId = 1;

		courseService.deleteCourse(courseId);

		verify(courseDAO, times(1)).deleteCourse(courseId);
	}

	@Test
	public void testGetCourseByName() {
		Course course = new Course(1, "Math", "Mathematics");
		when(courseDAO.getCourseByName("Math")).thenReturn(course);

		Course result = courseService.getCourseByName("Math");

		assertEquals(course, result);
	}
}
