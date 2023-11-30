package com.microservice.b.routes;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.b.entities.CurrencyExchange;

@Component
public class ActiveMqRecieverRoute extends RouteBuilder {
	
	@Autowired
	private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
	
	@Autowired
	private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

	@Override
	public void configure() throws Exception {
//		from("activemq:my-activemq-queue")
//		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class) // Convert into java pojo object
//		.bean(myCurrencyExchangeProcessor) // Message Processor
//		.bean(myCurrencyExchangeTransformer) // Message Transformer
//		.to("log:reciever-activemq-queue");
		
		
		// Json to XML
//		from("activemq:my-activemq-xml-queue")
//		.unmarshal().jacksonXml(CurrencyExchange.class)
//		.marshal().json()
//		.to("log:reciever-activemq-queue");
		
		//Splitter message reciever from queue
		from("activemq:split-csv-queue")
		.to("log:split-reciever-csv-queue");
		
		
	}

}

@Component
class MyCurrencyExchangeProcessor{
	Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);
	
	public void processMessage(CurrencyExchange currencyExchange) {
		logger.info("Do some processing with currencyExchange.getConversionMultiple() value which {}", currencyExchange.getConversionMultiple());
	}
}

@Component
class MyCurrencyExchangeTransformer{
	public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
		currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));	
		return currencyExchange;
	}
}
