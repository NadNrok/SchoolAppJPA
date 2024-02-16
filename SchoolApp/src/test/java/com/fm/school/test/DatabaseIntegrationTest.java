package com.fm.school.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseIntegrationTest {

	@Container
	private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("test").withUsername("test").withPassword("test");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void testDatabaseConnection() {
		String sql = "SELECT 1";
		Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
		assertThat(result).isEqualTo(1);
	}
}
