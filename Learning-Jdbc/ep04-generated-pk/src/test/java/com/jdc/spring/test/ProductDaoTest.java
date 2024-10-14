package com.jdc.spring.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jdc.spring.model.dao.CategoryDao;
import com.jdc.spring.model.dao.ProductDao;
import com.jdc.spring.model.dto.Product;

@SpringJUnitConfig(locations = "/application.xml")
@TestMethodOrder(OrderAnnotation.class)
public class ProductDaoTest {

	@Autowired
	private ProductDao dao;

	@Autowired
	private CategoryDao categories;

	@Test
	@Order(1)
	@DisplayName("1. Create Product Test")
	@Sql(statements = { "insert into CATEGORY (name) values ('Milk')", 
			"insert into CATEGORY (name) values ('Dairy')" })
	void test1() {
		var category = categories.findById(1);
		var product = new Product();
		product.setCateogry(category);
		product.setName("De De");
		product.setPrice(8000);

		var id = dao.create(product);
		assertEquals(1, id);
	}

	@Test
	@Order(2)
	@DisplayName("2. Find Product By Id")
	void test2() {
		var product = dao.findById(1);
		assertNotNull(product);
		assertEquals("Milk", product.getName());
		assertEquals("Dairy", product.getCateogry().getName());
		assertEquals(8000, product.getPrice());
		assertNull(dao.findById(2));
	}

	@Test
	@Order(3)
	@DisplayName("3. Find Product By Category")
	void test3() {
		List<Product> list = dao.findByCategory(1);
		assertEquals(1, list.size());
		assertTrue(dao.findByCategory(2).isEmpty());
	}

	@Test
	@Order(4)
	@DisplayName("4. Search Range")
	void test4() {
		assertEquals(1, dao.search("Milk").size());
		assertEquals(1, dao.search("Dairy").size());
		assertTrue(dao.search("De Des").isEmpty());
	}

	@Test
	@Order(5)
	@DisplayName("5. Update Product")
	void test5() {
		var product = dao.findById(1);
		product.setName("Crown Milk");
		product.setPrice(10000);
		
		int count = dao.update(product);
		assertEquals(1, count);
		
		
		var other = dao.findById(1);
		assertEquals(product.getName(), other.getName());
		assertEquals(product.getPrice(), other.getPrice());

	}

	@Test
	@Order(6)
	@DisplayName("6. Delete Product")
	void test6() {
		int count = dao.delete(1);
		assertEquals(1, count);
		assertNotNull(dao.findByCategory(1));
	}

}
