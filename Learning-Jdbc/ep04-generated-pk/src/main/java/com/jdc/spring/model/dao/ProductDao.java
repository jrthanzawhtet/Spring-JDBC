package com.jdc.spring.model.dao;

import java.util.List;

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

	public Product findById(int id) {
		return null;
	}

	public List<Product> findByCategory(int categoryId) {
		return null;
	}

	public List<Product> search(String keyWord) {
		return null;
	}

	public int update(Product product) {
		return 0;
	}

	public int delete(int i) {
		return 0;
	}

}
