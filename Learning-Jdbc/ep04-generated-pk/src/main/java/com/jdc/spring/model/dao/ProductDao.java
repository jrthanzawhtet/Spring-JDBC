package com.jdc.spring.model.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jdc.spring.model.dto.Category;
import com.jdc.spring.model.dto.Product;

@Repository
public class ProductDao {
	
	@Autowired
	private NamedParameterJdbcOperations jdbc;
	
	@Value("${dao.product.create}")
	private String create;
	
	@Value("${dao.product.update}")
	private String update;
	
	@Value("${dao.product.delete}")
	private String delete;
	
	@Value("${dao.product.findById}")
	private String findById;
	
	@Value("${dao.product.findByCategoryId}")
	private String findByCategory;
	
	@Value("${dao.product.search}")
	private String search;
	
	private RowMapper<ProductDto> rowMapper;
	
	public ProductDao() {
		rowMapper = new BeanPropertyRowMapper<ProductDto>(ProductDto.class);
	}
	
	public int create(Product product) {
		var keys = new GeneratedKeyHolder();
		var params = new MapSqlParameterSource();
		params.addValue("name", product.getName());
		params.addValue("categoryId", product.getCategory().getId());
		params.addValue("price", product.getPrice());
		jdbc.update(create, params, keys);
		return keys.getKey().intValue();
	}

	public Product findById(int id) {
		var params = new HashMap<String, Object>();
		params.put("id", id);
		return jdbc.queryForStream(findById, params, rowMapper).findFirst().orElseGet(() -> null);
	}

	public List<Product> findByCategory(int categoryId) {
		var params = new HashMap<String, Object>();
		params.put("categoryID", categoryId);
		return jdbc.queryForStream(findByCategory, params, rowMapper).map(ProductDto::toProduct).toList();
	}

	public List<Product> search(String keyWord) {
		var params = new HashMap<String, Object>();
		params.put("keyword", keyWord.toLowerCase().concat("%"));
		return jdbc.queryForStream(search, params, rowMapper).map(ProductDto::toProduct).toList();
	}

	public int update(Product product) {
		var params = new MapSqlParameterSource();
		params.addValue("id", product.getId());
		params.addValue("name", product.getName());
		params.addValue("price", product.getPrice());
		return jdbc.update(update, params);
	}

	public int delete(int id) {
		var params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbc.update(delete, params);
	}
	
	public static class ProductDto extends Product{
		public void setCategoryId(int id) {
			if(null == getCategory()) {
				setCategory(new Category());
			}
			getCategory().setId(id);
		}
		public void setCategoryName(String name) {
			if(null == getCategory()) {
				setCategory(new Category());
			}
			getCategory().setName(name);
		}
		
		public Product toProduct() {
			return this;
		}
	}
	
	

}
