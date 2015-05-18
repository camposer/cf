package com.cf.tradeprocessor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import com.cf.tradeprocessor.dto.TradeMessage;
import com.cf.tradeprocessor.exception.TradeProcessorException;

@Service
public class TradeServiceImpl implements TradeService {
	@Value("#{tradeMessages}")
	private AbstractSubscribableChannel channel;

	@Override
	public void consumeTradeMessage(TradeMessage tradeMessage) throws TradeProcessorException {
		channel.send(new GenericMessage<TradeMessage>(tradeMessage));
	}

	@Override
	public void process(Message<TradeMessage> message) throws TradeProcessorException {
		// TODO 0. Include Integration Test
		// TODO 1. Store raw trade message in mongo (optional)
		// TODO 2. Summarize totals
		// TODO 3. Send totals via WebSocket
		
		System.out.println(message);
	}
}
