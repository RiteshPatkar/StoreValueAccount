package com.svpoc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Table(name = "SV_ACCOUNT")
@Entity
public class Account {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ACCOUNT_TYPE")
	@NotNull
	private String accountType;

	@Column(name = "WALLET_ID")
	@NotNull
	private String walletId;
	
	@Column(name = "PAYMENT_METHOD_ID")
	@NotNull
	private String paymentMethodId;

}
