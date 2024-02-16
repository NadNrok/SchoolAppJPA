package com.fm.school.controller;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.fm.school.model.Course;
import com.fm.school.model.Group;
import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import com.fm.school.service.CourseService;
import com.fm.school.service.GroupService;
import com.fm.school.service.StudentCourseService;
import com.fm.school.service.StudentService;
import com.fm.school.util.ScannerUtil;

import jakarta.annotation.PostConstruct;

@Component
@DependsOn("databaseInitializer")
public class Controller {

	@Autowired
	private GroupService groupService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentCourseService studentCourseService;

	@PostConstruct
	public void start() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			printMenu();
			choice = ScannerUtil.getIntInput("Enter your choice: ");

			switch (choice) {
			case 1:
				findAllGroupsWithLessOrEqualStudents(scanner);
				break;
			case 2:
				findAllStudentsByCourseName(scanner);
				break;
			case 3:
				addNewStudent(scanner);
				break;
			case 4:
				deleteStudentById(scanner);
				break;
			case 5:
				addStudentToCourse(scanner);
				break;
			case 6:
				removeStudentFromCourse(scanner);
				break;
			case 0:
				System.out.println("Exiting the program.");
				System.exit(0);
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
			}
			System.out.println();
		} while (choice != 0);

		scanner.close();
	}

	private void printMenu() {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("1. Find all groups with less or equal students’ number");
		System.out.println("2. Find all students related to the course with the given name");
		System.out.println("3. Add a new student");
		System.out.println("4. Delete a student by the STUDENT_ID");
		System.out.println("5. Add a student to the course (from a list)");
		System.out.println("6. Remove the student from one of their courses");
		System.out.println("0. Exit");
		System.out.println("-----------------------------------------------------------------");

	}

	void findAllGroupsWithLessOrEqualStudents(Scanner scanner) {
		int numberOfStudents = ScannerUtil.getIntInput("Enter the number of students: ");
		List<Group> groups = groupService.findAllGroupsWithLessOrEqualStudents(numberOfStudents);

		System.out.println("Groups with less or equal students’ number:");
		for (Group group : groups) {
			System.out.println("Group ID: " + group.getGroupId() + ", Group Name: " + group.getGroupName());
		}
	}

	void findAllStudentsByCourseName(Scanner scanner) {
		List<Course> courses = courseService.getAllCourses();
		System.out.println("Available courses:");
		for (int i = 0; i < courses.size(); i++) {
			System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
		}

		String input = ScannerUtil.getStringInput("Enter the course name: ");

		List<Student> students = studentService.getStudentsByCourseName(input);
		if (students.isEmpty()) {
			System.out.println("No students found for the course '" + input + "'.");
		} else {
			System.out.println("Students related to the course '" + input + "':");
			for (Student student : students) {
				System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
			}
		}
	}

	void addNewStudent(Scanner scanner) {
		String firstName = ScannerUtil.getStringInput("Enter student's first name: ");
		String lastName = ScannerUtil.getStringInput("Enter student's last name: ");

		List<Group> groups = groupService.getAllGroups();
		System.out.println("Available groups:");
		int index = 1;
		for (Group group : groups) {
			System.out.println(index + ". " + group.getGroupName());
			index++;
		}

		if (groups.isEmpty()) {
			System.out.println("There are no available groups to assign the student to.");
			return;
		}

		int groupIndex = 0;
		while (true) {
			try {
				groupIndex = Integer
						.parseInt(ScannerUtil.getStringInput("Enter the number of the group to assign the student: "));
				if (groupIndex < 1 || groupIndex > groups.size()) {
					System.out.println(
							"Invalid group number. Please enter a number between 1 and " + groups.size() + ".");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid integer.");
			}
		}

		Group selectedGroup = groups.get(groupIndex - 1);

		Student newStudent = new Student();
		newStudent.setFirstName(firstName);
		newStudent.setLastName(lastName);
		newStudent.setGroupId(selectedGroup.getGroupId());

		studentService.addStudent(newStudent);

		System.out.println("Student added successfully!");
	}

	void deleteStudentById(Scanner scanner) {
		List<Student> students = studentService.getAllStudents();

		System.out.println("Available students:");
		for (Student student : students) {
			System.out.println("ID: " + student.getStudentId() + ", Name: " + student.getFirstName() + " "
					+ student.getLastName());
		}

		int studentId = ScannerUtil.getIntInput("Enter the student ID to delete: ");

		studentService.deleteStudentById(studentId);

		System.out.println("Student with ID " + studentId + " has been deleted.");
	}

	void addStudentToCourse(Scanner scanner) {
		List<Student> students = studentService.getAllStudents();
		System.out.println("Available students:");
		for (int i = 0; i < students.size(); i++) {
			Student student = students.get(i);
			System.out.println((i + 1) + ". " + student.getFirstName() + " " + student.getLastName() + " (Student ID: "
					+ student.getStudentId() + ")");
		}

		int studentIndex = ScannerUtil.getIntInput("Enter the number of the student to add to the course: ");
		if (studentIndex < 1 || studentIndex > students.size()) {
			System.out.println("Invalid student number. Please enter a number between 1 and " + students.size() + ".");
			return;
		}
		Student selectedStudent = students.get(studentIndex - 1);

		List<Course> courses = courseService.getAllCourses();
		System.out.println("Available courses:");
		for (int i = 0; i < courses.size(); i++) {
			Course course = courses.get(i);
			System.out.println((i + 1) + ". " + course.getCourseName());
		}

		int courseIndex = ScannerUtil.getIntInput("Enter the number of the course to add the student to: ");
		if (courseIndex < 1 || courseIndex > courses.size()) {
			System.out.println("Invalid course number. Please enter a number between 1 and " + courses.size() + ".");
			return;
		}
		Course selectedCourse = courses.get(courseIndex - 1);

		if (studentCourseService.isStudentEnrolledInCourse(selectedStudent.getStudentId(),
				selectedCourse.getCourseId())) {
			System.out.println("Student is already enrolled in the selected course.");
			return;
		}

		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setStudentId(selectedStudent.getStudentId());
		studentCourse.setCourseId(selectedCourse.getCourseId());
		studentCourseService.addStudentCourse(studentCourse);

		System.out.println("Student added to the course successfully!");
	}

	void removeStudentFromCourse(Scanner scanner) {
		List<Student> students = studentService.getAllStudents();
		System.out.println("Available students:");
		for (int i = 0; i < students.size(); i++) {
			Student student = students.get(i);
			System.out.println((i + 1) + ". " + student.getFirstName() + " " + student.getLastName() + " (Student ID: "
					+ student.getStudentId() + ")");
		}

		int studentIndex = ScannerUtil.getIntInput("Enter the number of the student to remove from a course: ");
		if (studentIndex < 1 || studentIndex > students.size()) {
			System.out.println("Invalid student number. Please enter a number between 1 and " + students.size() + ".");
			return;
		}
		Student selectedStudent = students.get(studentIndex - 1);

		List<StudentCourse> studentCourses = studentCourseService
				.getStudentCoursesByStudentId(selectedStudent.getStudentId());
		if (studentCourses.isEmpty()) {
			System.out.println("The selected student is not enrolled in any courses.");
			return;
		}

		System.out.println("Courses enrolled by the selected student:");
		for (int i = 0; i < studentCourses.size(); i++) {
			StudentCourse studentCourse = studentCourses.get(i);
			Course course = courseService.getCourseById(studentCourse.getCourseId());
			System.out.println((i + 1) + ". " + course.getCourseName());
		}

		int courseIndex = ScannerUtil.getIntInput("Enter the number of the course to remove the student from: ");
		if (courseIndex < 1 || courseIndex > studentCourses.size()) {
			System.out.println(
					"Invalid course number. Please enter a number between 1 and " + studentCourses.size() + ".");
			return;
		}
		StudentCourse selectedStudentCourse = studentCourses.get(courseIndex - 1);

		studentCourseService.deleteStudentCourse(selectedStudentCourse.getStudentId(),
				selectedStudentCourse.getCourseId());
		System.out.println("Student removed from the course successfully!");
	}

}
