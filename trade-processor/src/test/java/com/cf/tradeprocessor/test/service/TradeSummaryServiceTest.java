package com.cf.tradeprocessor.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.cache.InMemoryTradeSummaryCache;
import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.service.TradeSummaryService;
import com.cf.tradeprocessor.service.TradeSummaryServiceImpl;
import com.cf.tradeprocessor.test.config.MongoConfigTest;
import com.cf.tradeprocessor.test.service.TradeSummaryServiceTest.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, MongoConfigTest.class })
public class TradeSummaryServiceTest {
	@Autowired
	private TradeSummaryService tradeSummaryService;
	@Autowired
	private MongoConfigTest mongoConfigTest;	
	
	@Test
	public void test() {
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

		tradeSummaryService.add(tradeMessage11_1);
		tradeSummaryService.add(tradeMessage11_2);
		tradeSummaryService.add(tradeMessage12_1);
		tradeSummaryService.add(tradeMessage22_1);

		assertEquals(2, tradeSummaryService.getTradeSummaries().size());
		assertEquals(2, tradeSummaryService.getTradeSummaries(currencyFrom1).size());
		
		TradeSummary tradeSummary = tradeSummaryService.getTradeSummary(currencyFrom1, currencyTo1);
		assertTrue(amountSell1 * 2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy1 * 2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate1 == tradeSummary.getRateAvg());

		tradeSummary = tradeSummaryService.getTradeSummary(currencyFrom1, currencyTo2);
		assertTrue(amountSell2 == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy2 == tradeSummary.getTotalAmountBuy());
		assertTrue(rate2 == tradeSummary.getRateAvg());

		tradeSummary = tradeSummaryService.getTradeSummary(currencyFrom2, currencyTo2);
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
		public TradeSummaryService tradeSummaryService() {
			return new TradeSummaryServiceImpl();
		}

		@Bean
		public TradeSummaryCache tradeSummaryCache() {
			return new InMemoryTradeSummaryCache();
		}
	}
}
