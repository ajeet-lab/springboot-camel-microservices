package com.readattachment.readattachmentfromemail.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.readattachment.readattachmentfromemail.processor.MyMailProcessor;


@Component
public class ReadAttachmentRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        // TODO Auto-generated method stub
        from("imaps://imap.gmail.com?username=iredefinedapi@gmail.com&password=qewfkpdosnwshtzz")
        .process(new MyMailProcessor())
        .to("log:message");
    }
    
}
