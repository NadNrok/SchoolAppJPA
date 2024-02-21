package com.fm.school.dao;

import com.fm.school.dao.impl.CourseDAOImpl;
import com.fm.school.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseDAOImplTest {

    @Autowired
    private CourseDAOImpl courseDAO;

    @Test
    @Sql(statements = {
            "INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')",
            "INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics', 'Physics course')" })
    public void testGetAllCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        assertThat(courses).isNotNull();
        assertThat(courses).hasSize(2);
    }

    @Test
    @Sql(statements = "INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')")
    public void testGetCourseById() {
        Course course = courseDAO.getCourseById(1);
        assertThat(course).isNotNull();
        assertThat(course.getCourseName()).isEqualTo("Math");
        assertThat(course.getCourseDescription()).isEqualTo("Mathematics course");
    }

    @Test
    public void testAddCourse() {
        Course course = new Course();
        course.setCourseName("Chemistry");
        course.setCourseDescription("Chemistry course");
        courseDAO.addCourse(course);

        Course retrievedCourse = courseDAO.getCourseByName("Chemistry");

        assertThat(retrievedCourse).isNotNull();
        assertThat(retrievedCourse.getCourseName()).isEqualTo("Chemistry");
        assertThat(retrievedCourse.getCourseDescription()).isEqualTo("Chemistry course");
    }

    @Test
    public void testUpdateCourse() {
        Course updatedCourse = new Course(1, "Mathematics", "Mathematics course updated");
        courseDAO.updateCourse(updatedCourse);

        Course retrievedCourse = courseDAO.getCourseById(1);

        assertThat(retrievedCourse).isNotNull();
        assertThat(retrievedCourse.getCourseName()).isEqualTo("Mathematics");
        assertThat(retrievedCourse.getCourseDescription()).isEqualTo("Mathematics course updated");
    }

    @Test
    public void testDeleteCourse() {
        courseDAO.deleteCourse(1);

        Course deletedCourse = courseDAO.getCourseById(1);

        assertThat(deletedCourse).isNull();
    }

    @Test
    @Sql(statements = {
            "INSERT INTO courses (course_id, course_name, course_description) VALUES (1, 'Math', 'Mathematics course')",
            "INSERT INTO courses (course_id, course_name, course_description) VALUES (2, 'Physics', 'Physics course')" })
    public void testGetCourseByName() {
        Course course = courseDAO.getCourseByName("Math");
        assertThat(course).isNotNull();
        assertThat(course.getCourseId()).isEqualTo(1);
    }
}
