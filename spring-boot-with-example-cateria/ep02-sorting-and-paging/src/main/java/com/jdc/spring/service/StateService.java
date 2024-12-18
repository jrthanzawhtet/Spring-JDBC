package com.jdc.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
