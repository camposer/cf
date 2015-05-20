package com.cf.tradeprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cf.tradeprocessor.cache.TradeSummaryCache;
import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.repository.mongo.TradeMessageRepository;

@Service
public class TradeProcessorServiceImpl implements TradeProcessorService {
	private static Logger logger = LoggerFactory.getLogger(TradeProcessorServiceImpl.class); 
	
	@Autowired
	private TradeMessageRepository tradeMessageRepository;
	@Autowired
	private TradeSummaryCache tradeSummaryCache;
    @Autowired
    private SimpMessagingTemplate template;	
	
	@Override
	public void process(TradeMessage message) throws TradeProcessorException {
		try {
			saveTradeMessage(message);
			summarizeTrades(message);
			sendSummaries();
		} catch (Exception e) {
			String error = "Error while processing trade message";
			logger.error(error, e);
			
			throw new TradeProcessorException(error, e);
		}
	}

	private void saveTradeMessage(TradeMessage message) {
		tradeMessageRepository.save(message);
	}

	private void summarizeTrades(TradeMessage message) {
		tradeSummaryCache.add(message);
	}

	private void sendSummaries() {
		template.convertAndSend("/topic/trade-summaries", tradeSummaryCache.getTradeSummaries());
	}
}
