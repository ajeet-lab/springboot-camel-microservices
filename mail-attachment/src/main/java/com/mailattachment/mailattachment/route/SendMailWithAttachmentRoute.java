package com.mailattachment.mailattachment.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.mailattachment.mailattachment.processor.MyMailProcessor;

@Component
public class SendMailWithAttachmentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
       // from("timer:foo?period=1m")
        // .setHeader("To").simple("{{to}}")
        // .setHeader("Subject").simple("{{subject}}")
        // .setHeader("Cc").simple("{{cc}}")
        // .setBody().simple("This is test mail")
        // .to("smtps://smtp.gmail.com?username=iredefinedapi@gmail.com&password=qewfkpdosnwshtzz")
        // .log("Mail send successfully");


        from("timer:foo?period=1m")        
        .setHeader("endpoint").simple("smtps://smtp.gmail.com?username=iredefinedapi@gmail.com&password=qewfkpdosnwshtzz")
        .setHeader("To").simple("{{to}}")
        .setHeader("Subject").simple("{{subject}}")
        .setHeader("Cc").simple("{{cc}}")
        .setBody().simple("This is test mail only")
        .bean(new MyMailProcessor())
        .to("smtps://smtp.gmail.com?username=iredefinedapi@gmail.com&password=qewfkpdosnwshtzz")
        .log("Mail send sccefully");
    }
    
}
