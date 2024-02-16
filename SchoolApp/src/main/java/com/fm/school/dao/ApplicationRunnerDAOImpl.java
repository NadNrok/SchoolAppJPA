package com.fm.school.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationRunnerDAOImpl implements ApplicationRunnerDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ApplicationRunnerDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean databaseHasData() {
        String countAllTablesQuery = "SELECT SUM(cnt) FROM (SELECT COUNT(*) AS cnt FROM students UNION ALL SELECT COUNT(*) AS cnt FROM courses UNION ALL SELECT COUNT(*) AS cnt FROM groups UNION ALL SELECT COUNT(*) AS cnt FROM student_courses) AS total";
        Integer totalCount = jdbcTemplate.queryForObject(countAllTablesQuery, Integer.class);
        return totalCount != null && totalCount > 0;
    }
}
