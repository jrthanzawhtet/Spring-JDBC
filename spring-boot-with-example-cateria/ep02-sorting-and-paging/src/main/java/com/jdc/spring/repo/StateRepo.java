package com.jdc.spring.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jdc.spring.entity.State;

public interface StateRepo extends JpaRepository<State, Integer> {

	List<State> findByNameLikeOrderByCapital(String name);

	@Query(value = "select s from State s where s.name like :name order by s.capital ")
	List<State> findbyStateNameLike(@Param("name") String name);

	Page<State> findByRegionNameLike(String name, Pageable page);
	
	List<State> findByNameLike(String name, Sort sort);


	Page<State> findByStateByNameLike(String name, Pageable page);

	@Query(value = "select s from State s where s.name like :name ",
			countQuery = "select count(s) from State s where s.name like :name")
	List<State> findByRegionNameLike(@Param("name") String name, Sort sort);


}
