package com.fm.school.service;

import com.fm.school.dao.StudentCourseRepository;
import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;
import com.fm.school.service.impl.StudentCourseServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentCourseServiceImplTest {

	@Mock
	private StudentCourseRepository studentCourseDAO;

	@InjectMocks
	private StudentCourseService studentCourseService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetStudentEnrolledCourses() {
		int studentId = 1;
		List<Course> courses = Arrays.asList(new Course(1, "Math", "Mathematics course"),
				new Course(2, "Physics", "Physics course"));
		when(studentCourseDAO.getStudentEnrolledCourses(studentId)).thenReturn(courses);

		List<Course> result = studentCourseService.getStudentEnrolledCourses(studentId);

		assertEquals(courses.size(), result.size());
		assertEquals(courses.get(0), result.get(0));
		assertEquals(courses.get(1), result.get(1));
	}

	@Test
	public void testIsStudentEnrolledInCourse() {
		int studentId = 1;
		int courseId = 1;
		when(studentCourseDAO.isStudentEnrolledInCourse(studentId, courseId)).thenReturn(true);

		boolean result = studentCourseService.isStudentEnrolledInCourse(studentId, courseId);

		assertEquals(true, result);
	}

	@Test
	public void testAddStudentToCourse() {
		StudentCourse studentCourse = new StudentCourse(1, 1);

		studentCourseService.addStudentToCourse(studentCourse);

		verify(studentCourseDAO, times(1)).addStudentCourse(studentCourse);
	}

	@Test
	public void testGetAllStudentCourses() {
		List<StudentCourse> studentCourses = Arrays.asList(new StudentCourse(1, 1), new StudentCourse(2, 2));
		when(studentCourseDAO.getAllStudentCourses()).thenReturn(studentCourses);

		List<StudentCourse> result = studentCourseService.getAllStudentCourses();

		assertEquals(studentCourses.size(), result.size());
		assertEquals(studentCourses.get(0), result.get(0));
		assertEquals(studentCourses.get(1), result.get(1));
	}

	@Test
	public void testGetStudentCoursesByStudentId() {
		int studentId = 1;
		List<StudentCourse> studentCourses = Arrays.asList(new StudentCourse(1, 1), new StudentCourse(1, 2));
		when(studentCourseDAO.getStudentCoursesByStudentId(studentId)).thenReturn(studentCourses);

		List<StudentCourse> result = studentCourseService.getStudentCoursesByStudentId(studentId);

		assertEquals(studentCourses.size(), result.size());
		assertEquals(studentCourses.get(0), result.get(0));
		assertEquals(studentCourses.get(1), result.get(1));
	}

	@Test
	public void testGetStudentCoursesByCourseId() {
		int courseId = 1;
		List<StudentCourse> studentCourses = Arrays.asList(new StudentCourse(1, 1), new StudentCourse(2, 1));
		when(studentCourseDAO.getStudentCoursesByCourseId(courseId)).thenReturn(studentCourses);

		List<StudentCourse> result = studentCourseService.getStudentCoursesByCourseId(courseId);

		assertEquals(studentCourses.size(), result.size());
		assertEquals(studentCourses.get(0), result.get(0));
		assertEquals(studentCourses.get(1), result.get(1));
	}

	@Test
	public void testDeleteStudentCourse() {
		int studentId = 1;
		int courseId = 1;

		studentCourseService.deleteStudentCourse(studentId, courseId);

		verify(studentCourseDAO, times(1)).deleteStudentCourse(studentId, courseId);
	}
}
