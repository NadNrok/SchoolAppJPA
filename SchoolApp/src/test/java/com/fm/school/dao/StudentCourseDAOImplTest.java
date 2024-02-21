package com.fm.school.dao;

import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentCourseDAOImplTest {

    @Autowired
    private StudentCourseRepository studentCourseDAO;

    @Test
    @Sql(statements = { "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)",
            "INSERT INTO student_courses (student_id, course_id) VALUES (1, 2)" })
    public void testGetStudentEnrolledCourses() {
        List<Course> courses = studentCourseDAO.getStudentEnrolledCourses(1);
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);
    }

    @Test
    @Sql(statements = "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)")
    public void testIsStudentEnrolledInCourse() {
        boolean enrolled = studentCourseDAO.isStudentEnrolledInCourse(1, 1);
        assertThat(enrolled).isTrue();
    }

    @Test
    @Sql(statements = { "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)",
            "INSERT INTO student_courses (student_id, course_id) VALUES (2, 2)" })
    public void testGetAllStudentCourses() {
        List<StudentCourse> studentCourses = studentCourseDAO.getAllStudentCourses();
        assertThat(studentCourses).isNotNull();
        assertThat(studentCourses).hasSize(2);
    }

    @Test
    @Sql(statements = { "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)",
            "INSERT INTO student_courses (student_id, course_id) VALUES (1, 2)" })
    public void testGetStudentCoursesByStudentId() {
        List<StudentCourse> studentCourses = studentCourseDAO.getStudentCoursesByStudentId(1);
        assertThat(studentCourses).isNotNull();
        assertThat(studentCourses).hasSize(2);
    }

    @Test
    @Sql(statements = { "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)",
            "INSERT INTO student_courses (student_id, course_id) VALUES (2, 1)" })
    public void testGetStudentCoursesByCourseId() {
        List<StudentCourse> studentCourses = studentCourseDAO.getStudentCoursesByCourseId(1);
        assertThat(studentCourses).isNotNull();
        assertThat(studentCourses).hasSize(2);
    }

    @Test
    public void testAddStudentCourse() {
        StudentCourse newStudentCourse = new StudentCourse(1, 3);
        studentCourseDAO.addStudentCourse(newStudentCourse);

        List<StudentCourse> studentCourses = studentCourseDAO.getStudentCoursesByCourseId(3);

        assertThat(studentCourses).isNotNull();
        assertThat(studentCourses).hasSize(1);
    }

    @Test
    @Sql(statements = "INSERT INTO student_courses (student_id, course_id) VALUES (1, 1)")
    public void testDeleteStudentCourse() {
        studentCourseDAO.deleteStudentCourse(1, 1);

        List<StudentCourse> studentCourses = studentCourseDAO.getAllStudentCourses();

        assertThat(studentCourses).isEmpty();
    }
}
