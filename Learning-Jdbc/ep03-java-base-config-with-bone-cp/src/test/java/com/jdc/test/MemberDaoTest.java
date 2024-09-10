package com.jdc.test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.jdbc.dao.MemberDao;
import com.jdc.jdbc.dto.Member;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(locations = "/application.xml")
public class MemberDaoTest {

	@Autowired
	private MemberDao dao;

	@Autowired
	private JdbcOperations dbOperations;

	@Test
	@Sql(scripts = "/database.sql")
	@Order(1)
	void testCreate() {
		Member m = new Member();
		m.setLoginId("admin");
		m.setPassword("sysAdmin");
		m.setName("Admin");

		dao.create(m);

	}

	@Test
	@Order(2)
	void testUsingConnection() {
		var data = dbOperations.execute((Connection conn) -> {
			var stmt = conn.createStatement();
			var rs = stmt.executeQuery("select count(*) from MEMBER");
			while (rs.next()) {
				return rs.getLong(1);
			}
			return 0;
		});

		assertEquals(2L, data);
	}

	@Test
	@Order(3)
	void testUsingStatement() {
		var data = dbOperations.execute((Statement stmt) -> {
			return stmt.executeUpdate("""
					insert into MEMBER(loginId,password,name) values
					('root', 'root' , 'sysroot')
					""");
		});
		assertEquals(2, data);
	}

	@Test
	@Order(4)
	void testStaticQueryExtractor() {
		var result = dbOperations.query("select count(*) from MEMBER", rs -> {
			while (rs.next()) {
				rs.getInt(1);
			}
			return 0;
		});
		assertEquals(2, result);

		var list = dbOperations.query("select * from MEMBER", rs -> {
			var data = new ArrayList<Member>();
			while (rs.next()) {
				var m = new Member();
				m.setLoginId(rs.getString(1));
				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
				m.setPhone(rs.getString(4));
				m.setEmail(rs.getString(5));
				data.add(m);
			}
			return data;
		});
		assertEquals(2L, list.size());
	}

	@Test
	@Order(5)
	void testStaticQueryRollCallbackHaldler() {
		var list = new ArrayList<Member>();

		dbOperations.query("select * from MEMBER ", rs -> {
			var m = new Member();
			m.setLoginId(rs.getString(1));
			m.setPassword(rs.getString(2));
			m.setName(rs.getString(3));
			m.setPhone(rs.getString(4));
			m.setEmail(rs.getString(5));
			list.add(m);
		});
		assertEquals(2, list.size());
	}
	
	@Test
	@Order(6)
	void testStaticQueryRollMapper() {
	    var list = dbOperations.query("select * from MEMBER", (rs, no) -> {
	        var m = new Member();
	        m.setLoginId(rs.getString(1));
	        m.setPassword(rs.getString(2));
	        m.setName(rs.getString(3));
	        m.setPhone(rs.getString(4));
	        m.setEmail(rs.getString(5));
	        return m; 
	    });

	    assertEquals(2, list.size());
	}
	
	@Test
	@Order(7)
	void testStaticQueryOneResultWithClass() {
		var count = dbOperations.queryForObject("select count(*) from MEMBER ", Long.class);
		assertEquals(2L, count);
		
		var m = dbOperations.queryForObject("select name from MEMBER  where loginId = 'admin'", String.class);
		assertEquals("Admin", m);
	}

}
