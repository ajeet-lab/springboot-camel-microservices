package com.microservice.a.routes;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class EipPatternsRoute extends RouteBuilder {

	@Autowired
	private SplitterComponent splitter;
	
	@Override
	public void configure() throws Exception {	
		// 1. Pipeline pattern - By default
		// 2. Multicasting pattern
		// 3. Splitter pattern
		
		// 2. Multicasting pattern
//		from("timer:multicast-pattern?period=10000")
//		.multicast()
//		.to("log:Get message1", "log:Get message2", "log:Get message3");
		
		// 3.1 Splitter pattern using body
//		from("file:files/csv")
//		.unmarshal().csv()
//		.convertBodyTo(String.class)
//		.split(body())
//		.to("log:split-csv-queue")
//		.to("activemq:split-csv-queue");
		
		
		// 3.2 Splitter pattern using comma
//		from("file:files/csv")
//		.convertBodyTo(String.class)
//		.split(body(), ",")
//		.to("log:split-csv-queue")
//		.to("activemq:split-csv-queue");
		
		// 3.3 Splitter pattern using method
		from("file:files/csv")
		.unmarshal().csv()
		.convertBodyTo(String.class)
		.split(method(splitter))
		.to("log:split-csv-queue")
		.to("activemq:split-csv-queue");
		
	}

}

@Component
class SplitterComponent{
	public List<String> splitInput(String body){
		return List.of("ABC", "DEF", "GHK");
	}
}
