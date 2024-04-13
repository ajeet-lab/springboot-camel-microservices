package com.read.processor;

import java.io.FileOutputStream;
import java.util.Map;
import javax.activation.DataHandler;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyMailProcessor implements Processor {
	Logger logger = LoggerFactory.getLogger(MyMailProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
	    AttachmentMessage attachmentMessage = exchange.getIn(AttachmentMessage.class);		    
	    Map<String, DataHandler> attachments = attachmentMessage.getAttachments();
	    if (attachments.size() > 0) {
	        for (String name : attachments.keySet()) {
	            DataHandler dh = attachments.get(name);
	            String filename = dh.getName();
	            byte[] data = exchange.getContext().getTypeConverter()
	                              .convertTo(byte[].class, dh.getInputStream());
	            FileOutputStream out = new FileOutputStream("work\\output\\"+filename);
	            out.write(data);          
	            out.flush();
	            out.close();
	        }
	    }
	}

}