package com.fm.school.service;

import com.fm.school.dao.GroupDAO;
import com.fm.school.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

	private final GroupDAO groupDAO;

	@Autowired
	public GroupServiceImpl(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	@Override
	public List<Group> findAllGroupsWithLessOrEqualStudents(int numberOfStudents) {
		return groupDAO.findAllGroupsWithLessOrEqualStudents(numberOfStudents);
	}

	@Override
	public List<Group> getAllGroups() {
		return groupDAO.getAllGroups();
	}

	@Override
	public Group getGroupById(int groupId) {
		return groupDAO.getGroupById(groupId);
	}

	@Override
	public void addGroup(Group group) {
		groupDAO.addGroup(group);
	}

	@Override
	public void updateGroup(Group group) {
		groupDAO.updateGroup(group);
	}

	@Override
	public void deleteGroup(int groupId) {
		groupDAO.deleteGroup(groupId);
	}
}
