package com.jdc.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DetailsRepository {
	
	private SimpleJdbcInsert jdbcInsert;
	
	public DetailsRepository(DataSource dataSource) {
		jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.setTableName("details_tbl");
		jdbcInsert.setGeneratedKeyName("id");
		jdbcInsert.setColumnNames(List.of("header_id", "name"));
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> create(int headerId,String ...names) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		for(var name: names) {
			var id = jdbcInsert.executeAndReturnKey(
					Map.of(
							"header_id", headerId,
							"name",name
							)).intValue();
			list.add(id);
			
		}
		return list;
	}

}
