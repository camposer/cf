package com.cf.tradeprocessor.test.cache;

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
		final String currencyFrom = "EUR";
		final String currencyTo1 = "GBP";
		final String currencyTo2 = "USD";
		final double amountSell1 = 1000;
		final double amountBuy1 = 747.1;
		final double rate1 = amountBuy1 / amountSell1;
		final double amountSell2 = 2000;
		final double amountBuy2 = 1400;
		final double rate2 = amountBuy2 / amountSell2;
		
		final TradeMessage tradeMessage11 = new TradeMessage();
		tradeMessage11.setCurrencyFrom(currencyFrom);
		tradeMessage11.setCurrencyTo(currencyTo1);
		tradeMessage11.setAmountSell(amountSell1);
		tradeMessage11.setAmountBuy(amountBuy1);
		
		final TradeMessage tradeMessage12 = new TradeMessage();
		tradeMessage12.setCurrencyFrom(currencyFrom);
		tradeMessage12.setCurrencyTo(currencyTo1);
		tradeMessage12.setAmountSell(amountSell1);
		tradeMessage12.setAmountBuy(amountBuy1);

		final TradeMessage tradeMessage21 = new TradeMessage();
		tradeMessage21.setCurrencyFrom(currencyFrom);
		tradeMessage21.setCurrencyTo(currencyTo2);
		tradeMessage21.setAmountSell(amountSell2);
		tradeMessage21.setAmountBuy(amountBuy2);
		
		final TradeMessage tradeMessage22 = new TradeMessage();
		tradeMessage22.setCurrencyFrom(currencyFrom);
		tradeMessage22.setCurrencyTo(currencyTo2);
		tradeMessage22.setAmountSell(amountSell2);
		tradeMessage22.setAmountBuy(amountBuy2);

		tradeSummaryCache.add(tradeMessage11);
		tradeSummaryCache.add(tradeMessage12);
		tradeSummaryCache.add(tradeMessage21);
		tradeSummaryCache.add(tradeMessage22);
		
		assertEquals(tradeSummaryCache.getTradeSummaries(currencyFrom).size(), 2);
		
		TradeSummary tradeSummary = tradeSummaryCache.getTradeSummary(currencyFrom, currencyTo1);
		assertTrue(amountSell1 * 2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy1 * 2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate1 == tradeSummary.getRateAvg());

		tradeSummary = tradeSummaryCache.getTradeSummary(currencyFrom, currencyTo2);
		assertTrue(amountSell2 * 2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy2 * 2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate2 == tradeSummary.getRateAvg());
	}
	
	@Configuration
	public static class Config {
		@Bean
		public TradeSummaryCache tradeSummaryCache() {
			return new TradeSummaryCacheImpl();
		}
	}
}
