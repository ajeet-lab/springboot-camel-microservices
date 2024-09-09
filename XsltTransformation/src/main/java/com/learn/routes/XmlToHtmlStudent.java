package com.learn.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component
public class XmlToHtmlStudent extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:xmltohtml")
                .log("Hit received ${body}")
                .to("velocity:./xslt/Student.vm")
                .log("body ${body}")
                .to("xslt:./xslt/Student.xsl")
                .setHeader("Content-Type", constant("text/html"))
                .log("Final response is: ${body}");
    }
}
