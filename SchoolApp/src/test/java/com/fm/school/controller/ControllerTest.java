package com.fm.school.controller;

import com.fm.school.model.Course;
import com.fm.school.model.Group;
import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import com.fm.school.service.CourseService;
import com.fm.school.service.GroupService;
import com.fm.school.service.StudentCourseService;
import com.fm.school.service.StudentService;
import com.fm.school.util.ScannerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class ControllerTest {

	@Mock
	private GroupService groupService;

	@Mock
	private StudentService studentService;

	@Mock
	private CourseService courseService;

	@Mock
	private StudentCourseService studentCourseService;

	@InjectMocks
	private Controller controller;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAllGroupsWithLessOrEqualStudents() {
		Scanner scanner = mock(Scanner.class);
		when(ScannerUtil.getIntInput("Enter the number of students: ")).thenReturn(5);

		List<Group> groups = new ArrayList<>();
		groups.add(new Group(1, "Group 1"));
		groups.add(new Group(2, "Group 2"));
		when(groupService.findAllGroupsWithLessOrEqualStudents(5)).thenReturn(groups);

		controller.findAllGroupsWithLessOrEqualStudents(scanner);

		verify(groupService, times(1)).findAllGroupsWithLessOrEqualStudents(5);
	}

	@Test
	public void testFindAllStudentsByCourseName() {
		Scanner scanner = mock(Scanner.class);
		when(courseService.getAllCourses()).thenReturn(new ArrayList<>());
		when(ScannerUtil.getStringInput("Enter the course name: ")).thenReturn("Math");

		controller.findAllStudentsByCourseName(scanner);

		verify(courseService, times(1)).getAllCourses();
		verify(studentService, times(1)).getStudentsByCourseName("Math");
	}

	@Test
	public void testAddNewStudent() {
	    Scanner scanner = mock(Scanner.class);
	    
	    when(groupService.getAllGroups()).thenReturn(new ArrayList<>());
	    when(ScannerUtil.getStringInput(anyString())).thenReturn("John", "Doe");
	    when(ScannerUtil.getIntInput("Enter the number of the group to assign the student: ")).thenReturn(1);

	    controller.addNewStudent(scanner);

	    verify(studentService, times(1)).addStudent(argThat(student ->
	            student.getFirstName().equals("John") &&
	            student.getLastName().equals("Doe") &&
	            student.getGroup() != null && student.getGroup().getGroupId() == 1));
	}


	@Test
	public void testDeleteStudentById() {
		Scanner scanner = mock(Scanner.class);
		when(studentService.getAllStudents()).thenReturn(new ArrayList<>());
		when(ScannerUtil.getIntInput("Enter the student ID to delete: ")).thenReturn(1);

		controller.deleteStudentById(scanner);

		verify(studentService, times(1)).deleteStudentById(1);
	}

	@Test
	public void testAddStudentToCourse() {
	    Scanner scanner = mock(Scanner.class);
	    List<Student> students = new ArrayList<>();
	    students.add(new Student(1, new Group(1, "Group 1"), "John", "Doe"));
	    List<Course> courses = new ArrayList<>();
	    courses.add(new Course(1, "Math", "Mathematics"));
	    when(studentService.getAllStudents()).thenReturn(students);
	    when(courseService.getAllCourses()).thenReturn(courses);
	    when(ScannerUtil.getIntInput(anyString())).thenReturn(1);

	    controller.addStudentToCourse(scanner);

	    verify(studentCourseService, times(1)).addStudentCourse(any());
	}

	@Test
	public void testRemoveStudentFromCourse() {
	    Scanner scanner = mock(Scanner.class);
	    List<Student> students = new ArrayList<>();
	    students.add(new Student(1, new Group(1, "Group 1"), "John", "Doe"));
	    List<StudentCourse> studentCourses = new ArrayList<>();
	    studentCourses.add(new StudentCourse(1, 1));
	    when(studentService.getAllStudents()).thenReturn(students);
	    when(studentCourseService.getStudentCoursesByStudentId(1)).thenReturn(studentCourses);
	    when(courseService.getCourseById(1)).thenReturn(new Course(1, "Math", "Mathematics"));
	    when(ScannerUtil.getIntInput(anyString())).thenReturn(1);

	    controller.removeStudentFromCourse(scanner);

	    verify(studentCourseService, times(1)).deleteStudentCourse(1, 1);
	}

}
