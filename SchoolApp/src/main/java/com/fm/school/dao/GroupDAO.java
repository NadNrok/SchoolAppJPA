package com.fm.school.dao;

import com.fm.school.model.Group;

import java.util.List;

public interface GroupDAO {
	List<Group> getAllGroups();

	Group getGroupById(int groupId);

	void addGroup(Group group);

	void updateGroup(Group group);

	void deleteGroup(int groupId);

	List<Group> findAllGroupsWithLessOrEqualStudents(int numberOfStudents);
}
