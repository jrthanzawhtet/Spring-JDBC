package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region_;
import com.jdc.spring.entity.State;
import com.jdc.spring.entity.State_;
import com.jdc.spring.repo.StateRepo;

@Service
public class StateServiceSpecification {
	
	@Autowired
	private StateRepo repo;
	
	
	public List<State> selectStateByRegionNameLike(String name){
		Specification<State> spec = (root, query, cb) -> 
		cb.like(cb.lower(root.get(State_.region).get(Region_.name)),
				name.toLowerCase().concat("%"));
		
		return repo.findAll(spec);
	}	
	
	public List<State> selectStateByRegionNameWithJoin(String name){
		Specification<State> spec = (root, query, cb) -> {
			var join = root.join(State_.region);
			return cb.equal(join.get(Region_.name), name);
		};
		return repo.findAll(spec);
	}

	public long deleteByStateName(String name) {
		return repo.delete((root, query, cb) -> cb.equal(root.get(State_.name), name));
	}
	
}
