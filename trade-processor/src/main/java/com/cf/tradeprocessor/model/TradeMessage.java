package com.cf.tradeprocessor.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TradeMessage implements Serializable {
	private static final long serialVersionUID = -4523936941300220553L;
	private static final String DATE_MASK = "dd-MMM-yy HH:mm:ss";
	
	@Id
	private String id;	
	private String userId;
	private String currencyFrom; // Maybe create enum for this
	private String currencyTo; // Maybe create enum for this
	private Double amountSell;
	private Double amountBuy;
	private Double rate;
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss")	
	private String timePlaced;
	private String originatingCountry; // Maybe create enum for this
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
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

	@JsonIgnore
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
		return "TradeMessage [userId=" + userId + ", currencyFrom="
				+ currencyFrom + ", currencyTo=" + currencyTo + ", amountSell="
				+ amountSell + ", amountBuy=" + amountBuy + ", rate=" + rate
				+ ", timePlaced=" + timePlaced + ", originatingCountry="
				+ originatingCountry + "]";
	}
}
