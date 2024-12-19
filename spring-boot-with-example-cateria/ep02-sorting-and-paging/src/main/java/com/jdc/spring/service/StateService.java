package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jdc.spring.entity.State;
import com.jdc.spring.repo.StateRepo;

@Service
public class StateService {
	
	@Autowired
	private StateRepo stateRepo;
	
	
	public List<State> findByStatementNameLikeWithQueryMethod(String name){
		return stateRepo.findByNameLikeOrderByCapital(name);
	}
	
	public List<State> findByStateNameWithJpQl(String name){
		return stateRepo.findbyStateNameLike(name);
	}
	
	public List<State> findByStateNameLikeWithSort(String name,Sort sort){
		return stateRepo.findByNameLike(name,sort);
	}
	
	public Page<State> findByRegionNameLikeWithPagnation(String name,Pageable page){
		return stateRepo.findByRegionNameLike(name,page);
	}
	
	public Page<State> findByRegionNameLikeWithPagnationAndJpql(String name,Pageable page){
		return stateRepo.findByStateByNameLike(name, page);
	}

}
