package com.fm.school.repository;

import java.util.List;

import com.fm.school.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fm.school.model.Course;
import com.fm.school.model.StudentCourse;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
	List<StudentCourse> findByCourse(Course course);

	@Query("SELECT COUNT(sc) FROM StudentCourse sc")
	Long getCount();

	@Query("SELECT COUNT(*) FROM StudentCourse sc WHERE sc.student = ?1 AND sc.course = ?2")
	Integer checkIfStudentCourseExists(@Param("student") Student student, @Param("course") Course course);

	@Modifying
	@Query("DELETE FROM StudentCourse sc WHERE sc.id = ?1")
	void deleteStudentCourseById(@Param("id") Integer id);
}
