package com.cf.tradeprocessor.repository.mongo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeMessageRepository extends PagingAndSortingRepository<TradeMessage, String>{
	
}
