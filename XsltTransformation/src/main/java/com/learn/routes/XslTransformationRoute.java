package com.learn.routes;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XslTransformationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().host("localhost").port(9092);

        rest("/rest/v1.0")
                .post("/xmltoxslt/rentalproperties")
                .to("direct:rentalproperties")
                .post("/jsontoxmltoxslt/certificate")
                .to("direct:jsontoxmltoxslt")
                .get("/xmltohtml/student")
                .to("direct:xmltohtml")
                .get("/xmltopdf/student")
                .to("direct:xmltopdf")

        ;
    }
}
