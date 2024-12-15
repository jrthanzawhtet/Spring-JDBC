package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.repo.RegionRepo;

@Service
public class RegionService {
	
	@Autowired
	private RegionRepo regionRepo;

	
	public List<Region> findByRegionName(String name){
		var probe = new Region();
		probe.setName(name);
		var matcher = ExampleMatcher.matching()
				.withStringMatcher(StringMatcher.STARTING);
		
		var example = Example.of(probe,matcher);
		return regionRepo.findAll(example);
	}
}
