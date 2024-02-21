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
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Scanner;

@Component
@DependsOn("applicationRunner")
public class Controller {

	private final GroupService groupService;
	private final StudentService studentService;
	private final CourseService courseService;
	private final StudentCourseService studentCourseService;

	public Controller(GroupService groupService, StudentService studentService, CourseService courseService,
			StudentCourseService studentCourseService) {
		this.groupService = groupService;
		this.studentService = studentService;
		this.courseService = courseService;
		this.studentCourseService = studentCourseService;
	}

	@PostConstruct
	public void start() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			printMenu();
			choice = ScannerUtil.getIntInput(scanner, "Enter your choice: ");

			switch (choice) {
			case 1:
				getGroupsWithLessOrEqualStudents(scanner);
				break;
			case 2:
				displayStudentsByCourseName(scanner);
				break;
			case 3:
				addStudent(scanner);
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
		System.out.println("4. Delete a student by the Student_ID");
		System.out.println("5. Add a student to the course");
		System.out.println("6. Remove the student from one of their courses");
		System.out.println("0. Exit");
		System.out.println("-----------------------------------------------------------------");
	}

	@GetMapping("/groups")
	public void getGroupsWithLessOrEqualStudents(Scanner scanner) {
		int maxStudents = ScannerUtil.getIntInput(scanner, "Enter the maximum number of students: ");
		List<Group> groups = groupService.findGroupsWithLessOrEqualStudents(maxStudents);
		for (Group group : groups) {
			System.out.println("Group: " + group.getGroupName());
		}
	}

