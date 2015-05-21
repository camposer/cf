package com.cf.tradeprocessor.cache;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.cf.tradeprocessor.model.TradeSummary;

/**
 * This should be changed by a Memcached implementation
 */
@Component
public class TradeSummaryCacheImpl implements TradeSummaryCache {
	private Map<String, Map<String, TradeSummary>> tradeSummaryCache;
	
	public TradeSummaryCacheImpl() {
		tradeSummaryCache = new Hashtable<String, Map<String,TradeSummary>>();
	}
	
	/**
	 * This method may be called in case of an error in any method declared on TradeSummaryCache
	 * For example: if we have a memcached implementation of this class, data may be lost and 
	 * in that case a reconstruction of the cache should be triggered    
	 */
	@PostConstruct
	public void populate() {
		// TODO Implement! Right now is not necessary because it's used an embed mongo
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
