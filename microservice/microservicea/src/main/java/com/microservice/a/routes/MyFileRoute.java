package com.microservice.a.routes;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFileRoute extends RouteBuilder{
	
	@Autowired
	private DeciderBeen deciderBeen;

	@Override
	public void configure() throws Exception {
		
		from("file:files/input")
		
		.choice()
			.when(method(deciderBeen))
				.log("deciderBeen bean is called")
			.when(simple("${file:ext} ends with 'xml'"))
				.log("XML contain in file => ${body}")
			.when(simple("${file:ext} contains 'json'"))
				.log("JSON contain in file => ${body}")
			.otherwise()
				.log("not contain xml or json in the file ${body}")
		.end()
		.to("file:files/output")
		.to("direct:log-files-value");
		
		from("direct:log-files-value")
		.log("${messageHistory}")
		.log("Headers ${in.headers}")
		.log("Headers ${file:size}")
		.log("Headers ${file:modified}");
		
	}

}

@Component
class DeciderBeen{
	Logger logger = LoggerFactory.getLogger(String.class);
	
	public Boolean isConditionMet(@Body String body, 
			@Headers Map<String, String> headers,
			@Header("CamelFileName") String header,
			@ExchangeProperties Map<String, String> exchangeProperties ) {
		logger.info("isConditionMet Body :: {} ", body);
		logger.info("isConditionMet Headers :: {} ", headers);
		logger.info("isConditionMet Header :: {} ", header);
		logger.info("isConditionMet ExchangeProperties :: {} ", exchangeProperties);
		return true;
	}
}