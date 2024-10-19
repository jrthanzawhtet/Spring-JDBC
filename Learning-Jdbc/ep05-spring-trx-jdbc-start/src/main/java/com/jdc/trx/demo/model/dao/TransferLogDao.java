package com.jdc.trx.demo.model.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.jdc.trx.demo.model.dto.TransferForm;

@Repository
public class TransferLogDao {
	
	private SimpleJdbcInsert insert;
	
	public TransferLogDao(DataSource dataSource) {
		insert = new SimpleJdbcInsert(dataSource);
		insert.setTableName("transfer_log");
		insert.setGeneratedKeyName("id");
		insert.setColumnNames(List.of(
				"account_from", "account_to", "amount",
				"transfer_at"));
	}

	public int create(TransferForm form) {
		return insert.executeAndReturnKey(new BeanPropertySqlParameterSource(form))
				.intValue();
	}

}