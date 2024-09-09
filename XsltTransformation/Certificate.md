
# How to create XML with attributes and their values in XSLT?

## 1. Certificate JSON to XML to XSLT
URL: http://localhost:9092/rest/v1.0/jsontoxmltoxslt
* JSON Request
````
{
    "Certificate": {
        "language": "99",
        "name": "Birth Certificate",
        "type": "Original",
        "number": "123456",
        "prevNumber": "654321",
        "expiryDate": "2030-12-31",
        "validFromDate": "2020-01-01",
        "issuedAt": "City Hall",
        "issueDate": "2020-01-01",
        "status": "Valid",
    "IssuedBy": {
        "Organization": {
            "name": "Government Organization",
            "code": "gov.org",
            "tin": "123456789",
            "uid": "UID12345",
            "type": "Public"
        },
        "Address": {
            "type": "Residential",
            "line1": "Main Street 123",
            "line2": "Apt 4B",
            "house": "123",
            "landmark": "Near Park",
            "locality": "Downtown",
            "vtc": "Village Name",
            "district": "District Name",
            "pin": "987654",
            "state": "State Name",
            "country": "Country Name"
        }
    },
    "IssuedTo": {
        "Person": {
            "uid": "UID54321",
            "title": "Mr.",
            "name": "John Doe",
            "dob": "1985-05-10",
            "age": "35",
            "swd": "Father's Name",
            "swdIndicator": "Father",
            "motherName": "Jane Doe",
            "maritalStatus": "Married",
            "relationWithHof": "Son",
            "disabilityStatus": "None",
            "category": "General",
            "religion": "Christianity",
            "email": "john.doe@example.com",
            "gender": "Male",
            "phone": "+1234567890"
        },
        "Address": {
            "type": "Residential",
            "line1": "Main Street 123",
            "line2": "Apt 4B",
            "house": "123",
            "landmark": "Near Park",
            "locality": "Downtown",
            "vtc": "Village Name",
            "district": "District Name",
            "pin": "987654",
            "state": "State Name",
            "country": "Country Name"
        }
    },
    "CertificateData": {
        "Certificate": {
            "name": "Welcome Letter",
            "number": "123456",
            "place": "City Hall",
            "date": "2020-01-01"
        }
	}
}
}
````

JSON request converts to below XML Request using JacksonXML configuration

* **JacksonXML configuration** </br>
 -- Add into Application.java class
````
@Bean("jsontoxmlRootNameCertificate")
public JacksonXMLDataFormat getCustomJacksonDataFormatwithRootnameCertificate() {
    JacksonXMLDataFormat jd=new JacksonXMLDataFormat();
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.writer().withRootName("Certificate");
    xmlMapper.setConfig(xmlMapper.getSerializationConfig().withRootName("Certificate"));
    jd.setXmlMapper(xmlMapper);
    return jd;
}
````
* withRootName("Certificate"): It means adding all the data inside the Certificate tag
  </br></br>

* **Camel Routes**
````
from("direct:jsontoxmltoxslt")
    .unmarshal().json(JsonLibrary.Jackson)
    .marshal().custom("jsontoxmlRootNameCertificate")
    .setBody(xpath("/Certificate/Certificate"))
    .to("xslt:./xslt/Certificate.xsl")
    .log("Xml to xslt ${body}");
````
* marshal().custom("jsontoxmlRootNameCertificate") : Unmarshalling JSON data will convert it into XML, because marshalling takes a bean reference, which returns the data in JacksonXML format. 
</br></br>

