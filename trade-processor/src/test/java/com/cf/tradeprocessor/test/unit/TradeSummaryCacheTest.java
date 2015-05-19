package com.cf.tradeprocessor.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.cache.TradeSummaryCacheImpl;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TradeSummaryCacheTest {
	@Autowired
	private TradeSummaryCache tradeSummaryCache;
	
	@Test
	public void test() {
		String currencyFrom = "EUR";
		String currencyTo = "GBP";
		double amountSell = 1000;
		double amountBuy = 747.1;
		double rate = amountBuy / amountSell;
		
		TradeMessage tradeMessage1 = new TradeMessage();
		tradeMessage1.setCurrencyFrom(currencyFrom);
		tradeMessage1.setCurrencyTo(currencyTo);
		tradeMessage1.setAmountSell(amountSell);
		tradeMessage1.setAmountBuy(amountBuy);
		
		TradeMessage tradeMessage2 = new TradeMessage();
		tradeMessage2.setCurrencyFrom(currencyFrom);
		tradeMessage2.setCurrencyTo(currencyTo);
		tradeMessage2.setAmountSell(amountSell);
		tradeMessage2.setAmountBuy(amountBuy);

		tradeSummaryCache.add(tradeMessage1);
		tradeSummaryCache.add(tradeMessage2);
		
		assertEquals(tradeSummaryCache.getTradeSummaries(currencyFrom).size(), 1);
		
		TradeSummary tradeSummary = tradeSummaryCache.getTradeSummary(currencyFrom, currencyTo);
		
		assertTrue(amountSell * 2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy * 2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate == tradeSummary.getRateAvg());
	}
	
	@Configuration
	public static class Config {
		@Bean
		public TradeSummaryCache tradeSummaryCache() {
			return new TradeSummaryCacheImpl();
		}
	}
}
