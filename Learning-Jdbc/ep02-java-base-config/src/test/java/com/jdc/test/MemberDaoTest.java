package com.jdc.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.jdbc.config.ApplicationConfig;
import com.jdc.jdbc.dao.MemberDao;
import com.jdc.jdbc.dto.Member;

@SpringJUnitConfig(classes = ApplicationConfig.class)
public class MemberDaoTest {
	
	@Autowired
	private MemberDao dao;
	
	@Test
	@Sql(scripts = "/database.sql")
	void testCreate() {
		Member m = new Member();
		m.setLoginId("admin");
		m.setPassword("sysAdmin");
		m.setName("Admin");
		
		dao.create(m);
		
	}

}
