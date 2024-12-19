package com.jdc.spring.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.jdc.spring.entity.Region;
import com.jdc.spring.service.StateService;

@SpringBootTest
public class SortingTest {
	
	@Autowired
	private StateService service;
	
	@Test
	void testWithQueryMethod() {
		
		var list = service.findByStatementNameLikeWithQueryMethod("m".concat("%"));
		list.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n",s.getName(),s.getCapital()));		
	}
	
	@Test
	void testWithJpQlQuery() {
		var list = service.findByStateNameWithJpQl("m".concat("%"));
		list.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n", s.getName(),s.getCapital()));
	}
	
	@Test
	void testWithQueryMethodAndSort() {
		var list = service.findByStateNameLikeWithSort("m".concat("%"), Sort.by("name").descending());
		list.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n", s.getName(),s.getCapital()));
		
		var list2 = service.findByStateNameLikeWithSort("m".concat("%"), Sort.by(Direction.DESC, "name"));
		list2.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n", s.getName(),s.getCapital()));
		
		
		var list3 = service.findByStateNameLikeWithSort("m".concat("%"), Sort.by(Order.asc("name")));
		list3.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n", s.getName(),s.getCapital()));
		
		var  list4 = service.findByStateNameLikeWithSort("m".concat("%"), Sort.by(List.of(Order.asc("name"),Order.desc("capital"))));
		list4.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n", s.getName(),s.getCapital()));
		
		var list5 = service.findByStateNameLikeWithSort("m".concat("%"), Sort.sort(Region.class).descending());
		list5.forEach(s -> System.out.printf("Name :%10s\tCapital :%3s\n",s.getName(),s.getCapital()));


	}
	

}
