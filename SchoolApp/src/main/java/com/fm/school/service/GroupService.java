package com.fm.school.service;

import com.fm.school.model.Group;
import com.fm.school.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findGroupsWithLessOrEqualStudents(int maxStudents) {
        return groupRepository.findGroupsWithLessOrEqualStudents(maxStudents);
    }

    public Group findGroupByName(String groupName) {
        return groupRepository.findGroupByGroupName(groupName);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
