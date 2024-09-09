package com.learn.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component
public class XmlToPDFStudent extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:xmltopdf")
                .log("Hit received at ${date:now}")
                .to("velocity:./xslt/Student.vm")
                .log("body ${body}")
                .to("xslt:./xslt/Student.xsl")
                .setHeader("Content-Type", constant("text/html"))
                .process("XmlToPdfProcessor")
                .setHeader("Content-Type", constant("application/pdf"))
                .to("file:output?fileName=document.pdf")
                .log("Final response is: ${body}");
    }
}
