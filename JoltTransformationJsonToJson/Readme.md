
# Jolt
#### Request - 1 : http://localhost:9091/api/jolt/simplejsontojson1
```
{
  "firstname": "Alice",
  "lastname": "Joe",
  "email": "Alice@gmail.com",
  "phone": 9415652174,
  "house": "406A, Gauspur",
  "pincode": 233305,
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
Here, we've crafted a Jolt schema using the shift operation and **extract values from the request's identical keys**, then placing these values inside a **"person"** object. You can see the result below:
* **Let's Understand below how it is work.**
  * **"firstname":person.firstname** :- It means that the value of the "firstname" key is extracted and placed inside the "firstname" field within the "person" object in the output JSON. Result is - **{"person" : {"firstname" : "Alice"}}.**
  * **"email":person.contact.email** :- It means that the value of the **"email"** key is extracted and placed inside the **"email"** field within the **"contact"** object within the **"person"** object in the output JSON. Result is - **{"person" : "contact" : {"email" : "Alice@gmail.com"}}**
  * **"house": "person.address","pincode": "person.address"** :- It means that the value of the **"address** and **pincode"** key is extracted and placed in **an array** inside the **"address"** field within the **"person"** object in the output JSON. Because we've placed both the values of **"address"** and **"pincode"** keys inside the **"address"** field within the "person" object. Result is - **{"person" : "address" : [ "406A, Gauspur", 233305 ]}**

#### Result of Request -1 
```
{
  "person" : {
    "firstname" : "Alice",
    "lastname" : "Joe",
    "contact" : {
      "email" : "Alice@gmail.com",
      "phone" : 9415652174
    },
    "user" : {
      "age" : 30
    },
    "address" : [ "406A, Gauspur", 233305 ]
  }
}
```