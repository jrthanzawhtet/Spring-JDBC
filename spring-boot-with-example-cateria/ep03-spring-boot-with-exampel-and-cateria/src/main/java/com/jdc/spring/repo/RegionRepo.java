package com.jdc.spring.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.spring.entity.Region;

public interface RegionRepo extends JpaRepository<Region, Integer> {

	long deletebyName(Specification<Region> spec);

	long deleteStateByReionName(String name);

	List<Region> findAll(Specification<Region> sepc);
	
	

}