* **XML Request**
````
<?xml version="1.0" encoding="UTF-8"?>
<Certificate>
    <Certificate>
        <language>99</language>
        <name>Birth Certificate</name>
        <type>Original</type>
        <number>123456</number>
        <prevNumber>654321</prevNumber>
        <expiryDate>2030-12-31</expiryDate>
        <validFromDate>2020-01-01</validFromDate>
        <issuedAt>City Hall</issuedAt>
        <issueDate>2020-01-01</issueDate>
        <status>Valid</status>
        <IssuedBy>
            <Organization>
                <name>Government Organization</name>
                <code>gov.org</code>
                <tin>123456789</tin>
                <uid>UID12345</uid>
                <type>Public</type>
            </Organization>
            <Address>
                <type>Residential</type>
                <line1>Main Street 123</line1>
                <line2>Apt 4B</line2>
                <house>123</house>
                <landmark>Near Park</landmark>
                <locality>Downtown</locality>
                <vtc>Village Name</vtc>
                <district>District Name</district>
                <pin>987654</pin>
                <state>State Name</state>
                <country>Country Name</country>
            </Address>
        </IssuedBy>
        <IssuedTo>
            <Person>
                <uid>UID54321</uid>
                <title>Mr.</title>
                <name>John Doe</name>
                <dob>1985-05-10</dob>
                <age>35</age>
                <swd>Father's Name</swd>
                <swdIndicator>Father</swdIndicator>
                <motherName>Jane Doe</motherName>
                <maritalStatus>Married</maritalStatus>
                <relationWithHof>Son</relationWithHof>
                <disabilityStatus>None</disabilityStatus>
                <category>General</category>
                <religion>Christianity</religion>
                <email>john.doe@example.com</email>
                <gender>Male</gender>
                <phone>+1234567890</phone>
            </Person>
            <Address>
                <type>Residential</type>
                <line1>Main Street 123</line1>
                <line2>Apt 4B</line2>
                <house>123</house>
                <landmark>Near Park</landmark>
                <locality>Downtown</locality>
                <vtc>Village Name</vtc>
                <district>District Name</district>
                <pin>987654</pin>
                <state>State Name</state>
                <country>Country Name</country>
            </Address>
        </IssuedTo>
        <CertificateData>
            <Certificate>
                <name>Welcome Letter</name>
                <number>123456</number>
                <place>City Hall</place>
                <date>2020-01-01</date>
            </Certificate>
        </CertificateData>
    </Certificate>
</Certificate>
````

* **XSLT Configuration**
````
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Output settings -->
    <xsl:output method="xml" indent="yes"/>

    <!-- Parameters for dynamic data -->
    <xsl:param name="CertificateLanguage" select="/Certificate/language"/>
    <xsl:param name="CertificateName" select="/Certificate/name" />
    <xsl:param name="CertificateType" select="/Certificate/type"/>
    <xsl:param name="CertificateNumber" select="/Certificate/number"/>
    <xsl:param name="PrevNumber" select="/Certificate/prevNumber"/>
    <xsl:param name="ExpiryDate" select="/Certificate/expiryDate"/>
    <xsl:param name="ValidFromDate" select="/Certificate/validFromDate"/>
    <xsl:param name="IssuedAt" select="/Certificate/issuedAt"/>
    <xsl:param name="IssueDate" select="/Certificate/issueDate"/>
    <xsl:param name="Status" select="/Certificate/status"/>

    <xsl:param name="OrganizationName" select="/Certificate/IssuedBy/Organization/name"/>
    <xsl:param name="OrganizationCode" select="/Certificate/IssuedBy/Organization/code"/>
    <xsl:param name="Tin" select="/Certificate/IssuedBy/Organization/tin"/>
    <xsl:param name="Uid" select="/Certificate/IssuedBy/Organization/uid"/>
    <xsl:param name="OrganizationType" select="/Certificate/IssuedBy/Organization/type"/>

    <xsl:param name="AddressType" select="/Certificate/IssuedBy/Address/type"/>
    <xsl:param name="Line1" select="/Certificate/IssuedBy/Address/line1"/>
    <xsl:param name="Line2" select="/Certificate/IssuedBy/Address/line2"/>
    <xsl:param name="House" select="/Certificate/IssuedBy/Address/house"/>
    <xsl:param name="Landmark" select="/Certificate/IssuedBy/Address/landmark"/>
    <xsl:param name="Locality" select="/Certificate/IssuedBy/Address/locality"/>
    <xsl:param name="Vtc" select="/Certificate/IssuedBy/Address/vtc"/>
    <xsl:param name="District" select="/Certificate/IssuedBy/Address/district"/>
    <xsl:param name="Pin" select="/Certificate/IssuedBy/Address/pin"/>
    <xsl:param name="State" select="/Certificate/IssuedBy/Address/state"/>
    <xsl:param name="Country" select="/Certificate/IssuedBy/Address/country"/>

    <xsl:param name="PersonUid" select="/Certificate/IssuedTo/Person/uid"/>
    <xsl:param name="Title" select="/Certificate/IssuedTo/Person/title" />
    <xsl:param name="PersonName" select="/Certificate/IssuedTo/Person/name" />
    <xsl:param name="Dob" select="/Certificate/IssuedTo/Person/dob" />
    <xsl:param name="Age" select="/Certificate/IssuedTo/Person/age" />
    <xsl:param name="Swd" select="/Certificate/IssuedTo/Person/swd" />
    <xsl:param name="SwdIndicator" select="/Certificate/IssuedTo/Person/swdIndicator" />
    <xsl:param name="MotherName" select="/Certificate/IssuedTo/Person/motherName" />
    <xsl:param name="MaritalStatus" select="/Certificate/IssuedTo/Person/maritalStatus" />
    <xsl:param name="RelationWithHof" select="/Certificate/IssuedTo/Person/relationWithHof" />
    <xsl:param name="DisabilityStatus" select="/Certificate/IssuedTo/Person/disabilityStatus" />
    <xsl:param name="Category" select="/Certificate/IssuedTo/Person/category" />
    <xsl:param name="Religion" select="/Certificate/IssuedTo/Person/religion" />
    <xsl:param name="Email" select="/Certificate/IssuedTo/Person/email" />
    <xsl:param name="Gender" select="/Certificate/IssuedTo/Person/gender" />
    <xsl:param name="Phone" select="/Certificate/IssuedTo/Person/phone" />

    <xsl:param name="CertificateDataName" select="/Certificate/CertificateData/Certificate/name"/>
    <xsl:param name="CertificateDataNumber" select="/Certificate/CertificateData/Certificate/number"/>
    <xsl:param name="Place" select="/Certificate/CertificateData/Certificate/place"/>
    <xsl:param name="Date" select="/Certificate/CertificateData/Certificate/date"/>

    <!-- Template matching root element -->
    <xsl:template match="/Certificate">
        <Certificate language="{$CertificateLanguage}" name="{$CertificateName}" type="{$CertificateType}" number="{$CertificateNumber}" prevNumber="{$PrevNumber}" expiryDate="{$ExpiryDate}" validFromDate="{$ValidFromDate}" issuedAt="{$IssuedAt}" issueDate="{$IssueDate}" status="{$Status}">
            <IssuedBy>
                <Organization name="{$OrganizationName}" code="{$OrganizationCode}" tin="{$Tin}" uid="{$Uid}" type="{$OrganizationType}"/>
                <Address type="{$AddressType}" line1="{$Line1}" line2="{$Line2}" house="{$House}" landmark="{$Landmark}" locality="{$Locality}" vtc="{$Vtc}" district="{$District}" pin="{$Pin}" state="{$State}" country="{$Country}"/>
            </IssuedBy>
            <IssuedTo>
                <Person uid="{$PersonUid}" title="{$Title}" name="{$PersonName}" dob="{$Dob}" age="{$Age}" swd="{$Swd}" swdIndicator="{$SwdIndicator}" motherName="{$MotherName}" maritalStatus="{$MaritalStatus}" relationWithHof="{$RelationWithHof}" disabilityStatus="{$DisabilityStatus}" category="{$Category}" religion="{$Religion}" email="{$Email}" gender="{$Gender}" phone="{$Phone}"/>
                <Address type="{$AddressType}" line1="{$Line1}" line2="{$Line2}" house="{$House}" landmark="{$Landmark}" locality="{$Locality}" vtc="{$Vtc}" district="{$District}" pin="{$Pin}" state="{$State}" country="{$Country}"/>
            </IssuedTo>
            <CertificateData>
                <Certificate name="{$CertificateDataName}" number="{$CertificateDataNumber}" place="{$Place}" date="{$Date}"/>
            </CertificateData>
            <Signature/>
        </Certificate>
    </xsl:template>
