package com.fm.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fm.school.model.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query("SELECT g FROM Group g LEFT JOIN g.students s GROUP BY g HAVING COUNT(s) <= :maxStudents")
    List<Group> findGroupsWithLessOrEqualStudents(@Param("maxStudents") int maxStudents);
    Group findGroupByGroupName(String groupName);

    @Query("SELECT COUNT(g) FROM Group g")
    Long getCount();
}
