package com.mailattachment.mailattachment.processor;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;

public class MyMailProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // TODO Auto-generated method stub
        String pathName = "E:\\projects\\camel-project\\springboot-apache-projects\\mail-attachment\\work\\files\\1000.json";
        AttachmentMessage attMsg = exchange.getIn(AttachmentMessage.class);
        attMsg.addAttachment("1000.json", new DataHandler(new FileDataSource(new File(pathName))));
    }
    
}
