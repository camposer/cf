package com.cf.tradeprocessor.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cf.tradeprocessor.model.TradeMessage;

public interface TradeMessageRepository extends TradeMessageRepositoryCustom, MongoRepository<TradeMessage, String> {

}
