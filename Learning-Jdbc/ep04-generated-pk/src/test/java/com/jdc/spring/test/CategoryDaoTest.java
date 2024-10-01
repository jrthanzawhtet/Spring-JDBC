package com.jdc.spring.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "/application.xml")
@TestMethodOrder(OrderAnnotation.class)
public class CategoryDaoTest {
	
	@Autowired
	private JdbcOperations jdbc;
	
	@Test
	void test1() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] {1,"Fruid"});
		params.add(new Object[] {2,"Milk"});
		params.add(new Object[] {3,"Electronic"});
		params.add(new Object[] {4,"Kitchen"});
		params.add(new Object[] {5,"Fancy"});
		params.add(new Object[] {6,"Beauty"});

		var count = jdbc.batchUpdate("INSERT INTO CATEGORY VALUES(?,?)", params);
		Assertions.assertEquals(6, count.length);
	}

}
