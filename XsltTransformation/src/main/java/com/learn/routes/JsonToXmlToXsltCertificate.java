package com.learn.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class JsonToXmlToXsltCertificate extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:jsontoxmltoxslt")
                .unmarshal().json(JsonLibrary.Jackson)
                .marshal().custom("jsontoxmlRootNameCertificate")
                .setBody(xpath("/Certificate/Certificate"))
                .to("xslt:./xslt/Certificate.xsl")
                .log("Xml to xslt ${body}");
    }
}
