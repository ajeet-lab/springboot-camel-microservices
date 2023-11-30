package com.microservice.a.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestApiConsumerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		restConfiguration().port(8000).host("localhost");
		
		from("timer:foo?period=10000")
		.setHeader("from", ()-> "EURO")
		.setHeader("to", ()-> "USD")
		.log(LoggingLevel.INFO, "Reciver route ${body}")
		.to("rest:get:/currency-exchange/from/{from}/to/{to}")
		.log(LoggingLevel.INFO, "After calling rest api: ${body}");
		
	}

}
