package com.microservice.a.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqSenderRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
//		from("file:files/input")
//		.log("Json body: ${body}")
//		.to("activemq:my-activemq-queue");
		
		from("file:files/xml")
		.log("Json body: ${body}")
		.to("activemq:my-activemq-xml-queue");
		
	}
	
	
}
