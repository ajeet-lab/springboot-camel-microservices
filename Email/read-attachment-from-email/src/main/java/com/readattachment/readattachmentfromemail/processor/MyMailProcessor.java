package com.readattachment.readattachmentfromemail.processor;
import java.io.FileOutputStream;
import java.util.Map;
import javax.activation.DataHandler;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@AutoConfiguration
public class MyMailProcessor implements Processor {

   
    

    @Override
    public void process(Exchange exchange) throws Exception {
        //String pathName = (String) exchange.getProperty("${app.readAttachmentDir}");
        String pathName = exchange.getContext().resolvePropertyPlaceholders("{{app.readAttachmentDir}}");
          
        AttachmentMessage attachmentMessage = exchange.getIn(AttachmentMessage.class);
        Map<String, DataHandler> attachments = attachmentMessage.getAttachments();

        if(attachments.size() > 0){
            for(String name:attachments.keySet()){
                DataHandler dh = attachments.get(name);
                // get the file name
                String fileName = dh.getName();
                // get the content and convert it to byte[]
                byte[] data = exchange.getContext().getTypeConverter().convertTo(byte[].class, dh.getInputStream());

                FileOutputStream out = new FileOutputStream(pathName+"\\"+fileName);
                out.write(data);
                out.flush();
                out.close();
            }
        }

        
    }
    
}
