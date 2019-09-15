package com.svpoc.data;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode
@ToString(callSuper=true)
@Data
@Component
public class SVBalance {
	private String fromWalletId;
	private String fromPaymentMethodId;
	private BigDecimal amount;
	private String toWalletId;
	private String toPaymentMethodId;
	private String transactionType;
	
	private Integer errorCode;
	private String message;
}
