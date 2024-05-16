
# Jolt
#### Request - 1 : http://localhost:9091/api/jolt/simplejsontojson1
```
{
  "firstname": "Alice",
  "lastname": "Joe",
  "email": "Alice@gmail.com",
  "phone": 1234567891,
  "house": "40621, Delhi",
  "pincode": 110019,
  "age": 30
}
```
In Jolt transformation, we assume a **Left Hand Side (LHS)** and a **Right Hand Side (RHS)**. **In the LHS, the key must be the same as the key in the Jolt schema**. This means that the keys in your input JSON should match the keys specified in your Jolt transformation schema for the transformation to be applied correctly.

#### Jolt Schema for Request - 1
```
[
  {
    "operation": "shift",
    "spec": {
      "firstname": "person.firstname", //It means that the value of the "firstname" key is extracted and placed inside the "firstname" field within the "person" object in the output JSON.
      "lastname": "person.lastname",
      "email": "person.contact.email", //It means that the value of the **"email"** key is extracted and placed inside the **"email"** field within the **"contact"** object within the **"person"** object in the output JSON
      "phone": "person.contact.phone",
      "age": "person.user.age",
      "house": "person.address", // It means that the value of the "address and pincode" key is extracted and placed in an array inside the "address" field within the "person" object in the output JSON.
      "pincode": "person.address" //
    }
  }
]

```
Here, we've created a Jolt schema using the shift operation and **extract values from the request's identical keys**, then placing these values inside a **"person"** object. You can see the result below:
* **Let's Understand below how it is work.**
  * **"firstname":person.firstname"** :- It means that the value of the **"firstname"** key is extracted and placed inside the **"firstname"** field within the **"person"** object in the output JSON. Result is - **{"person" : {"firstname" : "Alice"}}.**
  * **"email":person.contact.email"** :- It means that the value of the **"email"** key is extracted and placed inside the **"email"** field within the **"contact"** object within the **"person"** object in the output JSON. Result is - **{"person" : "contact" : {"email" : "Alice@gmail.com"}}**
  * **"house": "person.address","pincode": "person.address"** :- It means that the value of the **"address** and **pincode"** key is extracted and placed in **an array** inside the **"address"** field within the **"person"** object in the output JSON. Because we've placed both the values of **"address"** and **"pincode"** keys inside the **"address"** field within the "person" object. Result is - **{"person" : "address" : [ "40621, Delhi", 110019 ]}**

#### Result of Request -1 
```
{
  "person" : {
    "firstname" : "Alice",
    "lastname" : "Joe",
    "contact" : {
      "email" : "Alice@gmail.com",
      "phone" : 1234567891
    },
    "user" : {
      "age" : 30
    },
    "address" : [ "40621, Delhi", 110019 ]
  }
}
```


#### Request - 2 : http://localhost:9091/api/jolt/simplejsontojson1
![Example Image](jolt2.png)

```
{
  "user": {
    "firstname": "Alice",
    "lastname": "Joe",
    "email": "Alice@gmail.com",
    "phone": 1234567891,
    "house": "40621, Delhi",
    "pincode": 110019,
    "age": 30,
    "user2": {
      "user1": "ajeet"
    }
  }
}
```
Here, we have modified the request by encapsulating it within a **"user"** object and introducing new fields such as **"user2": { "user1": "ajeet" }.**


#### Jolt Schema for Request - 2
```
[
  {
    "operation": "shift",
    "spec": {
      "user": {
        "firstname": "person.firstname",
        "lastname": "person.lastname",
        "email": "person.contact.email",
        "phone": "person.contact.phone",
        "age": "person.user.age",
        "house": "person.address",
        "pincode": "person.address",
        "user2": {
          "user1": "person.user1"
        }
      }
    }
  }
]
```
When extracting data from the request, we first extract the **"user"** object, highlighted in yellow, and then retrieve data from the new field, highlighted in pink, You can see the highlighted colors in the image above, like **"user2": { "user1": "person.user1" }**. Result is - **"user1" : "ajeet"**

#### Result of Request -2
```
{
  "person" : {
    "firstname" : "Alice",
    "lastname" : "Joe",
    "contact" : {
      "email" : "Alice@gmail.com",
      "phone" : 1234567891
    },
    "user" : {
      "age" : 30
    },
    "address" : [ "40621, Delhi", 110019 ],
    "user1" : "ajeet"
  }
}
```



