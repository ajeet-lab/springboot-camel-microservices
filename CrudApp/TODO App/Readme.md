<p><b>Java version :</b> 11</p>

<p> We have create rest api's using two component one is <b>"jetty"</b> and second one <b>"servlet"</b> </p>

<p><b>Create Data: </b> http://localhost:9000/api/tatacapital/loans</p>
<b>Payload: </b> {
    "loan_id": "abcdefghIghj",
    "uid": "sdt3434",
    "amount": 78000,
    "term": 54,
    "partner_token": "qwewerewerelssds32hif",
    "description_language_id": 1
}

<p><b>Get All data: </b>http://localhost:3000/api/tatacapital/loans</p>


<p><b>Get Data By id: </b>http://localhost:9000/api/tatacapital/loans/abcd01023</p>


<p><b>Update data by id: </b>http://localhost:9000/api/tatacapital/loans/abcd0101</p>
<b>Payload: </b> {
    "uid": "ajeet1234",
    "amount": 90000,
    "term": 24,
    "partner_token": "qjoijsoijfwkrljlssds32hif",
    "description_language_id": 1
}

