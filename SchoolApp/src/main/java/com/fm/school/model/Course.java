package com.fm.school.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courseId;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_description")
	private String courseDescription;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<StudentCourse> students = new ArrayList<>();

	public Course(int courseId, String courseName, String courseDescription) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	public Course(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseId=" + courseId +
				", courseName='" + courseName + '\'' +
				", courseDescription='" + courseDescription + '\'' +
				", students=" + students +
				'}';
	}
}