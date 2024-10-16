package com.jdc.trx.demo.model;

import java.sql.Timestamp;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.jdc.trx.demo.model.dto.TransferForm;

public class TransferOperationRollbakListener {
	
	private SimpleJdbcInsert insert;
	
	public TransferOperationRollbakListener(DataSource dataSource) {
	  insert = new SimpleJdbcInsert(dataSource);
	  insert.setTableName("transfer_log");
	  insert.setGeneratedKeyName("id");
	}
	
	@Transactional
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void onRollback(TransferForm form) {
		insert.execute(Map.of(
				"account_from", form.account_from(),
				"account_to", form.account_to(),
				"amount", form.amount(),
				"transfer_at", Timestamp.valueOf(form.transfer_at()),
				"status", true,
				"message", "Fails Operation"));
	}

}
