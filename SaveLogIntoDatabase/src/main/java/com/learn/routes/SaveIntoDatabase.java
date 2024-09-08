package com.learn.routes;

import com.learn.entities.LogginEvent;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class SaveIntoDatabase extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:saveintodb").routeId("SaveIntoDB")
                .log("Inside the SaveIntoDB")

                .process(ex -> {
                    String resBody= (ex.getProperty("resBody") == "" || ex.getProperty("resBody") ==null) ? "": ex.getProperty("resBody", String.class);
                    LogginEvent logginEvent = new LogginEvent();
                    logginEvent.setMessageId(ex.getProperty("messageId").toString());
                    logginEvent.setCreatedAt(ex.getProperty("createdAt").toString());
                    logginEvent.setUpdatedAt(ex.getProperty("updatedAt").toString());
                    logginEvent.setReqBody(ex.getProperty("reqBody").toString());
                    logginEvent.setResBody(resBody);
                    ex.getIn().setBody(logginEvent);
                })
                .to("jpa://com.learn.entities.LogginEvent")
                .log("Successfully inserted/updated..");
    }
}
