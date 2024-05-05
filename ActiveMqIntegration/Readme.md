### POST : http://localhost:9002/api/v1/amq/queue
**Payload: **{"name":"ajeet","age":"30","email":"ajeet@gmail.com", "status":"Pending"}


### POST : http://localhost:9002/api/v1/amq/selectorqueue
**Payload: **{"name":"ajeet","age":"30","email":"ajeet@gmail.com", "status":"Pending"}



#### POST : http://localhost:9002/api/v1/amq/topicqueue
**Payload: **{"name":"ajeet","age":"30","email":"ajeet@gmail.com", "status":"Pending"}



#### Dependencies
```
 <dependency>
      <groupId>org.apache.camel.springboot</groupId>
      <artifactId>camel-activemq-starter</artifactId>
    </dependency>
```
