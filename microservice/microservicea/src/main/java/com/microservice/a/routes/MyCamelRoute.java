package com.microservice.a.routes;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyCamelRoute extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;

	@Autowired
	private SimpleLoggingProcessingComponent loggingProcessor;

	@Override
	public void configure() throws Exception {
		
//		1. Bean used for both Processor and Transform
//		2. Proccessor calling throw process

		
		from("timer:first-timer")
//		.transform().constant("Current time is: "+ LocalDateTime.now())
//		.bean("getCurrentTimeBean") // Can Calling bean without @Autowired annotation
				.bean(getCurrentTimeBean, "getCurrentTime") // Calling bean with @Autowired annotation
				.log("Before calling logger: ${body}")
				.bean(loggingProcessor)
				.log("after calling logger: ${body}")
				.process(new SimpleLoggingProcesser())
				.to("log:first-timer");

	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Current time is: " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(Exchange ex, String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcesser implements Processor{
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcesser : {}", exchange.getMessage().getBody());
		
	}
}