package com.cf.tradeprocessor.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;

@Component
public class TradeSummaryCacheImpl implements TradeSummaryCache {
	private Map<String, Map<String, TradeSummary>> tradeSummaryCache;
	
	public TradeSummaryCacheImpl() {
		tradeSummaryCache = new Hashtable<String, Map<String,TradeSummary>>();
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
