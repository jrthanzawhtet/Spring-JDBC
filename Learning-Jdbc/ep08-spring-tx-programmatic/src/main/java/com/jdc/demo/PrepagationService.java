package com.jdc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import com.jdc.demo.repo.DeatilsRepository;
import com.jdc.demo.repo.HeaderRepository;

@Service
public class PrepagationService {

	@Autowired
	private HeaderRepository headerRepository;

	@Autowired
	private DeatilsRepository detailsRepository;

	@Autowired
	private PlatformTransactionManager txManager;

	private TransactionTemplate txTemplate;

	public PrepagationService(PlatformTransactionManager txManager) {
		txTemplate = new TransactionTemplate(txManager);
		txTemplate.setPropagationBehavior(TransactionAttribute.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionAttribute.ISOLATION_SERIALIZABLE);
		txTemplate.setTimeout(5);

	}

	/*
	 * public Result save(int state, String header, String... details) {
	 * 
	 * var txAttributes = new DefaultTransactionAttribute();
	 * txAttributes.setPropagationBehavior(TransactionAttribute.PROPAGATION_REQUIRED
	 * );
	 * txAttributes.setIsolationLevel(TransactionAttribute.ISOLATION_SERIALIZABLE);
	 * txAttributes.setTimeout(5);
	 * 
	 * 
	 * // Transaction Start TransactionStatus status = txManager.getTransaction(new
	 * DefaultTransactionAttribute());
	 * 
	 * 
	 * try {
	 * 
	 * var headerId = headerRepository.create(header);
	 * 
	 * if (state == 1) { throw new RuntimeException(); }
	 * 
	 * var detailsId = detailsRepository.create(headerId, details);
	 * 
	 * if (state == 2) { throw new RuntimeException(); }
	 * 
	 * // Commit txManager.commit(status);
	 * 
	 * return new Result(headerId, detailsId);
	 * 
	 * } catch (Exception e) { // Rollback txManager.rollback(status); throw new
	 * RuntimeException(e); }
	 * 
	 * }
	 */

	public Result save(int state, String header, String... details) {
		return txTemplate.execute(status -> {
			var headerId =  txTemplate.execute(a -> headerRepository.create(header)) ;

			if (state == 1) {
				throw new RuntimeException();
			}

			var detailsId = detailsRepository.create(headerId, details);

			if (state == 2) {
				throw new RuntimeException();
			}

			// Commit txManager.commit(status);

			return new Result(headerId, detailsId);
		});
	}

}
