package com.fm.school.dao;

import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;

import java.util.List;

public interface StudentDAO {
	List<Student> getAllStudents();

	Student getStudentById(int studentId);

	void addStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(int studentId);

	List<Student> getAllStudentsByCourseName(String courseName);

	void deleteStudentById(int studentId);

	List<StudentCourse> getStudentCoursesByStudentId(int studentId);

	void deleteStudentCourse(int studentId, int courseId);
}
