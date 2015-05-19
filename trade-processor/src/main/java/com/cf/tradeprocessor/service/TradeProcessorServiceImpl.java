package com.cf.tradeprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;

@Service
public class TradeProcessorServiceImpl implements TradeProcessorService {
	private static Logger logger = LoggerFactory.getLogger(TradeProcessorServiceImpl.class); 
	
	@Override
	public void process(TradeMessage message, Long timestamp) throws TradeProcessorException {
		// TODO 0. Include Integration Test
		// TODO 1. Store raw trade message in mongo (optional)
		// TODO 2. Summarize totals
		// TODO 3. Send totals via WebSocket
		
		System.out.println(message);
	}
}
