package com.fm.school.service;

import com.fm.school.dao.StudentDAO;
import com.fm.school.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

	@Mock
	private StudentDAO studentDAO;

	@InjectMocks
	private StudentServiceImpl studentService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDeleteStudentById() {
		int studentId = 1;

		studentService.deleteStudentById(studentId);

		verify(studentDAO, times(1)).deleteStudentById(studentId);
	}

	@Test
	public void testGetStudentsByCourseName() {
		String courseName = "Math";
		List<Student> students = Arrays.asList(new Student(1, 1, "John", "Doe"), new Student(2, 1, "Jane", "Smith"));
		when(studentDAO.getAllStudentsByCourseName(courseName)).thenReturn(students);

		List<Student> result = studentService.getStudentsByCourseName(courseName);

		assertEquals(students.size(), result.size());
		assertEquals(students.get(0), result.get(0));
		assertEquals(students.get(1), result.get(1));
	}

	@Test
	public void testGetAllStudents() {
		List<Student> students = Arrays.asList(new Student(1, 1, "John", "Doe"), new Student(2, 2, "Jane", "Smith"));
		when(studentDAO.getAllStudents()).thenReturn(students);

		List<Student> result = studentService.getAllStudents();

		assertEquals(students.size(), result.size());
		assertEquals(students.get(0), result.get(0));
		assertEquals(students.get(1), result.get(1));
	}

	@Test
	public void testGetStudentById() {
		int studentId = 1;
		Student student = new Student(studentId, 1, "John", "Doe");
		when(studentDAO.getStudentById(studentId)).thenReturn(student);

		Student result = studentService.getStudentById(studentId);

		assertEquals(student, result);
	}

	@Test
	public void testAddStudent() {
		Student student = new Student(1, 1, "John", "Doe");

		studentService.addStudent(student);

		verify(studentDAO, times(1)).addStudent(student);
	}

	@Test
	public void testUpdateStudent() {
		Student student = new Student(1, 1, "John", "Doe");

		studentService.updateStudent(student);

		verify(studentDAO, times(1)).updateStudent(student);
	}

	@Test
	public void testDeleteStudent() {
		int studentId = 1;

		studentService.deleteStudent(studentId);

		verify(studentDAO, times(1)).deleteStudent(studentId);
	}
}
