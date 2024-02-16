package com.fm.school.service;

import com.fm.school.model.Course;

import java.util.List;

public interface CourseService {
	List<Course> getAllCourses();

	Course getCourseById(int courseId);

	void addCourse(Course course);

	void updateCourse(Course course);

	void deleteCourse(int courseId);

	Course getCourseByName(String courseName);
}
