package com.jdc.spring.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import com.jdc.spring.entity.State;

@Repository
public interface StateRepo extends JpaRepositoryImplementation<State, Integer>{
	
	long deleteStateByRegionName(String name);

}
