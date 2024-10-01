package com.jdc.spring.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.spring.model.dto.Category;

@SpringJUnitConfig(locations = "/application.xml")
@TestMethodOrder(OrderAnnotation.class)
public class CategoryDaoTest {
	
	@Autowired
	private JdbcOperations jdbc;
	
	@Test
	@Order(1)
	@Disabled
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
	
	@Test
	@Order(2)
	void test2() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] {"Fruid"});
		params.add(new Object[] {"Milk"});
		params.add(new Object[] {"Electronic"});
		params.add(new Object[] {"Kitchen"});
		params.add(new Object[] {"Fancy"});
		params.add(new Object[] {"Beauty"});

		var count = jdbc.batchUpdate("INSERT INTO CATEGORY(name) VALUES(?)", params);
		Assertions.assertEquals(6, count.length);
	}
	
	@Test
	@Order(3)
	void test3() {
		var list = jdbc.query("SELECT * FROM CATEGORY", new BeanPropertyRowMapper<Category>(Category.class));
		System.out.println(list.toString());
	}

}
