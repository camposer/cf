package com.cf.tradeprocessor.test.integration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.cache.TradeSummaryCacheImpl;
import com.cf.tradeprocessor.channel.handler.TradeMessageChannelHandler;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.repository.mongo.TradeMessageRepository;
import com.cf.tradeprocessor.service.TradeConsumerService;
import com.cf.tradeprocessor.service.TradeConsumerServiceImpl;
import com.cf.tradeprocessor.service.TradeProcessorService;
import com.cf.tradeprocessor.service.TradeProcessorServiceImpl;
import com.cf.tradeprocessor.test.config.ProxyConfigTest;
import com.cf.tradeprocessor.test.config.MongoConfigTest;
import com.cf.tradeprocessor.test.integration.TradeConsumerProducerTest.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, ProxyConfigTest.class, MongoConfigTest.class })
public class TradeConsumerProducerTest {
	private final String currencyFrom = "EUR";
	private final String currencyTo = "GBP";
	private final Double amountSell = 1000d;
	private final Double amountBuy = 747.1;
	private final Double rate = 0.7471;
	
	private TradeMessage tradeMessage;
	
	@Autowired
	private TradeConsumerService tradeConsumerService;
	@Autowired
	private TradeSummaryCache tradeSummaryCache;
	@Autowired
	private TradeMessageRepository tradeMessageRepository;
	
	@Before
	public void setUp() throws Exception {
		tradeMessage = new TradeMessage();
		tradeMessage.setUserId("134256");
		tradeMessage.setCurrencyFrom(currencyFrom);
		tradeMessage.setCurrencyTo(currencyTo);
		tradeMessage.setAmountSell(amountSell);
		tradeMessage.setAmountBuy(amountBuy);
		tradeMessage.setRate(rate);
		tradeMessage.setTimePlaced("24­JAN­15 10:27:44");
		tradeMessage.setOriginatingCountry("FR");

		tradeConsumerService.consume(tradeMessage);
	}

	@After
	public void after() {
		tradeMessageRepository.delete(tradeMessage);
	}
	
	@Test
	public void testTradeSummaryCache() {
		final TradeSummary tradeSummary = tradeSummaryCache.getTradeSummary(currencyFrom, currencyTo);
		
		assertTrue(amountSell == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy == tradeSummary.getTotalAmountBuy());
		assertTrue(rate == tradeSummary.getRateAvg());	
	}
	
	@Test
	public void testTradeMessageRepository() {
		assertTrue(tradeMessageRepository.count() == 1);
		
		TradeMessage tradeMessageInRepo = tradeMessageRepository.findOne(tradeMessage.getId());
		assertEquals(tradeMessageInRepo, tradeMessage);
	}
	
	@Configuration
	@ImportResource("classpath:/spring-test/integration.xml")
	public static class Config {
		@Bean
		public TradeMessageChannelHandler tradeMessageChannelHandler() {
			return new TradeMessageChannelHandler();
		}

		@Bean
		public TradeProcessorService tradeProcessorService() {
			return new TradeProcessorServiceImpl();
		}

		@Bean
		public TradeConsumerService tradeConsumerService() {
			return new TradeConsumerServiceImpl();
		}

		@Bean
		public TradeSummaryCache tradeSummaryCache() {
			return new TradeSummaryCacheImpl();
		}
	}
}
