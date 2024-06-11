#SOAPCALCULATORSPRINGDSL


## WSDL CONFIGURATION USING cxf:ENDPOINT OR BEANS
### Using cxf:Endpoint
```
 <cxf:cxfEndpoint id="calculatorEndpoint"
     address="http://www.dneonline.com/calculator.asmx"
     wsdlURL="http://www.dneonline.com/calculator.asmx?wsdl"
     serviceClass="org.tempuri.CalculatorSoap"
     xmlns:s="http://tempuri.org/"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     endpointName="CalculatorSoap">
    <cxf:properties>
        <entry key="dataFormat" value="PAYLOAD" />
    </cxf:properties>
</cxf:cxfEndpoint>
```


### Using Bean
```
 <bean id="calculatorEndpoint" class="org.apache.camel.component.cxf.CxfEndpoint">
        <property name="address" value="http://www.dneonline.com/calculator.asmx"/>
        <property name="wsdlURL" value="http://www.dneonline.com/calculator.asmx?wsdl"/>
        <property name="serviceClass" value="org.tempuri.CalculatorSoap"/>
        <property name="dataFormat" value="PAYLOAD"/>
    </bean>
```