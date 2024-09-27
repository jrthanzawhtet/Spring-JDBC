package com.jdc.test;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.jdbc.dao.mapper.MemberRowMapper;
import com.jdc.jdbc.dto.Member;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(locations = "/application.xml")
public class PrepareStatementTest {

	@Autowired
	private JdbcOperations jdbc;
	
	@Autowired
	private MemberRowMapper rowMapper;

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
	@Order(2)
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
	@Order(3)
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
	@Order(4)
	void test4(@Qualifier("memberInsert") PreparedStatementCreatorFactory factory) {
		
		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(
				"amdin2", "admin2", "Admin User2" ,"092323" , "test@gamil.com"
				));
		
		var count = jdbc.execute(creator, PreparedStatement::executeUpdate);
		Assertions.assertEquals(1,count);
	}
	
	@Test
	@DisplayName("5 Execute With Creator for Select Statement")
	@Order(5)
	void test5(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var result = jdbc.execute(factory.newPreparedStatementCreator(List.of("Admin%")), stmt -> {
			List<Member> list = new ArrayList<>();
			var rs = stmt.executeQuery();
			while(rs.next()) {
				var m = new Member();
				m.setLoginId(rs.getString(1));
				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
				m.setPhone(rs.getString(4));
				m.setEmail(rs.getString(5));
				list.add(m);
			}
			return list;
		});
		Assertions.assertEquals(2,result.size());
	}
	
	@Test
	@DisplayName("6 Execute With RowMapper")
	@Order(6)
	void test6(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		
		RowMapper<Member> rowMapper = (rs, index) -> {
			var m = new Member();
			m.setLoginId(rs.getString(1));
			m.setPassword(rs.getString(2));
			m.setName(rs.getString(3));
			m.setPhone(rs.getString(4));
			m.setEmail(rs.getString(5));
			return m;
		};
		
	}
	
	@Test
	@DisplayName("7 Execute With RowMapper")
	@Order(7)
	void test7(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var result = jdbc.query(factory.newPreparedStatementCreator(List.of("Admin%")),rowMapper);
		assertEquals(2, result.size());
		
	}
	
	@Test
	@DisplayName("8 Execute With RowMapper")
	@Order(8)
	void test8(@Qualifier("memberFindByPk") PreparedStatementCreatorFactory factory) {
		var result = jdbc.query(factory.newPreparedStatementCreator(List.of("network")),rs -> {
			while(rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
			return null;
		});
		assertNotNull(result);
		assertEquals("networkAdmin", result.getName());
		
	}

}
