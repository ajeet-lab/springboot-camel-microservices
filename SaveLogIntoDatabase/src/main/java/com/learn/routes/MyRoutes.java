package com.learn.routes;

import com.learn.entities.LogginEvent;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class MyRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty").host("localhost").port(9092).bindingMode(RestBindingMode.auto);

        rest("/rest/v1.0")
                .post("save").to("direct:save");


        from("direct:save")
//                .setBody(simple("{\"name\": \"John Doe\"}"))
                .setHeader("Content-Type", simple("application/json"))
                .setProperty("messageId", simple("${date:now:yyyy-MM-dd'T'HH:mm:ss.SSS}"))
                .setProperty("createdAt", simple("${date:now}"))
                .setProperty("reqBody", simple("${body}"))
                .setProperty("updatedAt", simple("${date:now}"))
                .to("direct:saveintodb")
                .setBody(simple("${exchangeProperty.reqBody}"))
                .delay(simple("20000"))
                .setHeader("CamelHttpMethod", simple("GET"))
                .to("https://dummyjson.com/products/1?throwExceptionOnFailure=false&bridgeEndpoint=true")
                .setProperty("resBody", simple("${body}"))
                .setProperty("updatedAt", simple("${date:now}"))
                .to("direct:saveintodb")
                .setBody(simple("${exchangeProperty.resBody}"))
                .setHeader("Content-Type", simple("application/json"))
                .log("Final response ${body}");
    }



}
