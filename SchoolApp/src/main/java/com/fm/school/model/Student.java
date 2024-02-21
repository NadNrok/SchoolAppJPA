package com.fm.school.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<StudentCourse> courses = new ArrayList<>();

	public Student(int studentId, Group group, String firstName, String lastName) {
		this.studentId = studentId;
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int studentId, String firstName, String lastName) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(Group group, String firstName, String lastName) {
		this.group = group;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student() {
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<StudentCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<StudentCourse> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
	    return "Student{" + "studentId=" + studentId + ", group=" + group.getGroupName() + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", courses=" + courses + '}';
	}

}