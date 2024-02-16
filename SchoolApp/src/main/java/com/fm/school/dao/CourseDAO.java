package com.fm.school.dao;

import com.fm.school.model.Course;

import java.util.List;

public interface CourseDAO {
	List<Course> getAllCourses();

	Course getCourseById(int courseId);

	void addCourse(Course course);

	void updateCourse(Course course);

	void deleteCourse(int courseId);

	Course getCourseByName(String courseName);
}
