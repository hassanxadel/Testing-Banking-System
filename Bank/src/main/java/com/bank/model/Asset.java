package com.bank.model;

import java.security.SecureRandom;

public class Asset {
	private String assetId;
	private AssetType type;
	private double amount;
	private double equivalent_to;
	
	Asset(AssetType type, double amount, double equivalent_to) {
		if (amount > 0 && equivalent_to > 0) {
		    final SecureRandom secureRandom = new SecureRandom();
	        long timestamp = System.currentTimeMillis();
	        long randomPart = secureRandom.nextLong();
			assetId = String.format("%010d", Math.abs(timestamp + randomPart));
			this.type = type;
			this.amount = amount;
			this.equivalent_to = equivalent_to;
		}
	}
	
	String get_assetId() {
		return assetId;
	}
	
	AssetType get_type() {
		return type;
	}
	
	double get_amount() {
		return amount;
	}
	
	double get_equivalent_to() {
		return equivalent_to;
	}
	
}
