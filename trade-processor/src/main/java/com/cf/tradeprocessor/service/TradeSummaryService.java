package com.cf.tradeprocessor.service;

import java.util.List;
import java.util.Map;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;

public interface TradeSummaryService {
	/**
	 * Add the trade message data to its corresponding trade summary
	 * @param	tradeMessage	Trade message to be processed (summarized)
	 */
	void add(TradeMessage tradeMessage);
	
	/**
	 * Get trade summaries 
	 * @param	currencyFrom	Currency to be used as key for trade summaries
	 * @return					Map of trade summaries for each currency. If there are are no summaries null is returned
	 */
	Map<String, List<TradeSummary>> getTradeSummaries();

	/**
	 * Get trade summaries for a specified currency
	 * @param	currencyFrom	Currency to be used as key for trade summaries
	 * @return					List of trade summaries for each currency. If there are are no summaries null is returned
	 */
	List<TradeSummary> getTradeSummaries(String currencyFrom);
	
	/**
	 * Get trade summary for a specified pair of currencies (from and to)
	 * @param	currencyFrom	Currency from
	 * @param 	currencyTo		Currency to
	 * @return					Trade summary for the specified currency pair (from and to)
	 */
	TradeSummary getTradeSummary(String currencyFrom, String currencyTo);
}
