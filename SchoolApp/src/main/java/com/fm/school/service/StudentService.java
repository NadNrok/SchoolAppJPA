package com.fm.school.service;

import com.fm.school.model.Student;

import java.util.List;

public interface StudentService {
	List<Student> getAllStudents();

	Student getStudentById(int studentId);

	void addStudent(Student student);

	void updateStudent(Student student);

	void deleteStudent(int studentId);

	List<Student> getStudentsByCourseName(String courseName);

	void deleteStudentById(int studentId);
	
}
