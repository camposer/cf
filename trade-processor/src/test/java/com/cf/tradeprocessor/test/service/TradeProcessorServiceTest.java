package com.cf.tradeprocessor.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.cache.InMemoryTradeSummaryCache;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.repository.mongo.TradeMessageRepository;
import com.cf.tradeprocessor.service.TradeProcessorService;
import com.cf.tradeprocessor.service.TradeProcessorServiceImpl;
import com.cf.tradeprocessor.service.TradeSummaryService;
import com.cf.tradeprocessor.service.TradeSummaryServiceImpl;
import com.cf.tradeprocessor.test.config.MongoConfigTest;
import com.cf.tradeprocessor.test.config.ProxyConfigTest;
import com.cf.tradeprocessor.test.service.TradeProcessorServiceTest.Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class, ProxyConfigTest.class, MongoConfigTest.class })
public class TradeProcessorServiceTest {
	private final String currencyFrom = "EUR";
	private final String currencyTo = "GBP";
	private final Double amountSell = 1000d;
	private final Double amountBuy = 747.1;
	private final Double rate = 0.7471;
	
	@Autowired
	private TradeProcessorService tradeProcessorService;
	@Autowired
	private TradeMessageRepository tradeMessageRepository;
	@Autowired
	private TradeSummaryService tradeSummaryService;
    @Autowired
    private SimpMessagingTemplate template;	

	private TradeMessage tradeMessage;
	
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
	}
	
	@Test
	public void testProduce() {
		tradeProcessorService.process(tradeMessage);
		
		// Testing TradeSummaryService results
		final TradeSummary tradeSummary = tradeSummaryService.getTradeSummary(currencyFrom, currencyTo);
		
		assertTrue(amountSell == tradeSummary.getTotalAmountSell());
		assertTrue(amountBuy == tradeSummary.getTotalAmountBuy());
		assertTrue(rate == tradeSummary.getRateAvg());
		
		// Testing TradeMessageRepository
		assertTrue(tradeMessageRepository.count() == 1);
		
		TradeMessage tradeMessageInRepo = tradeMessageRepository.findOne(tradeMessage.getId());
		assertEquals(tradeMessageInRepo, tradeMessage);
	}

	@Configuration
	public static class Config {
		@Bean
		public TradeProcessorService tradeProcessorService() {
			return new TradeProcessorServiceImpl();
		}
		
		@Bean
		public TradeSummaryService tradeSummaryService() {
			return new TradeSummaryServiceImpl();
		}

		@Bean
		public TradeSummaryCache tradeSummaryCache() {
			return new InMemoryTradeSummaryCache();
		}

		@Bean
		public SimpMessagingTemplate simpMessagingTemplate() {
			return Mockito.mock(SimpMessagingTemplate.class); // TODO This should be tested in a different test class (websocket)
		}
	}

}
