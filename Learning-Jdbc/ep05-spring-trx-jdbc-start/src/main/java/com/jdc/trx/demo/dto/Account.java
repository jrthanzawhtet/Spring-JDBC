package com.jdc.trx.demo.dto;

public record Account(
		int id,
		String name,
		String email,
		int amount
		) {

	public Account withAmount(int amount) {
		return new Account(amount, name, email, amount);
	}
}
