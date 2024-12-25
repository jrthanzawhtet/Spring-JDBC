package com.jdc.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdc.spring.service.RegionServiceWithSpecification;
import com.jdc.spring.service.StateServiceWithCriteria;

@SpringBootTest
public class CriteriaTest {
	
	@Autowired
	private StateServiceWithCriteria stateService;
	
	@Autowired
	private RegionServiceWithSpecification regionService;
	
	@Test
	void selectStateByNameLikeTest() {
		
		var list = stateService.selectStateByNameLike("k");
		System.out.println(list);
		
	}
	
	@Test
	void selectByRegionNameLikeTest() {
		var list = stateService.selectStateByRegionNameLike("T");
		System.out.println(list);
	}
	
	@Test
	void selectRegionByStateNameLikeTest() {
		var list = regionService.selectRegionByStateNameLike("l");
		System.out.println(list);
	}

}
