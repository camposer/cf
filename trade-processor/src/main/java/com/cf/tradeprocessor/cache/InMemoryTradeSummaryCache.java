package com.cf.tradeprocessor.cache;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.repository.mongo.TradeMessageRepository;

/**
 * This should be changed by a Memcached implementation
 */
@Component
public class InMemoryTradeSummaryCache implements TradeSummaryCache {
	private Map<String, Map<String, TradeSummary>> tradeSummaryCache;
	
	@Autowired
	private TradeMessageRepository tradeMessageRepository;
	
	public InMemoryTradeSummaryCache() {
		tradeSummaryCache = new Hashtable<String, Map<String,TradeSummary>>();
	}
	
	/**
	 * This method may be called in case of an error in any method declared on TradeSummaryCache
	 * For example: if we have a memcached implementation of this class, data may be lost and 
	 * in that case a reconstruction of the cache should be triggered    
	 */
	@PostConstruct
	public void populate() {
		List<TradeMessage> tradeMessages = tradeMessageRepository.findTradeSummaries();
		
		if (tradeMessages != null) for (TradeMessage tm : tradeMessages) {
			Map<String, TradeSummary> tradeSummaryMap = tradeSummaryCache.get(tm.getCurrencyFrom());
			
			if (tradeSummaryMap == null) {
				tradeSummaryMap = new Hashtable<String, TradeSummary>();
				tradeSummaryCache.put(tm.getCurrencyFrom(), tradeSummaryMap);
			}
			
			TradeSummary tradeSummary = new TradeSummary(tm.getCurrencyFrom(), tm.getCurrencyTo());
			tradeSummary.addOperation(tm.getAmountSell(), tm.getAmountBuy());
			tradeSummaryMap.put(tm.getCurrencyTo(), tradeSummary);
		}
	}
	
	@Override
	public Set<String> currenciesFrom() {
		return tradeSummaryCache.keySet();
	}
	
	@Override
	public Map<String, TradeSummary> get(String currencyFrom) {
		return tradeSummaryCache.get(currencyFrom);
	}
	
	@Override
	public void put(String currencyFrom, Map<String, TradeSummary> tradeSummaryMap) {
		tradeSummaryCache.put(currencyFrom, tradeSummaryMap);
	}
}
