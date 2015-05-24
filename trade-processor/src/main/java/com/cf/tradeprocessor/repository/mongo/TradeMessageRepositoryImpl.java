package com.cf.tradeprocessor.repository.mongo;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

import com.cf.tradeprocessor.model.TradeMessage;

public class TradeMessageRepositoryImpl implements TradeMessageRepositoryCustom {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<TradeMessage> findTradeSummaries() {
		List<AggregationOperation> operations = new ArrayList<AggregationOperation>();

		operations.add(project("currencyFrom", 
				"currencyTo", 
				"amountSell", 
				"amountBuy", 
				"rate"));

		operations.add(group(
				fields()
					.and("currencyFrom")
					.and("currencyTo")
					.and("amountSell")
					.and("amountBuy")
					.and("rate"))
				.sum("amountSell").as("amountSell")
				.sum("amountBuy").as("amountBuy")
				.avg("rate").as("rate"));			
		
		TypedAggregation<TradeMessage> aggregation = newAggregation(TradeMessage.class, operations.toArray(new AggregationOperation[0]));
		
		return mongoTemplate
				.aggregate(aggregation,	TradeMessage.class)
				.getMappedResults();
	}

}
