package com.svpoc.data;

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
public class Payment {
//	private String fromWalletId;
//	private String toWalletId;
//	private BigDecimal amount;
//	private Integer errorCode;
//	private String message;
}
