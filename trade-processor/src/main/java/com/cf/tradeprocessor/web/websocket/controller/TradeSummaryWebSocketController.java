package com.cf.tradeprocessor.web.websocket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.cf.tradeprocessor.model.TradeSummary;
import com.cf.tradeprocessor.service.TradeSummaryService;

@Controller
public class TradeSummaryWebSocketController {
	@Autowired
	private TradeSummaryService tradeSummaryService;
	
    @MessageMapping("/trade-summaries")
    @SendTo("/topic/trade-summaries")
    public Map<String, List<TradeSummary>> greeting() throws Exception {
        return tradeSummaryService.getTradeSummaries();
    }
}
