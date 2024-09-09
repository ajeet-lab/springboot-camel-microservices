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
    <!--    <xsl:param name="OrganizationCode" select="'in.gov.dvc'"/> FOR PUTTING DEFAULT VALUE-->
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
