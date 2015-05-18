package com.cf.tradeprocessor.service;

import org.springframework.messaging.Message;

import com.cf.tradeprocessor.dto.TradeMessage;
import com.cf.tradeprocessor.exception.TradeProcessorException;

public interface TradeService {
	/**
	 * Publish raw trade messages to tradeMessages queue
	 * @param	tradeMessage			TradeMessage to be sent 	
	 * @throws	TradeProcessorException	In case an error occurs
	 */
	void consumeTradeMessage(TradeMessage tradeMessage) throws TradeProcessorException;

	/**
	 * Process raw trade messages  
	 * @param	message					Spring message (payload: TradeMessage) 
	 */
	void process(Message<TradeMessage> message) throws TradeProcessorException;
}
