package com.fm.school.service;

import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;

import java.util.List;

public interface StudentCourseService {
	List<StudentCourse> getAllStudentCourses();

	List<StudentCourse> getStudentCoursesByStudentId(int studentId);

	List<StudentCourse> getStudentCoursesByCourseId(int courseId);

	void addStudentCourse(StudentCourse studentCourse);

	void deleteStudentCourse(int studentId, int courseId);

	void addStudentToCourse(StudentCourse studentCourse);

	boolean isStudentEnrolledInCourse(int studentId, int courseId);

	List<Course> getStudentEnrolledCourses(int studentId);
}
