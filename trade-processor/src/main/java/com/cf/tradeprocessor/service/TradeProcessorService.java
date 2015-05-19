package com.cf.tradeprocessor.service;

import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeProcessorService {
	/**
	 * Process raw trade messages  
	 * @param	message					Spring message (payload: TradeMessage)
	 * @throws	In case an error occurs while processing the trade message
1	 */
	void process(TradeMessage message) throws TradeProcessorException;
}
