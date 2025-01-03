package com.jdc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.demo.repo.DetailsRepository;
import com.jdc.demo.repo.HeaderRepository;

@Service
public class PropagationService {
	
	@Autowired
	private HeaderRepository headerRepository;
	
	@Autowired
	private DetailsRepository detailsRepository;
	
	@Transactional(isolation = Isolation.SERIALIZABLE, timeout = 5)
	public Result save(int state,String header, String ...details) {
		
		var headerId = headerRepository.create(header);
		
		if(state == 1) {
			throw new RuntimeException();
		}
		
		var detailsId = detailsRepository.create(headerId, details);
		
		if(state == 2) {
			throw new RuntimeException();
		}
		return new Result(headerId, detailsId);
		
	}

}
