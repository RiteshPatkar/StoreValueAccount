package com.svpoc.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SVTransactionLog {
	
	private String fromWalletId;
	private String fromPaymentMethodId;
	private BigDecimal amount;
	private String toWalletId;
	private String toPaymentMethodId;
	private String transactionType;
}
