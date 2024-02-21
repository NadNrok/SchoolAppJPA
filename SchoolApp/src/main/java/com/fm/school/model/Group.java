package com.fm.school.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "groups")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;

	@Column(name = "group_name")
	private String groupName;

	@OneToMany(mappedBy = "group")
	private List<Student> students = new ArrayList<>();

	public Group(int groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public Group(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "Group{" + "groupId=" + groupId + ", groupName='" + groupName + '\'' + ", students=" + students + '}';
	}
}