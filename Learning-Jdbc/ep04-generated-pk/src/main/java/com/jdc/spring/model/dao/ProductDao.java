package com.jdc.spring.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jdc.spring.model.dto.Product;

@Repository
public class ProductDao {
	
	@Autowired
	private NamedParameterJdbcOperations jdbc;
	
	@Value("${dao.product.create}")
	private String create;
	
	public int create(Product product) {
		var keys = new GeneratedKeyHolder();
		var params = new MapSqlParameterSource();
		params.addValue("name", product.getName());
		params.addValue("categoryId", product.getCateogry().getId());
		params.addValue("price", product.getPrice());
		jdbc.update(create, params, keys);
		return keys.getKey().intValue();
	}

}
