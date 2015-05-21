package com.cf.tradeprocessor.cache;

import java.util.Map;
import java.util.Set;

import com.cf.tradeprocessor.model.TradeSummary;

public interface TradeSummaryCache {
	/**
	 * Returns a set of all currencyFrom
	 */
	Set<String> currenciesFrom();
	
	/**
	 * Return a map of currencyTo and TradeSummary for a given currencyTo
	 * IMPORTANT: The first time this method is called should read mongodb for populating the internal map
	 */
	Map<String, TradeSummary> get(String currencyFrom);
	
	/**
	 * Puts a map of currencyTo and TradeSummary  for a currencyFrom
	 */
	void put(String currencyFrom, Map<String, TradeSummary> tradeSummaryMap);
}
