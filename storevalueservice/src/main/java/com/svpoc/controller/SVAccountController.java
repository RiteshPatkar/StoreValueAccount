package com.svpoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.svpoc.data.SVAccount;
import com.svpoc.service.SVAccountService;

@RestController
@RequestMapping(value = "/svAccount")
public class SVAccountController {
	
	@Autowired
	private SVAccountService service;
	
	@PostMapping
	public SVAccount addStoreValueAccount(@RequestBody SVAccount account) {
		return service.addAccount(account);
	}
	
	@GetMapping("{walletId}")
	public SVAccount getStoreValueAccountDetails(@PathVariable(value = "walletId") final String walletId) {
		return service.getAccountDetails(walletId);
	}
	
	@GetMapping("{walletId}/{paymentMethodId}")
	public SVAccount getStoreValueAccountDetail(@PathVariable(value = "walletId") final String walletId, @PathVariable(value = "paymentMethodId") final String paymentMethodId) {
		return service.getSingleAccountDetail(walletId, paymentMethodId);
	}
	
	@DeleteMapping(value = "/wallet")
	public SVAccount deleteAccount(@RequestBody SVAccount account) {
		return service.deleteAccount(account);
	}
}
