package com.fm.school.dao;

import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;

import java.util.List;

public interface StudentCourseDAO {
	List<StudentCourse> getAllStudentCourses();

	List<StudentCourse> getStudentCoursesByStudentId(int studentId);

	List<StudentCourse> getStudentCoursesByCourseId(int courseId);

	void addStudentCourse(StudentCourse studentCourse);

	void deleteStudentCourse(int studentId, int courseId);

	boolean isStudentEnrolledInCourse(int studentId, int courseId);

	List<Course> getStudentEnrolledCourses(int studentId);
}
