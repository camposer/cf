package com.cf.tradeprocessor.model;

import java.io.Serializable;

public class TradeSummary implements Serializable {
	private static final long serialVersionUID = -93470476097394080L;

	private String currencyFrom;
	private String currencyTo;
	private double amountSell; // Maybe BigDecimal?
	private double amountBuy; // Maybe BigDecimal?
	private double rateAvg;
	private int rateCounter;

	public TradeSummary() {
		this(null, null);
	}
	
	public TradeSummary(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.amountSell = 0d;
		this.amountBuy = 0d;
		this.rateAvg = 0d;
		this.rateCounter = 0;
	}

	public String getCurrencyFrom() {
		return currencyFrom;
	}
	
	public String getCurrencyTo() {
		return currencyTo;
	}
	
	public double getTotalAmountSell() {
		return amountSell;
	}
	
	public double getTotalAmountBuy() {
		return amountBuy;
	}

	public double getRateAvg() {
		return rateAvg / rateCounter;
	}

	public void addOperation(double amountSell, double amountBuy) {
		this.amountSell += amountSell;
		this.amountBuy += amountBuy;
		this.rateAvg += amountBuy / amountSell;
		this.rateCounter++;
	}

	@Override
	public String toString() {
		return "TradeSummary [currencyFrom=" + currencyFrom + ", currencyTo="
				+ currencyTo + ", amountSell=" + amountSell + ", amountBuy="
				+ amountBuy + ", rateAvg=" + rateAvg + ", rateCounter="
				+ rateCounter + "]";
	}
}
