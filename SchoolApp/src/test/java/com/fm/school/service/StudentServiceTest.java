package com.fm.school.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fm.school.model.Group;
import com.fm.school.model.Student;
import com.fm.school.repository.StudentRepository;
import com.fm.school.service.StudentService;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student(1, new Group(), "John", "Doe");
        studentService.saveStudent(student);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, new Group(), "John", "Doe"));
        students.add(new Student(2, new Group(), "Jane", "Smith"));
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student(1, new Group(), "John", "Doe");
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(1);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    public void testGetStudentById_NotFound() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        Student result = studentService.getStudentById(1);
        assertNull(result);
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(1, new Group(), "John", "Doe");
        studentService.deleteStudent(student);
        verify(studentRepository, times(1)).delete(student);
    }
}
