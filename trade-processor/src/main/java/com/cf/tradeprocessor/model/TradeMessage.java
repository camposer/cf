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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((amountBuy == null) ? 0 : amountBuy.hashCode());
		result = prime * result
				+ ((amountSell == null) ? 0 : amountSell.hashCode());
		result = prime * result
				+ ((currencyFrom == null) ? 0 : currencyFrom.hashCode());
		result = prime * result
				+ ((currencyTo == null) ? 0 : currencyTo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((originatingCountry == null) ? 0 : originatingCountry
						.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result
				+ ((timePlaced == null) ? 0 : timePlaced.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeMessage other = (TradeMessage) obj;
		if (amountBuy == null) {
			if (other.amountBuy != null)
				return false;
		} else if (!amountBuy.equals(other.amountBuy))
			return false;
		if (amountSell == null) {
			if (other.amountSell != null)
				return false;
		} else if (!amountSell.equals(other.amountSell))
			return false;
		if (currencyFrom == null) {
			if (other.currencyFrom != null)
				return false;
		} else if (!currencyFrom.equals(other.currencyFrom))
			return false;
		if (currencyTo == null) {
			if (other.currencyTo != null)
				return false;
		} else if (!currencyTo.equals(other.currencyTo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originatingCountry == null) {
			if (other.originatingCountry != null)
				return false;
		} else if (!originatingCountry.equals(other.originatingCountry))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (timePlaced == null) {
			if (other.timePlaced != null)
				return false;
		} else if (!timePlaced.equals(other.timePlaced))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
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
