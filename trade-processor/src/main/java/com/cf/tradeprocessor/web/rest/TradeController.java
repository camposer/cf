package com.cf.tradeprocessor.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cf.tradeprocessor.dto.TradeMessage;
import com.cf.tradeprocessor.exception.TradeProcessorException;
import com.cf.tradeprocessor.service.TradeService;
import com.cf.tradeprocessor.web.rest.response.JsonResponse;

@RestController
@RequestMapping("/trades")
public class TradeController {
	private static Logger logger = LoggerFactory.getLogger(TradeController.class); 
	
	@Autowired
	private TradeService tradeService;
	
	/**
	 * Consumes trading operations and submits to the trading queue
	 * 
	 * POST /trade
	 * { 
	 * 	"userId": "134256", 
	 * 	"currencyFrom": "EUR", 
	 * 	"currencyTo": "GBP", 
	 * 	"amountSell": 1000, 
	 * 	"amountBuy": 747.10, 
	 * 	"rate": 0.7471, 
	 * 	"timePlaced" : "24­JAN­15 10:27:44", 
	 * 	"originatingCountry" : "FR" 
	 * }
	 * 
	 * @param	operation	Trading operation 
	 * @return 				Success or Fail
	 */
	@RequestMapping(
			value = "", 
			method = RequestMethod.POST, 
			consumes = "application/json",
			produces = "application/json")
	public @ResponseBody JsonResponse consume(@RequestBody TradeMessage tradeMessage) {
		try {
			tradeService.consumeTradeMessage(tradeMessage);
			
			return JsonResponse.success();
		} catch (TradeProcessorException e) {
			String error = "Error consuming trade message";
			logger.error(error, e);
			
			return JsonResponse.error(error);
		}
	}
}
