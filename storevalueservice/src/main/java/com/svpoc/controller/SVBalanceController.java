package com.svpoc.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.svpoc.data.SVBalance;
import com.svpoc.service.SVBalanceService;

@RestController
@RequestMapping(value = "/svBalance")
public class SVBalanceController {
	
	@Autowired
	private SVBalanceService service;
	
	@GetMapping("{walletId}/{paymentMethodId}")
	public BigDecimal getSVBalance(@PathVariable(value = "walletId") final String walletId,
			@PathVariable(value = "paymentMethodId") final String paymentMethodId) {
		return service.getBalance(walletId, paymentMethodId);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public SVBalance updateSVBalance(@RequestBody SVBalance balance) {
		SVBalance response = service.updateBalance(balance);
		
		return createResponse(balance, response);

	}
	
	private SVBalance createResponse(SVBalance balance, SVBalance response) {
		if(response == null) {
			balance.setMessage("Failed");
		} else {
			balance.setMessage("Success");
		}
		return balance;
	}
}
