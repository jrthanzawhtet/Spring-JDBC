package com.jdc.spring.model.dao;

import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import com.jdc.spring.model.dto.Category;

@Service
public class CategoryDao {
	
	@Autowired
	private JdbcOperations jdbc;
	
	@Value("${dml.category.insert}")
	private String insertSql;
	
	public int create(Category category) {
		PreparedStatementCreator creator = conn -> {
			var stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,category.getName());
			return stmt;
		};
		return 0;
	}

}
