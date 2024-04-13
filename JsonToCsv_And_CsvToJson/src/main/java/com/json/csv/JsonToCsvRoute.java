package com.json.csv;

import org.apache.camel.builder.RouteBuilder;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonToCsvRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        DataFormat bind = new BindyCsvDataFormat(User.class);
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(User.class);
        jacksonDataFormat.useList();
        jacksonDataFormat.setUnmarshalType(User.class);


        // START CREATE JSON TO CSV ✅
        //from("file:work?fileName=input.json")
        from("jetty:http://localhost:9000/api/jsontocsv?httpMethodRestrict=POST")
                .unmarshal(jacksonDataFormat)
                .marshal().bindy(BindyType.Csv,User.class)
                .setHeader("CamelFileName", simple("output_${date:now:yyyy-MM-dd-HH-mm-ss}.csv"))
                .to("file:work/csv")
                .setBody(simple("{isSuccess:true, message:Successfully created CSV}"))
                .log("JavaDSL_JsonToCsv_001 :: Successfully created CSV");
        // END CREATE JSON TO CSV


        // START CREATE CSV TO JSON ✅
        from("file:work/output?fileName=output_2024-04-13-20-28-22.csv&noop=true")
                .unmarshal(bind)
                .log("JavaDSL_CsvToJson_001 :: after unmarshal ==> ${body}")
                .marshal().json(JsonLibrary.Jackson, true)
                .setHeader("CamelFileName", simple("output_${date:now:yyyy-MM-dd-HH-mm-ss}.json"))
                .to("file:work/json")
                .log("JavaDSL_CsvToJson_002 :: Successfully created JSON");
        // END CREATE CSV TO JSON
    }
}
