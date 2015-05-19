package com.cf.tradeprocessor.test.integration;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cf.tradeprocessor.model.TradeMessage;
import com.cf.tradeprocessor.service.TradeConsumerService;
import com.cf.tradeprocessor.service.TradeConsumerServiceImpl;
import com.cf.tradeprocessor.web.rest.controller.TradeRestController;
import com.cf.tradeprocessor.web.rest.response.JsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TradeConsumerTest {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	
    @Autowired
    private WebApplicationContext ctx;
	@Autowired
	private TradeRestController tradeRestController;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		mapper = new ObjectMapper();
	}

	@Test
	public void test() throws Exception {
		TradeMessage tradeMessage = new TradeMessage();
		tradeMessage.setUserId("134256");
		tradeMessage.setCurrencyFrom("EUR");
		tradeMessage.setCurrencyTo("GBP");
		tradeMessage.setAmountSell(1000d);
		tradeMessage.setAmountBuy(747.1);
		tradeMessage.setRate(0.7471);
		tradeMessage.setTimePlaced("24­JAN­15 10:27:44");
		tradeMessage.setOriginatingCountry("FR");

		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, tradeMessage);

		MvcResult result = mockMvc.perform(
				post("/trades")
					.contentType(MediaType.APPLICATION_JSON)
					.content(sw.toString()))
					.andExpect(status().isOk())
				.andReturn();

		String responseBody = result.getResponse().getContentAsString();
		
		JsonResponse jsonResponse = mapper.readValue(responseBody, JsonResponse.class);
		assertTrue(jsonResponse.getSuccess().equals(true));
	}

	@Configuration
    @EnableWebMvc	
	@ImportResource("classpath:/spring-test/integration.xml")
	public static class Config {
		@Bean
		public TradeConsumerService tradeConsumerService() {
			return new TradeConsumerServiceImpl();
		}

		@Bean
		public TradeRestController tradeRestController() {
			return new TradeRestController();
		}
	}
}
