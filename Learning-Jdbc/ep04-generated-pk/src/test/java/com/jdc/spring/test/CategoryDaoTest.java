package com.jdc.spring.test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.spring.model.config.ApplicationConfig;
import com.jdc.spring.model.dao.CategoryDao;
import com.jdc.spring.model.dto.Category;

//@SpringJUnitConfig(locations = "/application.xml")
@SpringJUnitConfig(classes = ApplicationConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryDaoTest {

	@Autowired
	private JdbcOperations jdbc;

	@Autowired
	private CategoryDao dao;

	@Test
	@Order(1)
	@Disabled
	void test1() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] { 1, "Fruid" });
		params.add(new Object[] { 2, "Milk" });
		params.add(new Object[] { 3, "Electronic" });
		params.add(new Object[] { 4, "Kitchen" });
		params.add(new Object[] { 5, "Fancy" });
		params.add(new Object[] { 6, "Beauty" });

		var count = jdbc.batchUpdate("INSERT INTO CATEGORY VALUES(?,?)", params);
		assertEquals(6, count.length);
	}

	@Test
	@Order(2)
	@Disabled
	void test2() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] { "Fruid" });
		params.add(new Object[] { "Milk" });
		params.add(new Object[] { "Electronic" });
		params.add(new Object[] { "Kitchen" });
		params.add(new Object[] { "Fancy" });
		params.add(new Object[] { "Beauty" });

		var count = jdbc.batchUpdate("INSERT INTO CATEGORY(name) VALUES(?)", params);
		assertEquals(6, count.length);
	}

	@Test
	@Order(3)
	@Disabled
	void test3() {
		var list = jdbc.query("SELECT * FROM CATEGORY", new BeanPropertyRowMapper<Category>(Category.class));
		System.out.println(list.toString());
	}

	@Test
	@Order(4)
	@Disabled
	void test4() {
		var c = new Category();
		c.setName("Lemon");
		var id = dao.create(c);
		assertEquals(1, id);
	}

	@Test
	@Order(5)
	@Disabled
	void test5() {
		var c = new Category();
		c.setName("Lemon");
		var id = dao.createWithSimpleJdbc(c);
		assertEquals(1, id);
	}

	@Test
	@Order(6)
	@DisplayName("1. Crate Category")
	void test6() {
		var c = new Category();
		c.setName("Lemon");
		var id = dao.preCreateWithSimpleJdbcInsertInXML(c);
		assertEquals(1, id);
	}

	@Test
	@Order(7)
	@DisplayName("2. Update Category")
	void test7() {
		var c = new Category();
		c.setId(1);
		c.setName("Lemon Tea");

		int count = dao.update(c);
		assertEquals(1, count);
	}
	
	@Test
	@Order(8)
	@DisplayName("3. Find Category By ID")
	void test8() {
		Category c = dao.findById(1);
		assertEquals("Lemon Tea", c.getName());
	}
	
	@Test
	@Order(9)
	@DisplayName("4. Find Category By Name")
	void test9() {
		List<Category> list = dao.findByNameLike("Tea");
		assertEquals(1, list.size());
	}
	
	@Test
	@Order(10)
	@DisplayName("5. Find Count By Name Like ")
	void test10() {
		var count = dao.findCountByNameLike("Lemo");
		assertEquals(1, count);
	}
	

}
