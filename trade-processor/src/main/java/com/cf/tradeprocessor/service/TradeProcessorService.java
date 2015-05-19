package com.cf.tradeprocessor.service;

import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeProcessorService {
	/**
	 * Process raw trade messages  
	 * @param	message					Spring message (payload: TradeMessage)
	 * @param	timestamp				Timestamp when message was received 
	 */
	void process(TradeMessage message, Long timestamp) throws TradeProcessorException;
}
