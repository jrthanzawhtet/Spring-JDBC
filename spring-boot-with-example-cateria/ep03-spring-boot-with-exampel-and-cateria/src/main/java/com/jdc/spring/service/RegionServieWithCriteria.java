package com.jdc.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.entity.Region_;
import com.jdc.spring.entity.State;
import com.jdc.spring.entity.State_;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class RegionServieWithCriteria {
	
	@PersistenceContext
	EntityManager em;	
	
	/*
	 * select r from Region r where r.id in
	 * (Select s.region.id from State s join s.region where s.name like :name)
	 */
	
	public List<Region> selectRegionByStateNameLike(String name){
		
		var cb = em.getCriteriaBuilder();
		var regionquery = cb.createQuery(Region.class);
		var region = regionquery.from(Region.class);
		
		var regionIdQuery = regionquery.subquery(Integer.class);
		var state = regionIdQuery.from(State.class);
		
		var join = state.join(State_.region);
		regionIdQuery.select(join.get(Region_.id));
		
		regionIdQuery.where(cb.like(cb.lower(state.get(State_.name)), name.toLowerCase().concat("%")));
		
		var query = regionquery.where(region.get(Region_.id).in(regionIdQuery));
		return em.createQuery(query).getResultList();
		
	}
	
	

	
	
}
