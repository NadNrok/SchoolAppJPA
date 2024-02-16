package com.fm.school.service;

import com.fm.school.dao.StudentCourseDAO;
import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	private final StudentCourseDAO studentCourseDAO;

	@Autowired
	public StudentCourseServiceImpl(StudentCourseDAO studentCourseDAO) {
		this.studentCourseDAO = studentCourseDAO;
	}

	@Override
	public List<Course> getStudentEnrolledCourses(int studentId) {
		return studentCourseDAO.getStudentEnrolledCourses(studentId);
	}

	@Override
	public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
		return studentCourseDAO.isStudentEnrolledInCourse(studentId, courseId);
	}

	@Override
	public void addStudentToCourse(StudentCourse studentCourse) {
		studentCourseDAO.addStudentCourse(studentCourse);
	}

	@Override
	public List<StudentCourse> getAllStudentCourses() {
		return studentCourseDAO.getAllStudentCourses();
	}

	@Override
	public List<StudentCourse> getStudentCoursesByStudentId(int studentId) {
		return studentCourseDAO.getStudentCoursesByStudentId(studentId);
	}

	@Override
	public List<StudentCourse> getStudentCoursesByCourseId(int courseId) {
		return studentCourseDAO.getStudentCoursesByCourseId(courseId);
	}

	@Override
	public void addStudentCourse(StudentCourse studentCourse) {
		studentCourseDAO.addStudentCourse(studentCourse);
	}

	@Override
	public void deleteStudentCourse(int studentId, int courseId) {
		studentCourseDAO.deleteStudentCourse(studentId, courseId);
	}
}
