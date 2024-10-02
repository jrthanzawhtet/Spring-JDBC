package com.jdc.spring.model.dao;

import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Service;

import com.jdc.spring.model.dto.Category;

@Service
public class CategoryDao {

	@Autowired
	private JdbcOperations jdbc;

	@Value("${dml.category.insert}")
	private String insertSql;

	public int create(Category category) {
		PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(insertSql, Types.VARCHAR);
		factory.setReturnGeneratedKeys(true);

		PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(category.getName()));

		PreparedStatementCallback<Integer> callback = stmt -> {
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new IllegalStateException("Insert failed, no rows affected.");
			}

			var rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new IllegalStateException("Insert succeeded but no ID obtained.");
			}
		};

		return jdbc.execute(creator, callback);
	}

}
