package com.cf.tradeprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;

@Service
public class TradeConsumerServiceImpl implements TradeConsumerService {
	private static Logger logger = LoggerFactory.getLogger(TradeConsumerServiceImpl.class); 

	@Value("#{tradeMessageChannel}")
	private AbstractSubscribableChannel channel;

	@Override
	public void consume(TradeMessage tradeMessage) throws TradeProcessorException {
		boolean result = channel.send(new GenericMessage<TradeMessage>(tradeMessage));
		if (!result) {
			String error = "Error sending trade message to channel";
			logger.error(error);
			
			throw new TradeProcessorException(error); 
		}
	}

}
