package com.cf.tradeprocessor.channel.handler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.service.TradeProcessorService;

@Component
public class TradeMessageChannelHandler implements MessageHandler {
	private static Logger logger = LoggerFactory.getLogger(TradeMessageChannelHandler.class); 
	
	@Value("#{tradeMessageChannel}")
	private AbstractSubscribableChannel channel;
	
	@Autowired
	private TradeProcessorService tradeProcessorService;
	
	@PostConstruct
	public void init() {
		channel.subscribe(this);
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		logger.debug("Received trade message", message);
		
		try {
			if (message.getPayload() instanceof TradeMessage) 
				tradeProcessorService.process((TradeMessage) message.getPayload());
		} catch (Exception e) {
			String error = "Error processing message";
			logger.error(error, e);
			
			throw new MessagingException(error, e);
		}
	}

}
