package com.jdc.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.jdc.spring.service.StateService;
import com.jdc.spring.service.StateWithProjectionService;

@SpringBootTest
public class StateExample {
	
	@Autowired
	StateService service;
	
	@Autowired
	StateWithProjectionService stateProjService;
	
	@Test
	@Order(1)
	void testWithNullHandler() {
		var list  = service.searchWithNullHandler("Magway Region", "Central");
		list.forEach(s -> System.out.println("Name :"+ s.getName()));
	}
	
	@Test
	@Order(2)
	void testWithIgnoreCase() {
		var list = service.searchWithIgnoreCase("magway region", "Central");
		list.forEach( s-> System.out.println("Name :" + s.getName()));
	}
	
	@Test
	@Order(3)
	void testWithStringMatcher() {
		var list = service.searchWithStringMatcher("region", "Central");
		list.forEach(s -> System.out.println("Name :" + s.getName()));
	}
	
	@Test
	@Order(4)
	void testWithPrepertiesMatcher() {
		var list = service.searchWithPropteriesMatcher("n", "Central");
		list.forEach(s -> System.out.println("Name :" + s.getName()));
	}
	
	@Test
	@Order(5)
	void testWithProjectMatcer() {
		var list = stateProjService.selecteWithProjection("n", "Central");
		list.forEach(s -> System.out.printf("State  :%s\t%s\t%s\n",s.getName(),s.getCapital(),s.getRegion().getName()));
		
	}
	

}
