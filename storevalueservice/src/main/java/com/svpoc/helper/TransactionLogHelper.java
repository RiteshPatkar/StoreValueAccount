package com.svpoc.helper;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svpoc.data.SVTransactionLog;
import com.svpoc.entity.TransactionLog;
import com.svpoc.jpa.TransactionLogRepository;

@Component
public class TransactionLogHelper {
	
	@Autowired
	TransactionLogRepository repository;
	
	public SVTransactionLog populateTransactionLog(BigDecimal amount, String... data) {
		
		SVTransactionLog log = new SVTransactionLog();
		log.setAmount(amount);
		log.setFromPaymentMethodId(data[0]);
		log.setFromWalletId(data[1]);
		log.setToPaymentMethodId(data[2]);
		log.setToWalletId(data[3]);
		log.setTransactionType(data[4]);
		
		addtransaction(log);
		
		return log;
		
	}
	
	private void addtransaction(SVTransactionLog transactionLog) {
		TransactionLog entity = new TransactionLog();
		entity.setFromPaymentMethodId(transactionLog.getFromPaymentMethodId());
		entity.setFromWalletId(transactionLog.getFromWalletId());
		entity.setTransactionType(transactionLog.getTransactionType());
		entity.setAmount(transactionLog.getAmount());
		entity.setToPaymentMethodId(transactionLog.getToPaymentMethodId());
		entity.setToWalletId(transactionLog.getToWalletId());
		
		repository.saveAndFlush(entity);
	}

}
