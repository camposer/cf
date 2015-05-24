package com.cf.tradeprocessor.repository.mongo;

import java.util.List;

import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeMessageRepositoryCustom {
	List<TradeMessage> findTradeSummaries();
}
