package com.cf.tradeprocessor.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.service.TradeConsumerService;
import com.cf.tradeprocessor.service.TradeConsumerServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TradeConsumerServiceTest implements MessageHandler {
	@Autowired
	private TradeConsumerService tradeConsumerService;
	@Value("#{tradeMessageChannel}")
	private AbstractSubscribableChannel channel;

	private TradeMessage tradeMessage;
	
	@Before
	public void setUp() {
		channel.subscribe(this);
	}
	
	@Test
	public void testConsume() throws Exception {
		tradeMessage = new TradeMessage();
		tradeMessage.setUserId("134256");
		tradeMessage.setCurrencyFrom("EUR");
		tradeMessage.setCurrencyTo("GBP");
		tradeMessage.setAmountSell(1000d);
		tradeMessage.setAmountBuy(747.1);
		tradeMessage.setRate(0.7471);
		tradeMessage.setTimePlaced("24­JAN­15 10:27:44");
		tradeMessage.setOriginatingCountry("FR");

		tradeConsumerService.consume(tradeMessage);
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		assertTrue(message.getPayload() instanceof TradeMessage);
		
		TradeMessage tradeMesageFromChannel = (TradeMessage)message.getPayload();
		assertEquals(tradeMessage, tradeMesageFromChannel);
	}
	
	@Configuration
	@ImportResource("classpath:/spring-test/integration.xml")
	public static class Config {
		@Bean
		public TradeConsumerService tradeConsumerService() {
			return new TradeConsumerServiceImpl();
		}
	}

}
