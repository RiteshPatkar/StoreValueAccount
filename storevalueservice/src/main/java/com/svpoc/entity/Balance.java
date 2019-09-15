package com.svpoc.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Table(name = "SV_BALANCE")
@Entity
public class Balance {
	
	@Id
	@Column(name = "Account_ID")
	private Integer accountId;
	
	
	@Column(name = "Balance")
	@NotNull
	private BigDecimal balance;
}
