package com.svpoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svpoc.data.SVAccount;
import com.svpoc.entity.Account;
import com.svpoc.helper.TransactionLogHelper;
import com.svpoc.jpa.AccountRepository;

@Service
public class SVAccountService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionLogHelper transactionHelper;
	
	@Autowired
	SVAccount response;
	
	public SVAccount getAccountDetails(String walletId) {
		List<Account> accounts = accountRepository.findByWalletId(walletId);
		return createResponse(accounts);
	}
	
	public SVAccount getSingleAccountDetail(String walletId, String paymentMethodId) {
		List<Account> accounts = accountRepository.findByWalletIdAndPaymentMethodId(walletId, paymentMethodId);
		return createResponse(accounts);
	}
	
	public Integer getAccountId(String walletId, String paymentMethodId) {
		List<Account> accounts = accountRepository.findByWalletIdAndPaymentMethodId(walletId, paymentMethodId);
		return accounts.get(0).getId();
	}

	public SVAccount addAccount(SVAccount accountToAdd) {
		Account account = new Account();
		account.setWalletId(accountToAdd.getWalletId());
		account.setAccountType(accountToAdd.getAccountType());
		account.setPaymentMethodId(accountToAdd.getPaymentMethodId());
		accountRepository.saveAndFlush(account);
		
		transactionHelper.populateTransactionLog(null, accountToAdd.getPaymentMethodId(),accountToAdd.getWalletId(),null, null, "ADD ACCOUNT");
		
		return createResponse();
	}
	
	public SVAccount deleteAccount(SVAccount accoutToDelete) {
			
		accountRepository.deleteAccountByWalletId(accoutToDelete.getWalletId());
		
		transactionHelper.populateTransactionLog(null, accoutToDelete.getPaymentMethodId(),accoutToDelete.getWalletId(),null, null, "DELETE ACCOUNT");
		
		return createResponse();
	}

	private SVAccount createResponse(List<Account> accounts) {
		if(null == accounts || accounts.isEmpty()) {
			response.setErrorCode(001);
			response.setMessage("No Account Found");
		} else if (accounts.size() > 1){
			response.setErrorCode(002);
			response.setMessage("Multiple Accounts Found");
		} else {
			Account account = accounts.get(0);
			response.setWalletId(account.getWalletId());
			response.setAccountType(account.getAccountType());
			response.setPaymentMethodId(account.getPaymentMethodId());
		}
		return response;
	}
	
	private SVAccount createResponse() {
		response.setMessage("Success");
		return response;
	}
}
