package com.cf.tradeprocessor.test.channel.handler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.service.TradeProcessorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TradeMessageChannelHandlerTest {
	@Autowired
	private TradeProcessorService tradeProcessorService;
	@Value("#{tradeMessageChannel}")
	private AbstractSubscribableChannel channel;
	
	@Test(expected = Exception.class)
	public void test() {
		TradeMessage  tradeMessage = new TradeMessage();
		tradeMessage.setUserId("134256");
		tradeMessage.setCurrencyFrom("EUR");
		tradeMessage.setCurrencyTo("GBP");
		tradeMessage.setAmountSell(1000d);
		tradeMessage.setAmountBuy(747.1);
		tradeMessage.setRate(0.7471);
		tradeMessage.setTimePlaced("24­JAN­15 10:27:44");
		tradeMessage.setOriginatingCountry("FR");
		
		// Validating receiving
		Mockito.doThrow(new Exception("Ironically everything is fine!"))
			.when(tradeProcessorService)
			.process(tradeMessage);

		// Sending message to channel
		Message<TradeMessage> message = new GenericMessage<TradeMessage>(tradeMessage);
		channel.send(message);
	}
	
	@Configuration
	@ImportResource("classpath:/spring-test/integration.xml")	
	public static class Config {
		@Bean
		public TradeProcessorService tradeProcessorService() {
			return Mockito.mock(TradeProcessorService.class);
		}
		
	}
}
