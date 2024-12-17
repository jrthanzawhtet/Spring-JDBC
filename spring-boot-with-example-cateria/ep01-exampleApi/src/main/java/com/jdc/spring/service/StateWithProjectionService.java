package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.Region;
import com.jdc.spring.entity.State;
import com.jdc.spring.repo.StateRepo;
import com.jdc.spring.repo.dto.StateDto;

@Service
public class StateWithProjectionService {
	
	@Autowired
	private StateRepo repo;

	public List<StateDto> selecteWithProjection(String name,String region){
		var probe = new State(convert(0),name,null,new Region(null,region,null));
		return repo.findBy(
				Example.of(
						probe,ExampleMatcher.matching()
						.withIgnorePaths("id","region")
						.withMatcher("name", GenericPropertyMatcher.of(StringMatcher.CONTAINING))), 
				dto -> dto
				.project("name","capital","region")
				.as(StateDto.class)
				.sortBy(Sort.by("name")).all());
	}
	
	private int convert(Integer id) {
		return null == id?0:id;
	}
}
