package com.jdc.spring.model.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jdc.spring.model.dto.Category;

@Repository
public class CategoryDao {

	@Autowired
	private JdbcOperations jdbc;

	@Autowired
	private JdbcTemplate template;

	@Value("${dml.category.insert}")
	private String insertSql;
	
	@Value("${dao.category.update}")
	private String update;
	@Value("${dao.category.findById}")
	private String findById;
	@Value("${dao.category.findByNameLike}")
	private String findByNameLike;
	@Value("${dao.category.findCountByNameLike}")
	private String findCountByNameLike;
	@Value("${dao.category.delete}")
	private String delete;

	@Autowired
	private SimpleJdbcInsert insert;
	
	
	private BeanPropertyRowMapper<Category> rowMapper;
	
	public CategoryDao() {
		rowMapper = new BeanPropertyRowMapper<>(Category.class);
	}
	

	public int create(Category category) {
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(insertSql, Types.VARCHAR);
		factory.setReturnGeneratedKeys(true);

		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(category.getName()));

		var keyHolder = new GeneratedKeyHolder();

		jdbc.update(creator, keyHolder);

		/*
		 * PreparedStatementCallback<Integer> callback = stmt -> { int affectedRows =
		 * stmt.executeUpdate(); if (affectedRows == 0) { throw new
		 * IllegalStateException("Insert failed, no rows affected."); }
		 * 
		 * var rs = stmt.getGeneratedKeys(); if (rs.next()) { return rs.getInt(1); }
		 * else { throw new
		 * IllegalStateException("Insert succeeded but no ID obtained."); } }; return
		 * jdbc.execute(creator, callback);
		 */
		return keyHolder.getKey().intValue();

	}

	public int createWithSimpleJdbc(Category category) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("CATEGORY");
		insert.setGeneratedKeyName("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("NAME", category.getName());
		return insert.executeAndReturnKey(params).intValue();
	}

	public int preCreateWithSimpleJdbcInsertInXML(Category category) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("NAME", category.getName());
		return insert.executeAndReturnKey(params).intValue();
	}

	public int update(Category c) {
		return insert.getJdbcTemplate().update(update, c.getName(), c.getId());
	}

	public Category findById(int id) {
		return insert.getJdbcTemplate().queryForObject(findById, rowMapper, id);
	}

	public List<Category> findByNameLike(String name) {
		return insert.getJdbcTemplate().query(findByNameLike, rowMapper, name.toLowerCase().concat("%"));
	}

	public int findCountByNameLike(String name) {
		return insert.getJdbcTemplate().queryForObject(findCountByNameLike, Integer.class, name.toLowerCase().concat("%"));
	}

	public int delete(int id) {
		return insert.getJdbcTemplate().update(delete, id);
	}

}
