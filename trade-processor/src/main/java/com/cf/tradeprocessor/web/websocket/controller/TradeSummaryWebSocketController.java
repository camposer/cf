package com.cf.tradeprocessor.web.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.cf.tradeprocessor.service.TradeSummaryService;
import com.cf.tradeprocessor.web.rest.response.JsonResponse;

@Controller
public class TradeSummaryWebSocketController {
	@Autowired
	private TradeSummaryService tradeSummaryService;
	
    @MessageMapping("/trade-summaries")
    @SendTo("/topic/trade-summaries")
    public JsonResponse greeting() throws Exception {
        return JsonResponse.success(tradeSummaryService.getTradeSummaries());
    }
}
