
**Code Ref url:** <a href="https://github.com/ajeet-lab/springboot-camel-microservices/tree/master/DataSplitAndCollect" target="_blank" />Click Here</a>

# Split data of list of String
#### Post: http://localhost:8301/api/partition/listofmap
**Payload:**
```
{
    "partitionSize":2,
    "datas": ["a", "b", "c", "d", "e", "f", "g", "h" ,"i"]
}
```

### Create a route in spring dsl and add the code inside to camel-context.xml file
```
 <route id="splitListOfString">
    <from uri="jetty:http://localhost:8301/api/partition/listofstring??httpMethodRestrict=POST&amp;enableCORS=true" />
    <removeHeaders pattern="*" excludePattern="Content-Type" />
    <unmarshal><json library="Jackson" /></unmarshal>

    <setHeader name="partitionSize">
        <simple>${body['partitionSize']}</simple>
    </setHeader>
    <log message="splitListOfString :: partitionSize ${body['partitionSize']}" />
    <setBody>
        <simple>${body['datas']}</simple>
    </setBody>
    <bean ref="SplitAndCollectData"  method="splitListOfString"/>
    <log message="splitListOfString001 :: Partition size -> ${body.size()}" />
    <marshal><json library="Jackson" /></marshal>
</route>
```

### Create a class named SplitAndCollectData and add the code inside the class.
```
public void splitListOfString(Exchange ex){
    List<String> lists = ex.getIn().getBody(List.class);
    int listOfStringPartitionSize = (int) ex.getIn().getHeader("partitionSize");

    Collection<List<String>> partitionedListOfString = IntStream.range(0, lists.size()).boxed()
            .collect(Collectors.groupingBy(partition -> (partition / listOfStringPartitionSize), Collectors.mapping(elementIndex -> lists.get(elementIndex), Collectors.toList()))).values();
    log.info("splitListOfString :: ", partitionedListOfString);
    ex.getIn().setBody(partitionedListOfString);
}
```

**Response**
```
[["a","b"],["c","d"],["e","f"],["g","h"],["i"]]
```






# Split data of list of Map
#### Post: http://localhost:8301/api/partition/listofmap
**Payload:**
```
{
    "partitionSize": 2, "datas": [{"name":"xyz","age":30},{"name":"xyzwww","age":33},{"name":"cxzzq","age":34},{"name":"abc","age":36},{"name":"abcd","age":34},{"name":"abcgdf","age":38},{"name":"abcgdfrewewe","age":40}]
}
```

### Create a route in spring dsl and add the code inside to camel-context.xml file
```
 <route id="splitListOfMap">
    <from uri="jetty:http://localhost:8301/api/partition/listofmap??httpMethodRestrict=POST&amp;enableCORS=true" />
    <removeHeaders pattern="*" excludePattern="Content-Type" />
    <unmarshal><json library="Jackson" /></unmarshal>
    <setHeader name="partitionSize">
        <simple>${body['partitionSize']}</simple>
    </setHeader>
    <log message="splitListOfMap :: partitionSize ${body['partitionSize']}" />
    <setBody>
        <simple>${body['datas']}</simple>
    </setBody>
    <bean ref="SplitAndCollectData"  method="splitListOfMap"/>
    <log message="splitListOfMap001 :: Partition size -> ${body.size()}" />
    <marshal><json library="Jackson" /></marshal>
</route>
```

### Create a class named SplitAndCollectData and add the code inside the class.
```
 public void splitListOfMap(Exchange ex){
    List<Map<String, Object>> listOfMap = (List<Map<String, Object>>) ex.getIn().getBody();
    int listOfMapPartitionSize = (int) ex.getIn().getHeader("partitionSize");
    Collection<List<Map<String, Object>>> partitionedListOfMap = IntStream.range(0, listOfMap.size()).boxed().collect(Collectors.groupingBy(partition -> (partition / listOfMapPartitionSize), Collectors.mapping(elementIndex -> listOfMap.get(elementIndex), Collectors.toList()))).values();
    System.out.println("splitListOfMap :: "+ partitionedListOfMap);
    ex.getIn().setBody(partitionedListOfMap);
}
```

**Response**
```
[[{"name":"xyz","age":30},{"name":"xyzwww","age":33}],[{"name":"cxzzq","age":34},{"name":"abc","age":36}],[{"name":"abcd","age":34},{"name":"abcgdf","age":38}],[{"name":"abcgdfrewewe","age":40}]]
```