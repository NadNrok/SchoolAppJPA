package com.fm.school.service;

import com.fm.school.model.Group;

import java.util.List;

public interface GroupService {
	List<Group> getAllGroups();

	Group getGroupById(int groupId);

	void addGroup(Group group);

	void updateGroup(Group group);

	void deleteGroup(int groupId);

	List<Group> findAllGroupsWithLessOrEqualStudents(int numberOfStudents);
}
