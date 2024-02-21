package com.fm.school.service;

import com.fm.school.dao.StudentRepository;
import com.fm.school.model.Group;
import com.fm.school.model.Student;
import com.fm.school.service.impl.StudentServiceImpl;

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
	private StudentRepository studentDAO;

	@InjectMocks
	private StudentService studentService;

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
		Group group = new Group(1, "GroupName");
		Student[] studentsArray = { new Student(1, group, "John", "Doe"), new Student(2, group, "Jane", "Smith") };

		List<Student> students = Arrays.asList(studentsArray);
		when(studentDAO.getAllStudentsByCourseName(courseName)).thenReturn(students);
		List<Student> result = studentService.getStudentsByCourseName(courseName);

		assertEquals(students.size(), result.size());
		assertEquals(students.get(0), result.get(0));
		assertEquals(students.get(1), result.get(1));
	}

	@Test
	public void testGetAllStudents() {
		Group group1 = new Group(1, "GroupName1");
		Group group2 = new Group(2, "GroupName2");
		Student[] studentsArray = { new Student(1, group1, "John", "Doe"), new Student(2, group2, "Jane", "Smith") };

		List<Student> students = Arrays.asList(studentsArray);
		when(studentDAO.getAllStudents()).thenReturn(students);
		List<Student> result = studentService.getAllStudents();
		assertEquals(students.size(), result.size());
		assertEquals(students.get(0), result.get(0));
		assertEquals(students.get(1), result.get(1));
	}

	@Test
	public void testGetStudentById() {
		int studentId = 1;
		Group group = new Group(1, "GroupName");
		Student student = new Student(studentId, group, "John", "Doe");
		when(studentDAO.getStudentById(studentId)).thenReturn(student);
		Student result = studentService.getStudentById(studentId);
		assertEquals(student, result);
	}

	@Test
	public void testAddStudent() {
		Group group = new Group(1, "GroupName");
		Student student = new Student(1, group, "John", "Doe");
		studentService.addStudent(student);
		verify(studentDAO, times(1)).addStudent(student);
	}

	@Test
	public void testUpdateStudent() {
		Group group = new Group(1, "GroupName");
		Student student = new Student(1, group, "John", "Doe");
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
