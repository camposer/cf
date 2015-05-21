package com.cf.tradeprocessor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;

@Service
public class TradeSummaryServiceImpl implements TradeSummaryService {
	@Autowired
	private TradeSummaryCache tradeSummaryCache;
	
	@Override
	public Map<String, List<TradeSummary>> getTradeSummaries() {
		Map<String, List<TradeSummary>> tradeSummaries = null;
		Set<String> currenciesFrom = tradeSummaryCache.currenciesFrom(); 
		
		for (String currencyFrom : currenciesFrom) {
			if (tradeSummaries == null)
				tradeSummaries = new Hashtable<String, List<TradeSummary>>();
			
			tradeSummaries.put(currencyFrom, getTradeSummaries(currencyFrom));
		}

		return tradeSummaries;
	}

	@Override
	public void add(TradeMessage tradeMessage) {
		Map<String, TradeSummary> currencyFromTradeMap = tradeSummaryCache.get(tradeMessage.getCurrencyFrom());

		if (currencyFromTradeMap == null) { 
			currencyFromTradeMap = new Hashtable<String, TradeSummary>();
			tradeSummaryCache.put(tradeMessage.getCurrencyFrom(), currencyFromTradeMap);
		}

		TradeSummary tradeSummary = currencyFromTradeMap.get(tradeMessage.getCurrencyTo());
		
		if (tradeSummary == null) {
			tradeSummary = new TradeSummary(tradeMessage.getCurrencyFrom(), tradeMessage.getCurrencyTo());
			currencyFromTradeMap.put(tradeMessage.getCurrencyTo(), tradeSummary);
		}
		
		tradeSummary.addOperation(tradeMessage.getAmountSell(), tradeMessage.getAmountBuy());
	}

	@Override
	public List<TradeSummary> getTradeSummaries(String currencyFrom) {
		Collection<TradeSummary> tradeSummaries = tradeSummaryCache.get(currencyFrom).values();
		
		if (tradeSummaries != null)
			return new ArrayList<TradeSummary>(tradeSummaries);
		else
			return null;
	}

	@Override
	public TradeSummary getTradeSummary(String currenFrom, String currencyTo) {
		Map<String,TradeSummary> currencyFromTradeMap = tradeSummaryCache.get(currenFrom);
		
		if (currencyFromTradeMap != null)
			return currencyFromTradeMap.get(currencyTo);
		else
			return null;
	}
	
}
