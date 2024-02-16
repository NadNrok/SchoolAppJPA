package com.fm.school.dao;

import com.fm.school.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GroupDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Group> findAllGroupsWithLessOrEqualStudents(int numberOfStudents) {
		return jdbcTemplate.query("SELECT g.group_id, g.group_name, COUNT(s.student_id) AS num_students "
				+ "FROM groups g LEFT JOIN students s ON g.group_id = s.group_id " + "GROUP BY g.group_id "
				+ "HAVING COUNT(s.student_id) <= ?", new Object[] { numberOfStudents }, (resultSet, rowNum) -> {
					Group group = new Group();
					group.setGroupId(resultSet.getInt("group_id"));
					group.setGroupName(resultSet.getString("group_name"));
					return group;
				});
	}

	@Override
	public List<Group> getAllGroups() {
		return jdbcTemplate.query("SELECT * FROM groups", new BeanPropertyRowMapper<>(Group.class));
	}

	@SuppressWarnings("deprecation")
	@Override
	public Group getGroupById(int groupId) {
		return jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_id = ?", new Object[] { groupId },
				new BeanPropertyRowMapper<>(Group.class));
	}

	@Override
	public void addGroup(Group group) {
		jdbcTemplate.update("INSERT INTO groups (group_name) VALUES (?)", group.getGroupName());
	}

	@Override
	public void updateGroup(Group group) {
		jdbcTemplate.update("UPDATE groups SET group_name = ? WHERE group_id = ?", group.getGroupName(),
				group.getGroupId());
	}

	@Override
	public void deleteGroup(int groupId) {
		jdbcTemplate.update("DELETE FROM groups WHERE group_id = ?", groupId);
	}
}
