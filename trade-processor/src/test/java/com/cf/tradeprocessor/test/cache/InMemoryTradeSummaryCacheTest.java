package com.cf.tradeprocessor.test.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.cache.InMemoryTradeSummaryCache;
import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.repository.mongo.TradeMessageRepository;
import com.cf.tradeprocessor.test.cache.InMemoryTradeSummaryCacheTest.Config;
import com.cf.tradeprocessor.test.config.MongoConfigTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, MongoConfigTest.class })
public class InMemoryTradeSummaryCacheTest {
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private TradeMessageRepository tradeMessageRepository;
	@Autowired
	private MongoConfigTest mongoConfigTest;
	
	@Test
	public void testPopulate() {
		final String currencyFrom1 = "EUR";
		final String currencyFrom2 = "YEN";
		final String currencyTo1 = "GBP";
		final String currencyTo2 = "USD";
		final double amountSell1 = 1000;
		final double amountBuy1 = 747.1;
		final double rate1 = amountBuy1 / amountSell1;
		final double amountSell2 = 2000;
		final double amountBuy2 = 1400;
		final double rate2 = amountBuy2 / amountSell2;
		
		final TradeMessage tradeMessage11_1 = new TradeMessage();
		tradeMessage11_1.setCurrencyFrom(currencyFrom1);
		tradeMessage11_1.setCurrencyTo(currencyTo1);
		tradeMessage11_1.setAmountSell(amountSell1);
		tradeMessage11_1.setAmountBuy(amountBuy1);
		
		final TradeMessage tradeMessage11_2 = new TradeMessage();
		tradeMessage11_2.setCurrencyFrom(currencyFrom1);
		tradeMessage11_2.setCurrencyTo(currencyTo1);
		tradeMessage11_2.setAmountSell(amountSell1);
		tradeMessage11_2.setAmountBuy(amountBuy1);

		final TradeMessage tradeMessage12_1 = new TradeMessage();
		tradeMessage12_1.setCurrencyFrom(currencyFrom1);
		tradeMessage12_1.setCurrencyTo(currencyTo2);
		tradeMessage12_1.setAmountSell(amountSell2);
		tradeMessage12_1.setAmountBuy(amountBuy2);
		
		final TradeMessage tradeMessage22_1 = new TradeMessage();
		tradeMessage22_1.setCurrencyFrom(currencyFrom2);
		tradeMessage22_1.setCurrencyTo(currencyTo2);
		tradeMessage22_1.setAmountSell(amountSell2);
		tradeMessage22_1.setAmountBuy(amountBuy2);

		tradeMessageRepository.save(tradeMessage11_1);
		tradeMessageRepository.save(tradeMessage11_2);
		tradeMessageRepository.save(tradeMessage12_1);
		tradeMessageRepository.save(tradeMessage22_1);

		// Getting tradeSummaryCache "deferred" for testing populate
		TradeSummaryCache tradeSummaryCache = ctx.getBean(TradeSummaryCache.class);

		assertEquals(2, tradeSummaryCache.currenciesFrom().size());
	
		// currencyFrom1
		Map<String, TradeSummary> tradeSummaryMap = tradeSummaryCache.get(currencyFrom1);
		assertEquals(2, tradeSummaryMap.keySet().size());
		
		TradeSummary tradeSummary = tradeSummaryMap.get(currencyTo1);
		assertTrue(amountSell1 * 2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy1 * 2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate1 == tradeSummary.getRateAvg());

		tradeSummary = tradeSummaryMap.get(currencyTo2);
		assertTrue(amountSell2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate2 == tradeSummary.getRateAvg());

		// currencyFrom2
		tradeSummaryMap = tradeSummaryCache.get(currencyFrom2);
		assertEquals(1, tradeSummaryMap.keySet().size());

		tradeSummary = tradeSummaryMap.get(currencyTo2);
		assertTrue(amountSell2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate2 == tradeSummary.getRateAvg());
	}
	
	@After
	public void destroy() {
		mongoConfigTest.destroy();
	}
	
	@Configuration
	public static class Config {
		@Bean
		@Lazy
		public TradeSummaryCache tradeSummaryCache() {
			return new InMemoryTradeSummaryCache();
		}
	}
}
