package com.cf.tradeprocessor.queue.handler;

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

import com.cf.tradeprocessor.dto.TradeMessage;
import com.cf.tradeprocessor.service.TradeService;

@Component
public class TradeMessageHandler implements MessageHandler {
	private static Logger logger = LoggerFactory.getLogger(TradeMessageHandler.class); 
	
	@Value("#{tradeMessages}")
	private AbstractSubscribableChannel channel;
	@Autowired
	private TradeService tradeService;
	
	@PostConstruct
	public void init() {
		channel.subscribe(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		logger.debug("Received trade message", message);
		
		try {
			if (message.getPayload() instanceof TradeMessage) 
				tradeService.process((Message<TradeMessage>) message);
		} catch (Exception e) {
			String error = "Error processing message";
			logger.error(error, e);
			
			throw new MessagingException(error, e);
		}
	}
}
