package com.jdc.spring.test;

import static org.junit.jupiter.api.Assertions.*;
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
	@Sql(statements = { "insert into CATEGORY (name) values ('Milk')", "insert into CATEGORY (name) values ('Dairy')" })
	void test1() {
		var category = categories.findById(1);
		var product = new Product();
		product.setCateogry(category);
		product.setName("De De");
		product.setPrice(8000);

		var id = dao.create(product);
		assertEquals(1, id);
	}

}
