package com.jdc.trx.demo.model;

import org.springframework.transaction.annotation.Transactional;

import com.jdc.trx.demo.model.dto.TransferForm;
import com.jdc.trx.demo.model.dto.TransferLog;

public interface TransferService {
	
	@Transactional(noRollbackFor = TransferServiceException.class)
	TransferLog transfer(TransferForm form);

}
