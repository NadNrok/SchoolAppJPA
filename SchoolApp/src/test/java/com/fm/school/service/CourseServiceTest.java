package com.fm.school.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fm.school.model.Course;
import com.fm.school.repository.CourseRepository;
import com.fm.school.service.CourseService;

public class CourseServiceTest {

	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private CourseService courseService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllCourses() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Math", "Math course"));
		courses.add(new Course(2, "Science", "Science course"));
		when(courseRepository.findAll()).thenReturn(courses);

		List<Course> result = courseService.getAllCourses();

		assertEquals(2, result.size());
		assertEquals("Math", result.get(0).getCourseName());
		assertEquals("Science", result.get(1).getCourseName());
	}

	@Test
	public void testGetCourseById() {
		Course course = new Course(1, "Math", "Math course");
		when(courseRepository.findById(1)).thenReturn(Optional.of(course));

		Course result = courseService.getCourseById(1);

		assertNotNull(result);
		assertEquals("Math", result.getCourseName());
	}

	@Test
    public void testGetCourseById_NotFound() {
        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        Course result = courseService.getCourseById(1);

        assertNull(result);
    }

	@Test
	public void testSaveCourse() {
		Course course = new Course(1, "Math", "Math course");

		courseService.saveCourse(course);

		verify(courseRepository, times(1)).save(course);
	}
}
