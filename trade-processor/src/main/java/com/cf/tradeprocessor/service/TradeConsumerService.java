package com.cf.tradeprocessor.service;

import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeConsumerService {
	/**
	 * Publish raw trade messages to tradeMessageChannel queue
	 * @param	tradeMessage			TradeMessage to be sent 	
	 * @throws	TradeProcessorException	In case an error occurs
	 */
	void consume(TradeMessage tradeMessage) throws TradeProcessorException;
}
