package com.jdc.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.spring.entity.Region;

public interface RegionRepo extends JpaRepository<Region, Integer> {
	
	

}
