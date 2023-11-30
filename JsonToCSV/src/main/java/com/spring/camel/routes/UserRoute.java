package com.spring.camel.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.spring.camel.entities.Todos;
import com.spring.camel.messages.CamelMessages;
import com.spring.camel.processor.CamelProcessor;

@Component
public class UserRoute extends RouteBuilder {


	@Autowired
	private CamelProcessor camelProcessor;
	
	@Autowired
	private CamelMessages camelMessages;

	@Override
	public void configure() throws Exception {
		DataFormat bind = new BindyCsvDataFormat(Todos.class);
		JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(Todos.class);
        jacksonDataFormat.useList();
        jacksonDataFormat.setUnmarshalType(Todos.class);
		
		restConfiguration().component("servlet").port(8080).host("localhost").bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").enableCORS(true).apiContextPath("/api-doc")
				.apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0");

		rest("/todos").consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)

				.get()
				.route()
				.bean("todosService", "getAllTodos")
				.marshal().json()
				.convertBodyTo(String.class)
				.setExchangePattern(ExchangePattern.InOnly)
				.to("activemq:todoqueue")
				.removeHeader("*")
				.setHeader("Content-Type", simple("application/json"))
				.bean("camelMessages", "afterDataPushInQueue");
				
 
//				.get("/{id}").outType(Todos.class)
//				.param().name("id").type(path).dataType("integer").endParam()
//				.responseMessage().code(200).message("Todos successfully returned").endResponseMessage()
//				.to("bean:todosService?method=todoGetById(${header.id})")
//		
//				.put("/{id}")
//				.route().marshal().json()
//				.unmarshal(camelProcessor.getJacksonDataFormat(Todos.class))
//				.to("bean:todosService?method=updateTodo(${body}, ${header.id})");
					
					

	   from("activemq:todoqueue")
	   .log("Data body recived ${body}")
	   .unmarshal(jacksonDataFormat)
	   .marshal().bindy(BindyType.Csv, Todos.class)
	   .convertBodyTo(String.class)
	   .to("file:E:\\projects\\eclipse-workspace\\springcamel\\work\\output?fileName=Todos.csv")
	   .log("Csv file successfully create");
	}
	
	
	

}
