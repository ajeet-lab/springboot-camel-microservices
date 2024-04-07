

<b>API I :</b> http://0.0.0.0:9000/api/posts </br>
<b>Payload :</b>
{
"values": [
{
"products": 5
},
{
"quotes": "3"
},
{
"posts": "5"
},
{
"comments": 2
}
]
}


<b>API 2 :</b> http://0.0.0.0:9000/api/loans
<br />
<b>Payload I :</b>
{
"requestHeader": {
"tenantId": 505,
"userDetail": {
"userCode": "sachinm",
"branchId": 5
}
"loanId": "",
"loanAcccountNo": "TCFCE0400001100002",
"fetchAllCharges": "N",
"tenantId": 905,
"considerLPPComputation": "N"
}

<b>Payload II :</b> [
{
"requestHeader": {
"tenantId": 505,
"userDetail": {
"userCode": "sachinm",
"branchId": 5
}
},
"loanId": "",
"loanAcccountNo": "TCFCE0400000011376876",
"fetchAllCharges": "N",
"tenantId": 505,
"considerLPPComputation": "N"
},
{
"requestHeader": {
"tenantId": 505,
"userDetail": {
"userCode": "sachinm",
"branchId": 5
}
},
"loanId": "",
"loanAcccountNo": "TCFCE0400001100002",
"fetchAllCharges": "N",
"tenantId": 905,
"considerLPPComputation": "N"
}
]
