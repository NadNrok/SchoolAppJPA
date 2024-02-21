package com.fm.school.config;

import com.fm.school.model.Course;
import com.fm.school.model.Group;
import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import com.fm.school.repository.CourseRepository;
import com.fm.school.repository.GroupRepository;
import com.fm.school.repository.StudentCourseRepository;
import com.fm.school.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

@Component
public class ApplicationRunner {

	private static final String[] FIRST_NAMES = { "John", "Alice", "Bob", "Emma", "Michael", "Olivia", "William",
			"Sophia", "James", "Charlotte", "Daniel", "Emily", "Jacob", "Mia", "Alexander", "Abigail", "David", "Ella",
			"Matthew", "Sofia" };
	private static final String[] LAST_NAMES = { "Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore",
			"Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez",
			"Robinson", "Clark", "Lewis" };
	private static final String[] COURSES = { "Math", "Biology", "Physics", "Chemistry", "History", "English",
			"Computer Science", "Geography", "Art", "Economics" };

	private static final Random random = new Random();

	private final CourseRepository courseRepository;
	private final GroupRepository groupRepository;
	private final StudentCourseRepository studentCourseRepository;
	private final StudentRepository studentRepository;

	@Qualifier("myDataSource")
	private final DataSource dataSource;

	public ApplicationRunner(CourseRepository courseRepository, GroupRepository groupRepository, StudentCourseRepository studentCourseRepository, StudentRepository studentRepository, DataSource dataSource) {
		this.courseRepository = courseRepository;
		this.groupRepository = groupRepository;
		this.studentCourseRepository = studentCourseRepository;
		this.studentRepository = studentRepository;
		this.dataSource = dataSource;
	}


	@PostConstruct
	private void migrateAndGenerateData() {
		migrateDatabase();
		generateTestDataIfNeeded();
	}

	private void migrateDatabase() {
		Flyway flyway = Flyway.configure().dataSource(dataSource).load();
		flyway.migrate();
	}

	private void generateTestDataIfNeeded() {
		if (!databaseHasData()) {
			generateTestData();
		}
	}

	private boolean databaseHasData() {
	    Long totalStudents = studentRepository.getCount();
	    Long totalCourses = courseRepository.getCount();
	    Long totalGroups = groupRepository.getCount();
	    Long totalStudentCourses = studentCourseRepository.getCount();

	    long totalCount = totalStudents + totalCourses + totalGroups + totalStudentCourses;
	    return totalCount > 0;
	}


	private void generateTestData() {
		insertCourses();
		generateAndInsertGroups();
		generateAndInsertStudents();
		generateAndInsertStudentCourses();
	}

	private void insertCourses() {
		for (String courseName : COURSES) {
			courseRepository.save(new Course(courseName));
		}
	}

	private void generateAndInsertGroups() {
		Set<String> groupSet = new HashSet<>();

		while (groupSet.size() < 10) {
			String groupName = generateRandomGroupName();
			if (!groupSet.contains(groupName)) {
				groupSet.add(groupName);
				groupRepository.save(new Group(groupName));
			}
		}
	}

	private List<Integer> getRandomGroupIds(int numberOfGroups) {
		List<Integer> groupIds = new ArrayList<>();
		for (int i = 1; i <= numberOfGroups; i++) {
			groupIds.add(i);
		}
		Collections.shuffle(groupIds);
		return groupIds.subList(0, 10);
	}

	private String generateRandomGroupName() {
        return String.valueOf((char) (random.nextInt(26) + 'A')) +
				(char) (random.nextInt(26) + 'A') +
				"-" +
				random.nextInt(100);
	}

	private void generateAndInsertStudents() {
		Set<String> studentSet = new HashSet<>();

		List<Integer> groupIds = getRandomGroupIds(10);

		for (Integer groupId : groupIds) {
			int studentsInGroup = random.nextInt(21) + 10;

			for (int i = 0; i < studentsInGroup; i++) {
				String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
				String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

				String fullName = firstName + " " + lastName;

				if (!studentSet.contains(fullName)) {
					studentSet.add(fullName);
					Optional<Group> groupByGroupId = groupRepository.findById(groupId);
					studentRepository.save(new Student(groupByGroupId.get(), firstName, lastName));
				}
			}
		}
	}

	private void generateAndInsertStudentCourses() {
		List<Integer> studentIds = getStudentIds();
		List<Integer> courseIds = getCourseIds();

		for (Integer studentId : studentIds) {
			for (Integer courseId : courseIds) {
				boolean studentCourseExists = checkIfStudentCourseExists(studentId, courseId);
				if (!studentCourseExists) {
					studentCourseRepository.save(new StudentCourse(studentId, courseId));
				}
			}
		}
	}

	private boolean checkIfStudentCourseExists(Integer studentId, Integer courseId) {
		Optional<Student> student = studentRepository.findById(studentId);
		Optional<Course> course = courseRepository.findById(courseId);
		Integer count = studentCourseRepository.checkIfStudentCourseExists(student.get(), course.get());
		return count != null && count > 0;
	}

	private List<Integer> getStudentIds() {
		return studentRepository.getStudentIds();
	}

	private List<Integer> getCourseIds() {
		return courseRepository.getCourseIds();
	}

}
