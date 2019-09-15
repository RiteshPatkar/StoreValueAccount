package com.svpoc.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.svpoc.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public List<Account> findByWalletId(String walletId);
	
	public List<Account> findByWalletIdAndPaymentMethodId(String walletId, String paymentMethodId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from SV_ACCOUNT WHERE WALLET_ID = ?", nativeQuery = true)
	public void deleteAccountByWalletId(String walletId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE from SV_ACCOUNT WHERE WALLET_ID = ? AND PAYMENT_METHOD_ID = ?", nativeQuery = true)
	public void deletePaymentMethodForAccount(String walletId, String paymentMethodId);

}
