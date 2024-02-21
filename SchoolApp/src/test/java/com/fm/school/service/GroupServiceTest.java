package com.fm.school.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fm.school.model.Group;
import com.fm.school.repository.GroupRepository;
import com.fm.school.service.GroupService;

public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindGroupsWithLessOrEqualStudents() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "Group1"));
        groups.add(new Group(2, "Group2"));
        when(groupRepository.findGroupsWithLessOrEqualStudents(10)).thenReturn(groups);

        List<Group> result = groupService.findGroupsWithLessOrEqualStudents(10);

        assertEquals(2, result.size());
        assertEquals("Group1", result.get(0).getGroupName());
        assertEquals("Group2", result.get(1).getGroupName());
    }

    @Test
    public void testFindGroupByName() {
        Group group = new Group(1, "Group1");
        when(groupRepository.findGroupByGroupName("Group1")).thenReturn(group);

        Group result = groupService.findGroupByName("Group1");

        assertNotNull(result);
        assertEquals("Group1", result.getGroupName());
    }

    @Test
    public void testGetAllGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "Group1"));
        groups.add(new Group(2, "Group2"));
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> result = groupService.getAllGroups();

        assertEquals(2, result.size());
        assertEquals("Group1", result.get(0).getGroupName());
        assertEquals("Group2", result.get(1).getGroupName());
    }
}
