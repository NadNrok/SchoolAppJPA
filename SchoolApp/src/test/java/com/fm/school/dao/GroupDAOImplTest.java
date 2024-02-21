package com.fm.school.dao;

import com.fm.school.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupDAOImplTest {

    @Autowired
    private GroupRepository groupDAO;

    @Test
    @Sql(statements = "INSERT INTO groups (group_id, group_name) VALUES (1, 'Group A')")
    public void testGetAllGroups() {
        List<Group> groups = groupDAO.getAllGroups();
        assertThat(groups).isNotNull();
        assertThat(groups).hasSize(1);
        assertThat(groups.get(0).getGroupName()).isEqualTo("Group A");
    }

    @Test
    @Sql(statements = "INSERT INTO groups (group_id, group_name) VALUES (1, 'Group A')")
    public void testGetGroupById() {
        Group group = groupDAO.getGroupById(1);
        assertThat(group).isNotNull();
        assertThat(group.getGroupId()).isEqualTo(1);
        assertThat(group.getGroupName()).isEqualTo("Group A");
    }

    @Test
    public void testAddGroup() {
        Group newGroup = new Group();
        newGroup.setGroupName("Group B");
        groupDAO.addGroup(newGroup);

        Group retrievedGroup = groupDAO.getGroupById(4);

        assertThat(retrievedGroup).isNotNull();
        assertThat(retrievedGroup.getGroupName()).isEqualTo("Group B");
    }

    @Test
    public void testUpdateGroup() {
        Group updatedGroup = new Group();
        updatedGroup.setGroupId(1);
        updatedGroup.setGroupName("Updated Group A");
        groupDAO.updateGroup(updatedGroup);

        Group retrievedGroup = groupDAO.getGroupById(1);

        assertThat(retrievedGroup).isNotNull();
        assertThat(retrievedGroup.getGroupName()).isEqualTo("Updated Group A");
    }

    @Test
    public void testDeleteGroup() {
        groupDAO.deleteGroup(1);

        Group deletedGroup = groupDAO.getGroupById(1);

        assertThat(deletedGroup).isNull();
    }
}
