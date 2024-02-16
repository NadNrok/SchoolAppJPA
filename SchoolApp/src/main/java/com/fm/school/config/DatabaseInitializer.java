package com.fm.school.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fm.school.service.ApplicationRunnerService;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DatabaseInitializer {

	private final JdbcTemplate jdbcTemplate;
	private final ApplicationRunnerService applicationRunnerService;

	private static final String[] FIRST_NAMES = { "John", "Alice", "Bob", "Emma", "Michael", "Olivia", "William",
			"Sophia", "James", "Charlotte", "Daniel", "Emily", "Jacob", "Mia", "Alexander", "Abigail", "David", "Ella",
			"Matthew", "Sofia" };
	private static final String[] LAST_NAMES = { "Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Moore",
			"Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez",
			"Robinson", "Clark", "Lewis" };
	private static final String[] COURSES = { "Math", "Biology", "Physics", "Chemistry", "History", "English",
			"Computer Science", "Geography", "Art", "Economics" };

	private static final Random random = new Random();

	@Autowired
	public DatabaseInitializer(JdbcTemplate jdbcTemplate, ApplicationRunnerService applicationRunnerService) {
		this.jdbcTemplate = jdbcTemplate;
		this.applicationRunnerService = applicationRunnerService;
	}

	@PostConstruct
	private void migrateAndGenerateData() {
		migrateDatabase();
		generateTestDataIfNeeded();
	}

	private void migrateDatabase() {
		Flyway flyway = Flyway.configure().dataSource(jdbcTemplate.getDataSource()).load();
		flyway.migrate();
	}

	private void generateTestDataIfNeeded() {
		if (!applicationRunnerService.databaseHasData()) {
			generateTestData();
		}
	}

	private void generateTestData() {
		insertCourses();
		generateAndInsertGroups();
		generateAndInsertStudents();
		generateAndInsertStudentCourses();
	}

	private void insertCourses() {
		String insertCourseQuery = "INSERT INTO courses (course_name) VALUES (?)";
		for (String courseName : COURSES) {
			jdbcTemplate.update(insertCourseQuery, courseName);
		}
	}

	private void generateAndInsertGroups() {
		String insertGroupQuery = "INSERT INTO groups (group_name) VALUES (?)";
		Set<String> groupSet = new HashSet<>();

		while (groupSet.size() < 10) {
			String groupName = generateRandomGroupName();
			if (!groupSet.contains(groupName)) {
				groupSet.add(groupName);
				jdbcTemplate.update(insertGroupQuery, groupName);
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
		StringBuilder groupName = new StringBuilder();
		groupName.append((char) (random.nextInt(26) + 'A'));
		groupName.append((char) (random.nextInt(26) + 'A'));
		groupName.append("-");
		groupName.append(random.nextInt(100));
		return groupName.toString();
	}

	private void generateAndInsertStudents() {
		String insertStudentQuery = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
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
					jdbcTemplate.update(insertStudentQuery, firstName, lastName, groupId);
				}
			}
		}
	}

	private void generateAndInsertStudentCourses() {
		String insertStudentCourseQuery = "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

		List<Integer> studentIds = getStudentIds();
		List<Integer> courseIds = getCourseIds();

		for (Integer studentId : studentIds) {
			for (Integer courseId : courseIds) {
				boolean studentCourseExists = checkIfStudentCourseExists(studentId, courseId);
				if (!studentCourseExists) {
					jdbcTemplate.update(insertStudentCourseQuery, studentId, courseId);
				}
			}
		}
	}

	private boolean checkIfStudentCourseExists(Integer studentId, Integer courseId) {
		String query = "SELECT COUNT(*) FROM student_courses WHERE student_id = ? AND course_id = ?";
		Integer count = jdbcTemplate.queryForObject(query, Integer.class, studentId, courseId);
		return count != null && count > 0;
	}

	private List<Integer> getStudentIds() {
		String query = "SELECT student_id FROM students";
		return jdbcTemplate.queryForList(query, Integer.class);
	}

	private List<Integer> getCourseIds() {
		String query = "SELECT course_id FROM courses";
		return jdbcTemplate.queryForList(query, Integer.class);
	}

}