## One to Many and put value in an array
You can pick the value from particulate node and put into the multiple node.
#### Request - 3 : http://localhost:9091/api/jolt/simplejsontojson1
```
{
  "rating": {
    "primary": {
      "value": 3,
      "max": 5
    }
  }
}
```
#### Jolt Schema for Request - 3
```
[
  {
    "operation": "shift",
    "spec": {
      "rating": {
        "primary": {
          "value": ["person.v1", "person.v2"], //Here, we assign the same value to multiple nodes using an array.
          "max": "person.v3[]", //Here, we assign a value to a node in an array.
          "min": "person.v4[2]" //Suppose we want to assign a value to a particular index in an array. We specify the index value within square brackets
        }
      }
    }
  }
]
```
  * **"value": ["person.v1", "person.v2"] :** Here, we assign the same value to multiple nodes using an array.
  * **"max": "person.v3[]" :**  Here, we assign a value to a node in an array. The result is-**"v3" : [ 5 ]**
  * **"min": "person.v4[2]":**  Suppose we want to assign a value to a particular index in an array. We specify the index value within square brackets. The result is -  **"v4" : [ null, null, 1 ]**

#### Result of Request -3
```
{
  "person" : {
    "v1" : 3,
    "v2" : 3,
    "v3" : [ 5 ],
    "v4" : [ null, null, 1 ]
  }
}
```

## Request in an array
#### Request - 4 : http://localhost:9091/api/jolt/arrayjsontojson
```
[
{"exists or not":"true","application number":"APPL10754044","neo cif id":"GLBCUST0001000054643","creation_time_stamp":"11-MAY-17 01.51.00.390000000 PM","last_updated_time_stamp":"10-SEP-23 12.41.22.817000000 PM","customer number":"CUST100004567","product":"Business Loan Unsecured","product type":"Personal Finance","channel":"DSA\/Dealer","loan purpose":"Business Working Capital","scheme code":"BL_LOWTI","rate":23.05,"loan amount":665000,"tenure":36,"payment frequency":"Monthly","emi":"INR~25759","currency":"INR","loan stage name":"Post Disbursal","current processing stage":"CREDIT_APPROVAL","under writer result":"Approve","fi result":"Residence Verification - Neutral","applicant type":1,"deviation raised":"NegCIBIL, OwnrPrf1","first name":"RAFIQUDEEN","last name":"RS","date of birth":"1962-09-12","occupation type":"Salaried","phone number":"0141-04112047","mobile no":"98456645456","email id":"SALHGU345S@GMAIL.COM,SALASUHFFHUJ@GMAIL.COM","address":"E-145 NIRMA AJMER ROAD JAIPUR   Rajasthan Jaipur India","pan_number":"AMF66001N","loan branch code":"365","company name":"SALJYBH LAMINATION","dsa code":"8046879","contract no":"TCFBL03650000134323329","contract date":"30-JUN-17 03.24.16.170000000 PM","fi remarks\/fi negative reason":"Residence Verification - OFFICE FI NOT FIRED SO NOT CONDUCTED","rcu status":"Initiated","sanction amount":665000,"sanction date":"29-MAY-17 01.31.50.430000 PM","disbursement amount":665000,"disbursement date":"29-JUN-17 06.30.00.000000000 PM","login acceptance":"Approved","login acceptance date":"11-MAY-17 10.06.42.323000000 AM","assigned user":"99018784,99024657","special conditions":"Disbursement and Repayment from account - SBI61324353245272, , Resi FI of applicant to be documented, FI of under Construct property to be done","decision reason":"Excellent RTR","decision comment":"Approved","sourcing rm name":"542381","reporting supervisor name":"5313294","webtop":"365CG121200036","fi_completion_date":"17-MAY-17 12.13.08.924000 PM"}
]
```

