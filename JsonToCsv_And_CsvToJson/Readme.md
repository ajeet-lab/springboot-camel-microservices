## I have created this project for data conversion between JSON and CSV using two REST DSLs: Java DSL and Spring DSL.


<p><b>API JSON to CSV : </b> http://localhost:9000/api/jsontocsv</p>
<b>Payload : </b><br />

```
[
    {
      "id": 1,
      "todo": "Do something nice for someone I care about",
      "completed": true,
      "userId": 26
    },
    {
      "id": 2,
      "todo": "Memorize the fifty states and their capitals",
      "completed": false,
      "userId": 48
    },
    {
      "id": 3,
      "todo": "Watch a classic movie",
      "completed": false,
      "userId": 4
    }
]
```
<p><b>API CSV to JSON : </b> file:work/output?fileName=Give_Your_FileName&amp;noop=true</p>

```
userId,id,todo,completed
26,1,Do something nice for someone I care about,true
48,2,Memorize the fifty states and their capitals,false
4,3,Watch a classic movie,false
```


### Required Dependecies

```
<dependency>
  <groupId>org.apache.camel.springboot</groupId>
  <artifactId>camel-jackson-starter</artifactId>
</dependency>

<dependency>
  <groupId>org.apache.camel.springboot</groupId>
  <artifactId>camel-bindy-starter</artifactId>
</dependency>
```


### Spring DSL configuration

* #### JSON to CSV

```
<route>
    <from uri="jetty:http://localhost:9000/api/jsontocsv?httpMethodRestrict=POST"/>
    <convertBodyTo type="String" />
        <unmarshal >
            <json library="Jackson" unmarshalType="com.json.csv.User" useList="true" />
        </unmarshal>
        <log message="Afer unmarshling ====> ${body}" />
        <marshal>
            <bindy type="Csv" classType="com.json.csv.User" />
        </marshal>
        <setHeader name="CamelFileName">
            <simple>output_${date:now:yyyy-MM-dd-HH-mm-ss}.csv</simple>
        </setHeader>
        <to uri="file:work/output"/>
        <setBody>
            <simple>{"isSuccess":true, "message":"Successfully created CSV"}</simple>
        </setBody>
</route>
```

* #### CSV to JSON

```
<route>
    <from uri="file:work/output?fileName=Your_CSVFileName&amp;noop=true"/>
    <log message="data received from file" />
        <unmarshal>
            <bindy type="Csv" classType="com.json.csv.User" />
        </unmarshal>
        <log message="after CSV unmarshling ${body}" />
        <marshal >
            <json library="Jackson"  useList="true" />
        </marshal>
         <log message="after JSON unmarshling ${body}" />
        <convertBodyTo type="String" />
        <setHeader name="CamelFileName">
            <simple>input_${date:now:yyyy-MM-dd-HH-mm-ss}.json</simple>
        </setHeader>
        <to uri="file:work/output"/>
       <log message="Successfully created json" />
</route>
```
<p>In the above code, I have used <b>unmarshalType</b> inside the unmarshal and <b>classType</b> inside the Bindy EIP, both of which take a <b>User class</b> reference for unmarshalling and marshalling. <b>User class</b> defined below</p>

* <b>User.class - </b> It will be use for both Rest DSL Java and Spring

```
package com.json.csv; // Change your package name


import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", skipFirstLine = true, generateHeaderColumns = true)
public class User {
    @DataField(pos = 1, columnName = "userId")
    private int userId;
    @DataField(pos = 2, columnName = "id")
    private int id;

    @DataField(pos = 3, columnName = "todo")
    private String todo;

    @DataField(pos = 4, columnName = "completed")
    private Boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

```


### Java DSL configuration

* #### Configuration in the Java DSL route inside the configure method

```
DataFormat bind = new BindyCsvDataFormat(User.class);
JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(User.class);
jacksonDataFormat.useList();
jacksonDataFormat.setUnmarshalType(User.class);
```

* #### JSON to CSV

```
//from("file:work?fileName=input.json")
from("jetty:http://localhost:9000/api/jsontocsv?httpMethodRestrict=POST")
    .unmarshal(jacksonDataFormat)
    .marshal().bindy(BindyType.Csv,User.class)
    .setHeader("CamelFileName", simple("output_${date:now:yyyy-MM-dd-HH-mm-ss}.csv"))
    .to("file:work/csv")
    .setBody(simple("{isSuccess:true, message:Successfully created CSV}"))
    .log("JavaDSL_JsonToCsv_001 :: Successfully created CSV");
```


* #### CSV to JSON
```
from("file:work/output?fileName=Your_CSVFileName&noop=true")
    .unmarshal(bind)
    .log("JavaDSL_CsvToJson_001 :: after unmarshal ==> ${body}")
    .marshal().json(JsonLibrary.Jackson, true)
    .setHeader("CamelFileName", simple("output_${date:now:yyyy-MM-dd-HH-mm-ss}.json"))
    .to("file:work/json")
    .log("JavaDSL_CsvToJson_002 :: Successfully created JSON");
```