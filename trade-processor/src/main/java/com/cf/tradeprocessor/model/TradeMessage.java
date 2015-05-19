package com.cf.tradeprocessor.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TradeMessage {
	private static final String DATE_MASK = "dd-MMM-yy HH:mm:ss";
	
	private String userId;
	private String currencyForm; // Maybe create enum for this
	private String currencyTo; // Maybe create enum for this
	private Double amountSell;
	private Double amountBuy;
	private Double rate;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss")	
	private String timePlaced;
	private String originatingCountry; // Maybe create enum for this
	
	public TradeMessage() {
	
	}
	
	public TradeMessage(String userId, String currencyForm, String currencyTo,
			Double amountSell, Double amountBuy, Double rate, String timePlaced,
			String originatingCountry) {
		this.userId = userId;
		this.currencyForm = currencyForm;
		this.currencyTo = currencyTo;
		this.amountSell = amountSell;
		this.amountBuy = amountBuy;
		this.rate = rate;
		this.timePlaced = timePlaced;
		this.originatingCountry = originatingCountry;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCurrencyForm() {
		return currencyForm;
	}
	
	public void setCurrencyForm(String currencyForm) {
		this.currencyForm = currencyForm;
	}
	
	public String getCurrencyTo() {
		return currencyTo;
	}
	
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	
	public Double getAmountSell() {
		return amountSell;
	}
	
	public void setAmountSell(Double amountSell) {
		this.amountSell = amountSell;
	}
	
	public Double getAmountBuy() {
		return amountBuy;
	}
	
	public void setAmountBuy(Double amountBuy) {
		this.amountBuy = amountBuy;
	}
	
	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public String getTimePlaced() {
		return timePlaced;
	}
	
	public void setTimePlaced(String timePlaced) {
		this.timePlaced = timePlaced;
	}
	
	public Date getTimePlacedDate() throws ParseException {
		return new SimpleDateFormat(DATE_MASK).parse(timePlaced);
	}
	
	public String getOriginatingCountry() {
		return originatingCountry;
	}
	
	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	@Override
	public String toString() {
		return "TradeMessage [userId=" + userId + ", currencyForm="
				+ currencyForm + ", currencyTo=" + currencyTo + ", amountSell="
				+ amountSell + ", amountBuy=" + amountBuy + ", rate=" + rate
				+ ", timePlaced=" + timePlaced + ", originatingCountry="
				+ originatingCountry + "]";
	}
}
