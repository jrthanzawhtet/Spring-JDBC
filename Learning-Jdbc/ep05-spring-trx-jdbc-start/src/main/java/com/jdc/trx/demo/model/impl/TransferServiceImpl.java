package com.jdc.trx.demo.model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.jdc.trx.demo.model.TransferServiceException;
import com.jdc.trx.demo.model.dao.AccountDao;
import com.jdc.trx.demo.model.dao.AccountHistoryDao;
import com.jdc.trx.demo.model.dao.TransferLogDao;
import com.jdc.trx.demo.model.dto.TransferForm;
import com.jdc.trx.demo.model.dto.TransferLog;

@Service
public class TransferServiceImpl {
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private TransferLogDao transferLogDao;
	
	@Autowired
	private AccountHistoryDao accountHistoryDao;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public TransferLog transfer(TransferForm form) {
		eventPublisher.publishEvent(form);
		
		var transferId = transferLogDao.create(form);
		
		var fromAccount = accountDao.findById(form.account_from());
		
		var updatedAmountForFromUser = fromAccount.amount() - form.amount();
		
		if(updatedAmountForFromUser < 0) {
			throw new TransferServiceException("Account has no enough amount to transfer.");
		}
		
		accountDao.save(fromAccount.withAmount(updatedAmountForFromUser));
		
		var fromHistory = accountHistoryDao.debit(transferId, fromAccount, form.amount());
		
		var toAccount = accountDao.findById(form.account_to());
		
		var updatedAmountForToUser = toAccount.amount() + form.amount();
		
		accountDao.save(toAccount.withAmount(updatedAmountForToUser));
		
		var toHistory = accountHistoryDao.credit(transferId, toAccount, form.amount());
		
		return TransferLog.from(transferId, form.amount(), fromHistory, toHistory, form.transfer_at());
	}

}
