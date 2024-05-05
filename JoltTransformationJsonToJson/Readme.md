
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


## Spacail charactors in Jolt
* &: It is used in RHS with Shift Operations and Refer to current LHS node name &(1) one level above current LHS node &(2) Two level above current LHS. 
* *: It is used in LHS with Shift, remove, cardinality, modify-default-beta & modify-overwrite-beta operations and iterate Over items.
* @: It is used in both LHS and RHS with shift (LHS and RHS), modify-default-beta(RHS) and modify-overwrite-beta (RHS) operations and Lookup value in input json @ given node level.
* $: It is used in LHS with shift operation and only field Names.
* #: It is used in both LHS and RHS with Shift operation and Define the way object Array is created.
* |: It is used in LHS with shift operations and Input fields with uncertain name pointing to same output.
  
