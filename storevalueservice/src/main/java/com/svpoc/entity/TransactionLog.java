package com.svpoc.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Table(name = "SV_TRANSACTION_LOG")
@Entity
public class TransactionLog {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "FROM_WALLET_ID")
	private String fromWalletId;
	
	@Column(name = "FROM_PAYMENT_METHOD_ID")
	private String fromPaymentMethodId;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "TO_WALLET_ID")
	private String toWalletId;
	
	@Column(name = "TO_PAYMENT_METHOD_ID")
	private String toPaymentMethodId;
	
	@Column(name = "TRANSACTION_TYPE")
	@NotNull
	private String transactionType;
}
