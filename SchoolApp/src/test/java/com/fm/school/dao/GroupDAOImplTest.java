package com.fm.school.dao;

import com.fm.school.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupDAOImplTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private GroupDAO groupDAO;

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

	@SuppressWarnings("deprecation")
	@Test
	public void testAddGroup() {
		Group newGroup = new Group();
		newGroup.setGroupName("Group B");
		groupDAO.addGroup(newGroup);

		Group retrievedGroup = jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_name = ?",
				new Object[] { "Group B" }, (rs, rowNum) -> {
					Group g = new Group();
					g.setGroupId(rs.getInt("group_id"));
					g.setGroupName(rs.getString("group_name"));
					return g;
				});

		assertThat(retrievedGroup).isNotNull();
		assertThat(retrievedGroup.getGroupName()).isEqualTo("Group B");
	}

	@SuppressWarnings("deprecation")
	@Test
	@Sql(statements = "INSERT INTO groups (group_id, group_name) VALUES (1, 'Group A')")
	public void testUpdateGroup() {
		Group updatedGroup = new Group();
		updatedGroup.setGroupId(1);
		updatedGroup.setGroupName("Updated Group A");
		groupDAO.updateGroup(updatedGroup);

		Group retrievedGroup = jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_id = ?",
				new Object[] { 1 }, (rs, rowNum) -> {
					Group g = new Group();
					g.setGroupId(rs.getInt("group_id"));
					g.setGroupName(rs.getString("group_name"));
					return g;
				});

		assertThat(retrievedGroup).isNotNull();
		assertThat(retrievedGroup.getGroupName()).isEqualTo("Updated Group A");
	}

	@SuppressWarnings("deprecation")
	@Test
	@Sql(statements = "INSERT INTO groups (group_id, group_name) VALUES (1, 'Group A')")
	public void testDeleteGroup() {
		groupDAO.deleteGroup(1);

		Group deletedGroup = jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_id = ?", new Object[] { 1 },
				(rs, rowNum) -> {
					Group g = new Group();
					g.setGroupId(rs.getInt("group_id"));
					g.setGroupName(rs.getString("group_name"));
					return g;
				});

		assertThat(deletedGroup).isNull();
	}
}
