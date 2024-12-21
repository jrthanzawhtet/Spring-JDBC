package com.jdc.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdc.spring.entity.State;

public interface StateRepo extends JpaRepository<State, Integer>{

}
