package com.example.sumhobby.config;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TossPaymentConfig {
	
	private String testClientApiKey = "test_ck_5GePWvyJnrKnv4pgnx7VgLzN97Eo" ;
	
	private String testSecreKey = "test_sk_4vZnjEJeQVxXaL051vbVPmOoBN0k";

	private String successUrl = "http://localhost:1010/success";
	
	private String failUrl = "http://localhost:1010/fail";

}