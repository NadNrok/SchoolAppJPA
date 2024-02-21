package com.fm.school.service;

import com.fm.school.model.Course;
import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import com.fm.school.repository.CourseRepository;
import com.fm.school.repository.StudentCourseRepository;
import com.fm.school.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    private final CourseRepository courseRepository;

    public StudentCourseService(StudentCourseRepository studentCourseRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        Course course = courseRepository.findByCourseName(courseName);
        List<Student> students = new ArrayList<>();
        if (course != null) {
            List<StudentCourse> studentCourses = studentCourseRepository.findByCourse(course);
            for (StudentCourse studentCourse : studentCourses) {
                students.add(studentCourse.getStudent());
            }
        }
        return students;
    }

    public void saveStudentCourse(StudentCourse studentCourse) {
        studentCourseRepository.save(studentCourse);
    }

    @Transactional
    public void deleteStudentCourse(StudentCourse studentCourse) {
        studentCourseRepository.deleteStudentCourseById(studentCourse.getId());
    }
}
