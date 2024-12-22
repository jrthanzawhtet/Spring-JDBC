package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.entity.Region_;
import com.jdc.spring.entity.State;
import com.jdc.spring.entity.State_;
import com.jdc.spring.repo.RegionRepo;
import com.jdc.spring.repo.StateRepo;

import jakarta.transaction.Transactional;

@Service
public class RegionServiceWithSpecification {
	
	@Autowired
	private RegionRepo regionRepo;
	
	@Autowired
	private StateRepo stateRepo;
	
	
	@Transactional
	public long deleteRegionByStateNameLike(String name) {
		
		Specification<Region> spec = (root, query, cb) -> 
		cb.equal(root.get(Region_.name), name);
		
		return regionRepo.deletebyName(spec);
	}
	
	public long deleteStateByRegionName(String name) {
		return regionRepo.deleteStateByReionName(name);
	}
	
	/*
	 * select r from Region r where r.id in
	 * (select s.region.id from State s join s.region where lower(s.name) like lower(:name))
	 */
	
	public List<Region> selectRegionByStateNameLike(String name){
		Specification<Region> sepc = (root, query, cb) -> {
			var subQuery = query.subquery(Integer.class);
			var subRoot = subQuery.from(State.class);
			
			var join = subRoot.join(State_.region);
			subQuery.select(join.get(Region_.id));
			subQuery.where(cb.like(cb.lower(subRoot.get(State_.name)), name.toLowerCase().concat("%")));
			
			return root.get(Region_.id).in(subQuery);
		};
		return regionRepo.findAll(sepc);
	}

}
