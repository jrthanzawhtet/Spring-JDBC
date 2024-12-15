package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.entity.State;
import com.jdc.spring.repo.StateRepo;

@Service
public class StateService {
	
	@Autowired
	private StateRepo stateRepo;
	
	 
	public List<State> searchWithNullHandler(String name,String region){
		var probe = new State(convert(0),name,null,new Region(null,region,null));
		
		return stateRepo.findAll(Example.of(
				probe,
				ExampleMatcher
				.matching()
				/*
				 * Null Handler
				 */
				//.withNullHandler(NullHandler.IGNORE)
				//.withIgnoreNullValues()
				//.withIncludeNullValues()
				));
	}
	
	public List<State> searchWithIgnoreCase(String name,String region){
		var probe = new State(convert(0),name,null,new Region(null,region,null));
		
		return stateRepo.findAll(
				Example.of(probe),
				ExampleMatcher.matching()
				.withIgnoreCase(false)
				);
	}
	
	public List<State> searchWithStringMatcher(String name,String region){
		return null;
	}

	
	private int convert(Integer id) {
		return null==id?0:id;
	}
	
	
	

}
