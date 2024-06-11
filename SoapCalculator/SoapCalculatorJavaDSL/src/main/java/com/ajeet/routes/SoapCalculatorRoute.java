package com.ajeet.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class SoapCalculatorRoute extends RouteBuilder {
    public static final String SERVICE_ADDRESS = "http://www.dneonline.com/calculator.asmx";
    public static final String ENDPOINT = "cxf:bean:cxfEndpoint";


    @Override
    public void configure() throws Exception {
        from("jetty:http://localhost:4000/api/v1/{id}?httpMethodRestrict=POST")
                .routeId("SoapCalculator")
                .doTry()
                .removeHeaders("*", "Content-Type|CamelHttpUri")
                .unmarshal().json(JsonLibrary.Jackson)
                .log("SoapCalculator001 :: After marshalling : ${body}")
                .bean("Utills", "checkEmptyOrNullValue")
                .bean("Utills", "setOperationNameAndSpace")
                .to("velocity:{{calculator}}").convertBodyTo(String.class)
                .log("SoapCalculator002 :: Soap request is : ${body}")
                .to(ENDPOINT)
                .log("SoapCalculator003 :: Soap request is : ${body}")
                .unmarshal().jacksonxml().marshal().json(JsonLibrary.Jackson)
                .log("SoapCalculator004 :: Result is : ${body}")

                .doCatch(IllegalArgumentException.class)
                .log("SoapCalculatorException001 :: Inside the exception block : ${exception.message}")
                .setProperty("expMsg", simple("${exception.message}"))
                .setHeader("Content-Type", simple("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, simple("400"))
                .bean("Utills", "exceptionMessage")
                .marshal().json(JsonLibrary.Jackson)
                .log("SoapCalculatorException002 :: message: ${body}")
                .endDoTry();
    }

}
