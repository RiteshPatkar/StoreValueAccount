package com.svpoc.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svpoc.data.SVAccount;
import com.svpoc.data.SVBalance;
import com.svpoc.entity.Balance;
import com.svpoc.helper.TransactionLogHelper;
import com.svpoc.jpa.AccountRepository;
import com.svpoc.jpa.BalanceRepository;

@Service
public class SVBalanceService {
	
	@Autowired
	SVAccountService accountService;
	
	@Autowired
	BalanceRepository balanceRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionLogHelper transactionHelper;
	
	@Autowired
	SVAccount response;
	
	public SVBalance updateBalance(SVBalance balance) {
//		Integer merchantAccountId = null;
//		
//		Integer customerAccountId = accountService.getAccountId(balance.getFromWalletId(), balance.getFromPaymentMethodId());
//		
//		if(null != balance.getToWalletId() && null != balance.getToPaymentMethodId()) {
//			merchantAccountId = accountService.getAccountId(balance.getToWalletId(), balance.getToPaymentMethodId());
//		}
		
		//if transaction type Add
		//add in customer id
		if("ADD_BALANCE".equalsIgnoreCase(balance.getTransactionType())) {
						
			Integer customerAccountId = accountService.getAccountId(balance.getFromWalletId(), balance.getFromPaymentMethodId());

			Balance entity = new Balance();
			entity.setAccountId(customerAccountId);
			
			Optional<Balance> customerBalance = balanceRepository.findById(customerAccountId);
			
			BigDecimal newBalanceAmount = customerBalance.isPresent() ? customerBalance.get().getBalance().add(balance.getAmount()): balance.getAmount();
			entity.setBalance(newBalanceAmount);
			
			balanceRepository.saveAndFlush(entity);
			if(customerBalance.isPresent()) {
			}
		}
		
		//if transaction type update (user uses SV card at merchant's place)
		//add to merchant id
		//remove from customer Id
		else if("MAKE_TRANSACTION".equalsIgnoreCase(balance.getTransactionType())) {
			
			Integer merchantAccountId = null;
			
			Integer customerAccountId = accountService.getAccountId(balance.getFromWalletId(), balance.getFromPaymentMethodId());
			
			if(null != balance.getToWalletId() && null != balance.getToPaymentMethodId()) {
				merchantAccountId = accountService.getAccountId(balance.getToWalletId(), balance.getToPaymentMethodId());
			}

			
			Balance customerEntity = new Balance();
			customerEntity.setAccountId(customerAccountId);
			
			Optional<Balance> customerBalance = balanceRepository.findById(customerAccountId);
			
			if(customerBalance.isPresent()) { //HANDLE SCENARIO WHEN NO CUSTOMER BALANCE IS PRESENT. MAKE XTN IS NOT POSSIBLE
				BigDecimal newCustomerBalanceAmount = customerBalance.get().getBalance().subtract(balance.getAmount()); //WHAT IF BALANCE IS NEGATIVE
				customerEntity.setBalance(newCustomerBalanceAmount);
				
				balanceRepository.saveAndFlush(customerEntity);
			}
			
			Balance merchantEntity = new Balance();
			merchantEntity.setAccountId(merchantAccountId);

			Optional<Balance> merchantBalance = balanceRepository.findById(merchantAccountId);

			BigDecimal newMerchantBalanceAmount = merchantBalance.isPresent() ? merchantBalance.get().getBalance().add(balance.getAmount()) : balance.getAmount();
			merchantEntity.setBalance(newMerchantBalanceAmount);
			
			balanceRepository.saveAndFlush(merchantEntity);
		}
	
		//if transaction type pay(Fpay can make getBalce bcall for merchant and then after payment to merchant update merchant SV account balance)
		//subtract balance from merchant account
		else if("PAY_MERCHANT".equalsIgnoreCase(balance.getTransactionType())) {
			
			Integer merchantAccountId = null;
					
			if(null != balance.getToWalletId() && null != balance.getToPaymentMethodId()) {
				merchantAccountId = accountService.getAccountId(balance.getToWalletId(), balance.getToPaymentMethodId());
			}

			
			Balance merchantEntity = new Balance();
			merchantEntity.setAccountId(merchantAccountId);
			
			Optional<Balance> merchantBalance = balanceRepository.findById(merchantAccountId);
			
			if(merchantBalance.isPresent()) {
				BigDecimal newMerchantBalanceAmount = merchantBalance.get().getBalance().subtract(balance.getAmount());//SCENARIO WHEN BALANCE AMOUNT IS LESS THAN PAY AMOUNT 
				merchantEntity.setBalance(newMerchantBalanceAmount);
				
				balanceRepository.saveAndFlush(merchantEntity);
			}
		}
		//unknown transaction Type
		else {
			return null;
		}
		
		transactionHelper.populateTransactionLog(balance.getAmount(), balance.getFromPaymentMethodId(),
				balance.getFromWalletId(), balance.getToPaymentMethodId(), balance.getToWalletId(),
				balance.getTransactionType());

		return balance;
	}
	

	public BigDecimal getBalance(String walletId, String paymentMethodId) {
		Integer accountId = accountService.getAccountId(walletId, paymentMethodId);
		Balance balance = balanceRepository.getOne(accountId);
		
		return balance.getBalance();
	}
}