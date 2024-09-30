package com.jdc.test;

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
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.jdbc.dto.Member;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(locations = "/application.xml")
public class PrepareStatementTest {

	@Autowired
	private JdbcOperations jdbc;

	@Autowired
	@Qualifier("memberRowMapper")
	private RowMapper<Member> rowMapper;

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
		}, PreparedStatement::execute);
	}

	@Test
	@DisplayName("3. Execute with Creator")
	@Order(3)
	void test3(@Value("${member.insert}") String sql) {
		var factory = new PreparedStatementCreatorFactory(sql,
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, });
		PreparedStatementCreator creator = factory
				.newPreparedStatementCreator(List.of("amdin1", "admin1", "Admin User", "092323", "test@gamil.com"));
		PreparedStatementCallback<Integer> callback = PreparedStatement::executeUpdate;
		var count = jdbc.execute(creator, callback);
		Assertions.assertEquals(1, count);
	}

	@Test
	@DisplayName("4. Execute with CreatorFactory")
	@Order(4)
	void test4(@Qualifier("memberInsert") PreparedStatementCreatorFactory factory) {

		PreparedStatementCreator creator = factory
				.newPreparedStatementCreator(List.of("amdin2", "admin2", "Admin User2", "092323", "test@gamil.com"));

		var count = jdbc.execute(creator, PreparedStatement::executeUpdate);
		Assertions.assertEquals(1, count);
	}

	@Test
	@DisplayName("5. Execute With Creator for Select Statement")
	@Order(5)
	void test5(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var result = jdbc.execute(factory.newPreparedStatementCreator(List.of("Admin%")), stmt -> {
			List<Member> list = new ArrayList<>();
			var rs = stmt.executeQuery();
			while (rs.next()) {
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
		Assertions.assertEquals(2, result.size());
	}

	@Test
	@DisplayName("6. Execute With RowMapper")
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
	@DisplayName("7. Execute With RowMapper")
	@Order(7)
	void test7(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var result = jdbc.query(factory.newPreparedStatementCreator(List.of("Admin%")), rowMapper);
		assertEquals(2, result.size());

	}

	@Test
	@DisplayName("8. Execute With RowMapper")
	@Order(8)
	void test8(@Qualifier("memberFindByPk") PreparedStatementCreatorFactory factory) {
		var result = jdbc.query(factory.newPreparedStatementCreator(List.of("network")), rs -> {
			while (rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
			return null;
		});
		assertNotNull(result);
		assertEquals("networkAdmin", result.getName());

	}

	@Test
	@DisplayName("9. Execute with Simple SQL String")
	@Order(9)
	@Sql(scripts = "/database.sql")
	void test9(@Value("${member.insert}") String sql) {

		var count = jdbc.execute(sql, (PreparedStatement stmt) -> {
			stmt.setString(1, "admin");
			stmt.setString(2, "admin");
			stmt.setString(3, "Admin User");
			stmt.setString(4, "0923231999");
			stmt.setString(5, "admin@gmail.com");
			return stmt.executeUpdate();
		});

		assertEquals(1, count);
	}

	@Test
	@DisplayName("10. Update with PreparedStatementSetter")
	@Order(10)
	@Sql(scripts = "/database.sql")
	void test10(@Value("${member.insert}") String sql) {

		var count = jdbc.update(sql, stmt -> {
			stmt.setString(1, "admin");
			stmt.setString(2, "admin");
			stmt.setString(3, "Admin User");
			stmt.setString(4, "0923231999");
			stmt.setString(5, "admin@gmail.com");
		});

		assertEquals(1, count);
	}

	@Test
	@DisplayName("11. Update with PreparedStatementSetter")
	@Order(11)
	@Sql(scripts = "/database.sql")
	void test11(@Value("${member.insert}") String sql) {
		int count = jdbc.update(sql, "admin", "admin", "Admin User", "092323499", "admin@gmail.com");

		assertEquals(1, count);
	}

	@Test
	@DisplayName("12. Query With PreparedStatementSetter")
	@Order(12)
	void test12(@Value("${member.select.by.name}") String sql) {
		var list = jdbc.query(sql, stmt -> stmt.setString(1, "Admin%"), rowMapper);
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("13. Query With PreparedStatementSetter")
	@Order(13)
	void test13(@Value("${member.select.by.name}") String sql) {
		var list = jdbc.query(sql, rowMapper, "Admin%");
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("14. Query With PreparedStatementSetter")
	@Order(14)
	void test14(@Value("${member.select.by.name}") String sql) {
		var list = jdbc.query(sql, new BeanPropertyRowMapper<>(Member.class), "Admin%");
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("15. Select One With PreparedStatementSetter")
	@Order(15)
	void test15(@Value("${member.select.by.pk}") String sql) {

		var data = jdbc.query(sql, stmt -> stmt.setString(1, "admin"), rs -> {

			while (rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
			return null;
		});
		assertNotNull(data);
	}

	@Test
	@DisplayName("16. Select One With PreparedStatementSetter")
	@Order(16)
	void test16(@Value("${member.select.by.pk}") String sql) {

		ResultSetExtractor<Member> extractor = rs -> {

			while (rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
			return null;
		};

		var data = jdbc.query(sql, extractor, "admin");

		assertNotNull(data);
	}

	@Test
	@DisplayName("17. Select One With Param")
	@Order(17)
	void test17(@Value("${member.select.by.pk}") String sql) {
		var data = jdbc.queryForObject(sql, rowMapper, "admin");
		assertNotNull(data);
		assertEquals("Admin User", data.getName());
	}

	@Test
	@DisplayName("18. Select One Single Data With Params")
	@Order(18)
	void test18() {
		var sql = "select count(*) from MEMBER where name like ? ";
		var count = jdbc.queryForObject(sql, Long.class, "Admin%");
		assertEquals(1, count);

	}

}