#### Jolt Schema for Request - 4
```
[
  {
    "operation": "shift",
    "spec": {
      "*": {
        "last name": "[&1].Lastname__c",
        "loan stage name": "[&1].Loan_Stage_Name__c",
        "email id": "[&1].Email_Id__c",
        "customer number": "[&1].Customer_Number__c",
        "date of birth": "[&1].Date_Of_Birth__c",
        "current processing stage": "[&1].Current_Processing_Stage__c",
        "scheme code": "[&1].Scheme__c",
        "occupation typ": "[&1].Occupation__c",
        "tenure": "[&1].Tenure__c",
        "currency": "[&1].Currency__c",
        "rate": "[&1].Rate__c",
        "payment frequency": "[&1].Payment_Frequency__c",
        "applicant type": "[&1].Applicant_Type__c",
        "loan purpose": "[&1].Loan_Purpose__c",
        "product": "[&1].Product__c",
        "emi": "[&1].EMI__c",
        "neo cif id": "[&1].CIF_Number__c",
        "product type": "[&1].Product_Type__c",
        "loan amount": "[&1].Loan_Amount__c",
        "mobile no": "[&1].Mobile_Number__c",
        "loan branch code": "[&1].Branch_Code__c",
        "disbursement amount": "[&1].Disbursement_Amount__c",
        "sales officer code": "[&1].Sales_Officer_Code__c",
        "pan_number": "[&1].Pan_Number__c",
        "sanction date": "[&1].Sanction_Date__c",
        "creation_time_stamp": "[&1].Application_Creation_Timestamp__c",
        "address": "[&1].Address__c",
        "dsa code": "[&1].DSA_Code__c",
        "reporting supervisor name": "[&1].RSM__c",
        "first name": "[&1].Firstname__c",
        "under writer result": "[&1].Under_Writer_Result__c",
        "sanction amount": "[&1].Sanction_Amount__c",
        "channel": "[&1].Channel__c",
        "DST": "[&1].DST__c",
        "decision reason": "[&1].Decision_Reason__c",
        "sourcing rm name": "[&1].SRM__c",
        "webtop": "[&1].Webtop__c"
      }
    }
  }
]
```

#### Result of Request -3
```
[{"Lastname__c":"RS","Loan_Stage_Name__c":"Post Disbursal","Email_Id__c":"SALHGU345S@GMAIL.COM,SALASUHFFHUJ@GMAIL.COM","Customer_Number__c":"CUST100004567","Date_Of_Birth__c":"1962-09-12","Current_Processing_Stage__c":"CREDIT_APPROVAL","Scheme__c":"BL_LOWTI","Tenure__c":36,"Currency__c":"INR","Rate__c":23.05,"Payment_Frequency__c":"Monthly","Applicant_Type__c":1,"Loan_Purpose__c":"Business Working Capital","Product__c":"Business Loan Unsecured","EMI__c":"INR~25759","CIF_Number__c":"GLBCUST0001000054643","Product_Type__c":"Personal Finance","Loan_Amount__c":665000,"Mobile_Number__c":"98456645456","Branch_Code__c":"365","Disbursement_Amount__c":665000,"Pan_Number__c":"AMF66001N","Sanction_Date__c":"29-MAY-17 01.31.50.430000 PM","Application_Creation_Timestamp__c":"11-MAY-17 01.51.00.390000000 PM","Address__c":"E-145 NIRMA AJMER ROAD JAIPUR   Rajasthan Jaipur India","DSA_Code__c":"8046879","RSM__c":"5313294","Firstname__c":"RAFIQUDEEN","Under_Writer_Result__c":"Approve","Sanction_Amount__c":665000,"Channel__c":"DSA/Dealer","Decision_Reason__c":"Excellent RTR","SRM__c":"542381","Webtop__c":"365CG121200036"}]
```




## Spacail charactors in Jolt
* &: It is used in RHS with Shift Operations and Refer to current LHS node name &(1) one level above current LHS node &(2) Two level above current LHS.
* *: It is used in LHS with Shift, remove, cardinality, modify-default-beta & modify-overwrite-beta operations and iterate Over items.
* @: It is used in both LHS and RHS with shift (LHS and RHS), modify-default-beta(RHS) and modify-overwrite-beta (RHS) operations and Lookup value in input json @ given node level.
* $: It is used in LHS with shift operation and only field Names.
* #: It is used in both LHS and RHS with Shift operation and Define the way object Array is created.
* |: It is used in LHS with shift operations and Input fields with uncertain name pointing to same output.
