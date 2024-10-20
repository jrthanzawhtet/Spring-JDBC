package com.jdc.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdc.demo.repo.DeatilsRepository;
import com.jdc.demo.repo.HeaderRepository;

@Service
public class PrepagationService {
	
	private HeaderRepository headerRepository;
	
	private DeatilsRepository detailsRepository;
	
	public void setHeaderRepository(HeaderRepository headerRepository) {
		this.headerRepository = headerRepository;
	}
	
	public void setDetailsRepository(DeatilsRepository detailsRepository) {
		this.detailsRepository = detailsRepository;
	}
	
	@Transactional
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
