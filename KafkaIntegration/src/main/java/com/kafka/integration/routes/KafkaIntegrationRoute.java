package com.kafka.integration.routes;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class KafkaIntegrationRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("jetty")
                .host("localhost").port("9902")
                .bindingMode(RestBindingMode.auto);

        rest("/rest/api/v1").post("/kafka/push-into-kafka").to("direct:push-into-kafka");

      from("direct:push-into-kafka")
              .log("Hit received at ${date:now} and body -> ${body}")
              .marshal().json(JsonLibrary.Jackson)
              .to("kafka:my-topic?brokers=localhost:9092")
              .setBody(simple("{\"message\":\"data pushed into kafka queue\"}"))
              .unmarshal().json(JsonLibrary.Jackson);


        from("kafka:my-topic?brokers=localhost:9092&groupId=demo-group")
                .log("Message received from Kafka: ${body}");
    }
}
