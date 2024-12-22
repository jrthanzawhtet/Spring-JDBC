package com.jdc.spring.repo;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.spring.entity.State;

public interface StateRepo extends JpaRepository<State, Integer>{

	List<State> findAll(Specification<State> spec);


}
