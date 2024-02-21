package com.fm.school.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fm.school.model.Course;
import com.fm.school.model.Student;
import com.fm.school.model.StudentCourse;
import com.fm.school.repository.CourseRepository;
import com.fm.school.repository.StudentCourseRepository;
import com.fm.school.repository.StudentRepository;
import com.fm.school.service.StudentCourseService;

public class StudentCourseServiceTest {

    @Mock
    private StudentCourseRepository studentCourseRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentCourseService studentCourseService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindStudentsByCourseName() {
        Course course = new Course(1, "Math", "Math course");
        Student student1 = new Student(1, "John", "Doe");
        Student student2 = new Student(2, "Jane", "Smith");
        List<StudentCourse> studentCourses = new ArrayList<>();
        studentCourses.add(new StudentCourse(1, 1));
        studentCourses.add(new StudentCourse(2, 1));
        when(courseRepository.findByCourseName("Math")).thenReturn(course);
        when(studentCourseRepository.findByCourse(course)).thenReturn(studentCourses);

        List<Student> result = studentCourseService.findStudentsByCourseName("Math");

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    public void testSaveStudentCourse() {
        StudentCourse studentCourse = new StudentCourse(1, 1);
        studentCourseService.saveStudentCourse(studentCourse);
        verify(studentCourseRepository, times(1)).save(studentCourse);
    }

    @Test
    public void testDeleteStudentCourse() {
        StudentCourse studentCourse = new StudentCourse(1, 1);
        studentCourseService.deleteStudentCourse(studentCourse);
        verify(studentCourseRepository, times(1)).deleteStudentCourseById(studentCourse.getId());
    }
}

