package com.jdc.spring.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.jdc.spring.entity.Region;

@Repository
public interface RegionRepo extends JpaRepositoryImplementation<Region, Integer> {

	long deleteByName(String name);
}
