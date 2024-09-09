package com.learn.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XmlToXsltRentalProperties extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:rentalproperties")
                .log("Body is : ${body}")
                .to("xslt:./xslt/rentalProperties.xsl")
                .log("${body}");
    }
}
