package com.jdc.test;

import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(locations = "/application.xml")
public class PrepareStatementTest {

	@Autowired
	private JdbcOperations jdbc;

	@Test
	@DisplayName("1. Execute with Creator")
	@Order(1)
	@Sql(scripts = "/database.sql")
	void test1(@Value("${member.insert}") String sql) {
		PreparedStatementCreator creator = conn -> {
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, "network");
			stmt.setString(2, "network");
			stmt.setString(3, "networkAdmin");
			stmt.setString(4, "0923231999");
			stmt.setString(5, "network@gmail.com");
			return stmt;
		};
		// var count = jdbc.execute(creator ,stmt -> stmt.executeUpdate());
		var count = jdbc.execute(creator, PreparedStatement::execute);
	}

	@Test
	@DisplayName("2. Execute with Creator")
	void test2(@Value("${member.insert}") String sql) {
		var count = jdbc.execute((Connection conn) -> {
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, "cloud");
			stmt.setString(2, "clo");
			stmt.setString(3, "clo");
			stmt.setString(4, "094556678");
			stmt.setString(5, "cloud@gmail.com");
			return stmt;
		},PreparedStatement::execute);
	}
	
	@Test
	@DisplayName("3. Execute with Creator")
	void test3(@Value("${member.insert}") String sql) {
		var factory = new PreparedStatementCreatorFactory(sql, new int[] {
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
		}); 
		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(
				"amdin1", "admin1", "Admin User" ,"092323" , "test@gamil.com"
				));
		PreparedStatementCallback<Integer> callback = PreparedStatement::executeUpdate;
		var count = jdbc.execute(creator, callback);
		Assertions.assertEquals(1,count);
	}
	
	@Test
	@DisplayName("4. Execute with CreatorFactory")
	void test4(@Qualifier("memberInsert") PreparedStatementCreatorFactory factory) {
		
		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(
				"amdin2", "admin2", "Admin User2" ,"092323" , "test@gamil.com"
				));
		
		var count = jdbc.execute(creator, PreparedStatement::executeUpdate);
		Assertions.assertEquals(1,count);
	}

}
