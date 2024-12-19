package com.jdc.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.jdc.spring.service.StateService;

@SpringBootTest
public class PagnationTest {

	@Autowired
	StateService service;
	
	
	@Test
	void PagnationTestWithQueryMethod() {
		
		PageRequest req = PageRequest.of(0, 3,Sort.by("name"));
		var page = service.findByRegionNameLikeWithPagnation("North".concat("%"), req);
		System.out.println("Null of element :"+ page.getNumberOfElements());
		System.out.println("Total element :"+ page.getTotalElements());
		System.out.println("Number :"+ page.getNumber());
		System.out.println("Total pages :"+ page.getTotalPages());
		
		for(var state :page.toList()) {
			System.out.println("Name :" + state.getName());
		}
	}
}
