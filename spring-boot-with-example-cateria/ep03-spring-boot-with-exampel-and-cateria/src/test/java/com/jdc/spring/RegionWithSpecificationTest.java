package com.jdc.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdc.spring.service.RegionServiceWithSpecification;

@SpringBootTest
public class RegionWithSpecificationTest {

	@Autowired
	private RegionServiceWithSpecification regionService;
	
	@Test
	void selectRegionByStateNameLikeTest() {
		var list = regionService.selectRegionByStateNameLike("k");
		System.out.println(list);
	}
	
	@Test
	void deleteRegionByStateNameLikeTest() {
		var res = regionService.deleteRegionByStateNameLike("North");
		System.out.println(res);
	}
}
