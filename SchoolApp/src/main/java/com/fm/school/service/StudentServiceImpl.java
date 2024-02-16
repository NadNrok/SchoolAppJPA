package com.fm.school.service;

import com.fm.school.dao.StudentDAO;
import com.fm.school.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentDAO studentDAO;

	@Autowired
	public StudentServiceImpl(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	@Override
	public void deleteStudentById(int studentId) {
		studentDAO.deleteStudentById(studentId);
	}

	@Override
	public List<Student> getStudentsByCourseName(String courseName) {
		return studentDAO.getAllStudentsByCourseName(courseName);
	}

	@Override
	public List<Student> getAllStudents() {
		return studentDAO.getAllStudents();
	}

	@Override
	public Student getStudentById(int studentId) {
		return studentDAO.getStudentById(studentId);
	}

	@Override
	public void addStudent(Student student) {
		studentDAO.addStudent(student);
	}

	@Override
	public void updateStudent(Student student) {
		studentDAO.updateStudent(student);
	}

	@Override
	public void deleteStudent(int studentId) {
		studentDAO.deleteStudent(studentId);
	}
}