	@GetMapping("/courses")
	public void displayAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		System.out.println("List of courses:");
		for (Course course : courses) {
			System.out.println(course.getCourseName());
		}
	}

	@GetMapping("/studentsByCourse")
	public void displayStudentsByCourseName(Scanner scanner) {
		List<Course> courses = courseService.getAllCourses();

		System.out.println("Available courses:");
		for (int i = 0; i < courses.size(); i++) {
			System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
		}

		int courseChoice = ScannerUtil.getIntInput(scanner, "Enter the number of the course: ");

		if (courseChoice >= 1 && courseChoice <= courses.size()) {
			String courseName = courses.get(courseChoice - 1).getCourseName();

			List<Student> students = studentCourseService.findStudentsByCourseName(courseName);

			if (students.isEmpty()) {
				System.out.println("No students found for the course \"" + courseName + "\".");
			} else {
				System.out.println("Students enrolled in the course \"" + courseName + "\":");
				for (Student student : students) {
					System.out.println(student.getFirstName() + " " + student.getLastName());
				}
			}
		} else {
			System.out.println("Invalid course number. Please enter a valid option.");
		}
	}

	@PostMapping("/addStudent")
	public void addStudent(Scanner scanner) {
		System.out.println("Enter the first name of the student: ");
		String firstName = scanner.nextLine();

		System.out.println("Enter the last name of the student: ");
		String lastName = scanner.nextLine();

		List<Group> groups = groupService.getAllGroups();
		System.out.println("Available groups:");
		for (int i = 0; i < groups.size(); i++) {
			System.out.println((i + 1) + ". " + groups.get(i).getGroupName());
		}

		int groupChoice = ScannerUtil.getIntInput(scanner, "Enter the number of the group: ");
		if (groupChoice >= 1 && groupChoice <= groups.size()) {
			Group selectedGroup = groups.get(groupChoice - 1);
			Student newStudent = new Student();
			newStudent.setFirstName(firstName);
			newStudent.setLastName(lastName);
			newStudent.setGroup(selectedGroup);
			studentService.saveStudent(newStudent);
			System.out.println("Student \"" + firstName + " " + lastName + "\" added successfully to group \""
					+ selectedGroup.getGroupName() + "\".");
		} else {
			System.out.println("Invalid group number. Please enter a valid option.");
		}
	}

	@PostMapping("/deleteStudent")
	public void deleteStudentById(Scanner scanner) {
		List<Student> students = studentService.getAllStudents();
		System.out.println("Available students:");
		for (Student student : students) {
			System.out.println(student.getStudentId() + ". " + student.getFirstName() + " " + student.getLastName());
		}

		int studentId = ScannerUtil.getIntInput(scanner, "Enter the ID of the student to delete: ");
		Student student = studentService.getStudentById(studentId);
		if (student != null) {
			studentService.deleteStudent(student);
			System.out.println("Student with ID " + studentId + " has been deleted successfully.");
		} else {
			System.out.println("Student with ID " + studentId + " not found.");
		}
	}

	@PostMapping("/addStudentToCourse")
	public void addStudentToCourse(Scanner scanner) {

		List<Student> students = studentService.getAllStudents();
		System.out.println("Available students:");
		for (Student student : students) {
			System.out.println(student.getStudentId() + ". " + student.getFirstName() + " " + student.getLastName());
		}

		int studentId = ScannerUtil.getIntInput(scanner, "Enter the ID of the student: ");
		Student student = studentService.getStudentById(studentId);
		if (student == null) {
			System.out.println("Student with ID " + studentId + " not found.");
			return;
		}

		List<Course> courses = courseService.getAllCourses();
		System.out.println("Available courses:");
		for (Course course : courses) {
			System.out.println(course.getCourseId() + ". " + course.getCourseName());
		}

		int courseId = ScannerUtil.getIntInput(scanner, "Enter the ID of the course: ");
		Course course = courseService.getCourseById(courseId);
		if (course == null) {
			System.out.println("Course with ID " + courseId + " not found.");
			return;
		}

		List<StudentCourse> studentCourses = student.getCourses();
		for (StudentCourse sc : studentCourses) {
			if (sc.getCourse().getCourseId() == courseId) {
				System.out.println("Student " + student.getFirstName() + " " + student.getLastName()
						+ " is already enrolled in the course " + course.getCourseName());
				return;
			}
		}

		StudentCourse newStudentCourse = new StudentCourse();
		newStudentCourse.setStudent(student);
		newStudentCourse.setCourse(course);
		studentCourseService.saveStudentCourse(newStudentCourse);

		System.out.println("Student " + student.getFirstName() + " " + student.getLastName()
				+ " has been added to the course " + course.getCourseName() + " successfully.");
	}

	@DeleteMapping("/removeStudentFromCourse")
	public void removeStudentFromCourse(Scanner scanner) {
		List<Student> students = studentService.getAllStudents();
		System.out.println("Available students:");
		for (Student student : students) {
			System.out.println(student.getStudentId() + ". " + student.getFirstName() + " " + student.getLastName());
		}

		int studentId = ScannerUtil.getIntInput(scanner, "Enter the ID of the student: ");
		Student student = studentService.getStudentById(studentId);
		if (student == null) {
			System.out.println("Student with ID " + studentId + " not found.");
			return;
		}

		List<StudentCourse> studentCourses = student.getCourses();
		if (studentCourses.isEmpty()) {
			System.out.println("Student " + student.getFirstName() + " " + student.getLastName()
					+ " is not enrolled in any courses.");
			return;
		}

		System.out.println("Courses enrolled by student " + student.getFirstName() + " " + student.getLastName() + ":");
		for (int i = 0; i < studentCourses.size(); i++) {
			Course course = studentCourses.get(i).getCourse();
			System.out.println((i + 1) + ". " + course.getCourseName());
		}

		int courseChoice = ScannerUtil.getIntInput(scanner, "Enter the number of the course to remove: ");
		if (courseChoice >= 1 && courseChoice <= studentCourses.size()) {
			StudentCourse studentCourseToRemove = studentCourses.get(courseChoice - 1);
			Course courseToRemove = studentCourseToRemove.getCourse();

			studentCourseService.deleteStudentCourse(studentCourseToRemove);
			System.out.println("Student " + student.getFirstName() + " " + student.getLastName()
					+ " has been removed from the course " + courseToRemove.getCourseName() + " successfully.");
		} else {
			System.out.println("Invalid course number. Please enter a valid option.");
		}
	}

}
