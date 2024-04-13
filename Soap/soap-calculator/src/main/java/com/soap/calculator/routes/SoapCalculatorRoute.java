package com.soap.calculator.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.soap.calculator.config.AdditionalCxfPayloadConverters;

@Component
public class SoapCalculatorRoute extends RouteBuilder {
	public static final String SERVICE_ADDRESS = "http://www.dneonline.com/calculator.asmx";
	public static final String ENDPOINT = "cxf:bean:cxfEndpoint";
	
	
	@Autowired
	private CamelContext context;

	@Override
	public void configure() throws Exception {
		
		
		// For Addition
		from("timer:foo?period=50m")
				.to("velocity:file:{{calculator.add}}").convertBodyTo(String.class)
				.setHeader(CxfConstants.OPERATION_NAME, constant("Add"))
				.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://tempuri.org/"))
				.bean(AdditionalCxfPayloadConverters.class, "toCxfPayload(${body})").to(ENDPOINT)
				.log("Before convert to json ==> ${body} ").unmarshal().jacksonxml().marshal().json(JsonLibrary.Jackson)
				.log("After convert to json ==> ${body}");
		
		
		// For Subtraction
//		from("timer:foo?period=50m")
//		.removeHeaders("*")
//		.to("velocity:file:{{calculator.subtract}}").convertBodyTo(String.class)
//		.setHeader(CxfConstants.OPERATION_NAME, constant("Subtract"))
//		.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://tempuri.org/"))
//		.bean(AdditionalCxfPayloadConverters.class, "toCxfPayload(${body})").to(ENDPOINT)
//		.log("Before convert to json ==> ${body} ").unmarshal().jacksonxml().marshal().json(JsonLibrary.Jackson)
//		.log("After convert to json ==> ${body}");
		
		
		// For Multiplication
//		from("timer:foo?period=50m")		
//		.removeHeaders("*")
//		.to("velocity:file:{{calculator.multiply}}").convertBodyTo(String.class)
//		.setHeader(CxfConstants.OPERATION_NAME, constant("Multiply"))
//		.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://tempuri.org/"))
//		.bean(AdditionalCxfPayloadConverters.class, "toCxfPayload(${body})").to(ENDPOINT)
//		.log("Before convert to json ==> ${body} ").unmarshal().jacksonxml().marshal().json(JsonLibrary.Jackson)
//		.log("After convert to json ==> ${body}");
		
		// For Dividing
//		from("timer:foo?period=50m")
//		.removeHeaders("*")
//		.to("velocity:file:{{calculator.divide}}").convertBodyTo(String.class)
//		.setHeader(CxfConstants.OPERATION_NAME, constant("Divide"))
//		.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://tempuri.org/"))
//		.bean(AdditionalCxfPayloadConverters.class, "toCxfPayload(${body})").to(ENDPOINT)
//		.log("Before convert to json ==> ${body} ").unmarshal().jacksonxml().marshal().json(JsonLibrary.Jackson)
//		.log("After convert to json ==> ${body}");
	}

}
