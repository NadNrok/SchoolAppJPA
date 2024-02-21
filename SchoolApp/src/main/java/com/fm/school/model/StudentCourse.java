package com.fm.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student_courses")
public class StudentCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	public StudentCourse(int studentId, int courseId) {
		this.student = new Student();
		this.student.setStudentId(studentId);
		this.course = new Course();
		this.course.setCourseId(courseId);
	}

	@Override
	public String toString() {
		return "StudentCourse{" +
				"id=" + id +
				", student=" + student +
				", course=" + course +
				'}';
	}
}