</xsl:stylesheet>
````

* **Result**
````
<?xml version="1.0" encoding="UTF-8"?>
<Certificate language="99" name="Birth Certificate" type="Original" number="123456" prevNumber="654321" expiryDate="2030-12-31" validFromDate="2020-01-01" issuedAt="City Hall" issueDate="2020-01-01" status="Valid">
    <IssuedBy>
        <Organization name="Government Organization" code="gov.org" tin="123456789" uid="UID12345" type="Public"/>
        <Address type="Residential" line1="Main Street 123" line2="Apt 4B" house="123" landmark="Near Park" locality="Downtown" vtc="Village Name" district="District Name" pin="987654" state="State Name" country="Country Name"/>
    </IssuedBy>
    <IssuedTo>
        <Person uid="UID54321" title="Mr." name="John Doe" dob="1985-05-10" age="35" swd="Father's Name" swdIndicator="Father" motherName="Jane Doe" maritalStatus="Married" relationWithHof="Son" disabilityStatus="None" category="General" religion="Christianity" email="john.doe@example.com" gender="Male" phone="+1234567890"/>
        <Address type="Residential" line1="Main Street 123" line2="Apt 4B" house="123" landmark="Near Park" locality="Downtown" vtc="Village Name" district="District Name" pin="987654" state="State Name" country="Country Name"/>
    </IssuedTo>
    <CertificateData>
        <Certificate name="Birth Certificate" number="123456" place="City Hall" date="2020-01-01"/>
    </CertificateData>
    <Signature/>
</Certificate>
````


## 2. RentalProperties XML to XSLT
URL: http://localhost:9092/rest/v1.0/jsontoxmltoxslt