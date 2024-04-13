package com.read.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.read.processor.MyMailProcessor;

@Component
public class CamelRoute extends RouteBuilder{
	@Override
	public void configure() throws Exception {	
		from("imaps://imap.gmail.com?username=xxxxx@gmail.com&password=xxxxxxxx"
	    + "&delete=false")
		.process(new MyMailProcessor())
		.to("log:message");
			
		from("file:work/output")		
		.to("activemq:file-read")
		.log("File pushed successfully in Queue");
	}

}
