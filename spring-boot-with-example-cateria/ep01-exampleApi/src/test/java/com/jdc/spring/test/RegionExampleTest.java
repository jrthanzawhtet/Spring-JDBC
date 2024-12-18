package com.jdc.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdc.spring.service.RegionService;

@SpringBootTest
public class RegionExampleTest {
	
	@Autowired
	private RegionService regionService;
	
	@Test	
	void findWithRegionTest() {
		var list = regionService.findByRegionName("S");
		list.forEach(r -> System.out.println("Region :"+ r.getName()));
		
	}

	
}
