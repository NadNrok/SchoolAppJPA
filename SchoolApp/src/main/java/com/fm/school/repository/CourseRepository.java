package com.fm.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fm.school.model.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	Course findByCourseName(String courseName);

	@Query("SELECT COUNT(c) FROM Course c")
	Long getCount();


	@Query("SELECT c.courseId FROM Course c")
	List<Integer> getCourseIds();
}
