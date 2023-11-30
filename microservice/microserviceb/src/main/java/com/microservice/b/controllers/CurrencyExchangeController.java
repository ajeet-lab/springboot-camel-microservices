package com.microservice.b.controllers;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.b.entities.CurrencyExchange;

@RestController
public class CurrencyExchangeController {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange fintCurrencyExchange(@PathVariable String from, @PathVariable String to) {
		return new CurrencyExchange(2000L, from, to, BigDecimal.valueOf(70));
	}
}
