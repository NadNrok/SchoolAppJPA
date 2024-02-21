package com.fm.school.service;

import com.fm.school.dao.GroupRepository;
import com.fm.school.model.Group;
import com.fm.school.service.impl.GroupServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GroupServiceImplTest {

	@Mock
	private GroupRepository groupDAO;

	@InjectMocks
	private GroupService groupService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindAllGroupsWithLessOrEqualStudents() {
		int numberOfStudents = 10;
		List<Group> groups = Arrays.asList(new Group(1, "Group1"), new Group(2, "Group2"));
		when(groupDAO.findAllGroupsWithLessOrEqualStudents(numberOfStudents)).thenReturn(groups);

		List<Group> result = groupService.findAllGroupsWithLessOrEqualStudents(numberOfStudents);

		assertEquals(groups.size(), result.size());
		assertEquals(groups.get(0), result.get(0));
		assertEquals(groups.get(1), result.get(1));
	}

	@Test
	public void testGetAllGroups() {
		List<Group> groups = Arrays.asList(new Group(1, "Group1"), new Group(2, "Group2"));
		when(groupDAO.getAllGroups()).thenReturn(groups);

		List<Group> result = groupService.getAllGroups();

		assertEquals(groups.size(), result.size());
		assertEquals(groups.get(0), result.get(0));
		assertEquals(groups.get(1), result.get(1));
	}

	@Test
	public void testGetGroupById() {
		int groupId = 1;
		Group group = new Group(groupId, "Group1");
		when(groupDAO.getGroupById(groupId)).thenReturn(group);

		Group result = groupService.getGroupById(groupId);

		assertEquals(group, result);
	}

	@Test
	public void testAddGroup() {
		Group group = new Group(1, "Group1");

		groupService.addGroup(group);

		verify(groupDAO, times(1)).addGroup(group);
	}

	@Test
	public void testUpdateGroup() {
		Group group = new Group(1, "Group1");

		groupService.updateGroup(group);

		verify(groupDAO, times(1)).updateGroup(group);
	}

	@Test
	public void testDeleteGroup() {
		int groupId = 1;

		groupService.deleteGroup(groupId);

		verify(groupDAO, times(1)).deleteGroup(groupId);
	}
}
