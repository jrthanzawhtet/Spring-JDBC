package com.jdc.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdc.spring.service.StateServiceSpecification;

@SpringBootTest
public class SpecificationTest {
	
	@Autowired
	private StateServiceSpecification stateService;
	
	@Test
	void selectStateByRegionNameLike() {
		var list = stateService.selectStateByRegionNameLike("s");
		System.out.println(list);
	}
	
	@Test
	void selectStateByRegionName() {
		var list =  stateService.selectStateByRegionNameWithJoin("Nort West");
		System.out.println(list);
	}
	
	@Test
	void deleteByStateName() {
		var result = stateService.deleteByStateName("Magway Region");
		System.out.println(result);
	}

}
