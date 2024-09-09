package com.learn;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Component(value= "XmlToPdfProcessor")
public class XmlToPdfProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String htmlContent = exchange.getIn().getBody(String.class);
        htmlContent = htmlContent.replace(
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">",
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"
        );
        System.out.println("Body is " + htmlContent);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(pdfOutputStream);

        exchange.getIn().setBody(pdfOutputStream.toByteArray());
    }
}
