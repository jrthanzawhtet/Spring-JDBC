package com.jdc.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.repo.RegionRepo;
import com.jdc.spring.repo.StateRepo;

@Service
public class RegionServiceWithSpecification {
	
	@Autowired
	private RegionRepo regionRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	
	public long deleteRegionByStateNameLike(String name) {
		
		//Specification<Region> spec = (root, query, cb) -> 
		//cb.equal(root.get(Region_.), root)
		return 1;
	}

}
