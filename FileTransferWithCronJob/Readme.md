## Creating a cron job applicaion
   Creating a cron job applicaion using timer, sechedule and cron components and also implemented RoutePolicy for startTime and endTime in route
   

* ### Timer component
```
<route>
    <from uri="timer:myTimer?period=5000&amp;fixedRate=true&amp;delay=10000&amp;repeatCount=5"/>
    <pollEnrich>
        <!-- Read file from local system -->
        <simple>file:work/input</simple>
    </pollEnrich>
    <log message="Read file using Poll Enrich and body is : ${body}, FileName is : ${header.CamelFileName}" />
</route>
```
<b>fixedRate=true:</b> This will ensure that the timer fires every 5 seconds, even if the previous exchange hasn't completed.<br />
<b>delay=2000:</b> This will start the timer after an initial delay of 2 seconds.<br />
<b>repeatCount=5:</b> This will stop the timer after it has fired 5 times.


* ### Schedule component
```
<route>
    <from uri="scheduler://foo?delay=60000"/>
    <log message="Scheduler route using 'camel-scheduler-starter' depedency" />
</route>
```
This component is similar to the Timer component, but it offers more functionality in terms of scheduling.
  Also, this component uses JDK ScheduledExecutorService, whereas the timer uses a JDK Timer.

* ### Cron component
```
 <route>
    <from uri="cron:tab?schedule=0/1+*+*+*+*+?"/>
    <setBody>
        <constant>Cron :: Create a cron job using 'camel-cron-starter'</constant>
    </setBody>
    <to uri="log:info"/>
</route>
```


* ### Using routePolicy
```
 <bean id="startPolicy" class="org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy">
    <property name="routeStartTime" value="0 57 07 1/1 * ? *"/>
    <property name="routeStopTime" value="0 58 07 1/1 * ? *"/>
</bean>
```
Here, we have defined the start time and stop time for the route policy.

```
<route id="fileProcessWithRoutePolicy" routePolicyRef="startPolicy" autoStartup="false">
    <from uri="file:work/input"/>
    <log message="Create a cron job using routePolicy ${date:now}" />
    <to uri="file:work/output"/>
</route>
```
<b>autoStartup=false:</b> The route will not start automatically when we run the program.<br />
<b>routePolicyRef="startPolicy":</b> The route will start according to the route policy we defined earlier.
