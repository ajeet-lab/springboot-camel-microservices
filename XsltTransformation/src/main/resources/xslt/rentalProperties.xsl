<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="no"/>
    <xsl:param name="propertyContact" select='//property/@contact'/>
    <xsl:param name="propertyAddress" select='//property/@address'/>
    <xsl:param name="propertytype" select='/rentalProperties/property/type'/>
    <xsl:param name="propertyprice" select='/rentalProperties/property/price'/>
    <xsl:param name="propertynumberOfBedrooms" select='/rentalProperties/property/numberOfBedrooms'/>
    <xsl:param name="propertynumberOfBathrooms" select='/rentalProperties/property/numberOfBathrooms'/>
    <xsl:param name="propertygarage" select='/rentalProperties/property/garage'/>

    <xsl:param name="addressstreetNo" select='/rentalProperties/property/address/streetNo'/>
    <xsl:param name="addressstreet" select='/rentalProperties/property/address/street'/>
    <xsl:param name="addresssuburb" select='/rentalProperties/property/address/suburb'/>
    <xsl:param name="addressstate" select='/rentalProperties/property/address/state'/>

<!--    <xsl:param name="addresszipcode" select='/rentalProperties/property/address/zipcode'/>-->
    <xsl:param name="addresszipcode" select='//address/zipcode'/>

<!--    <xsl:param name="addressvillage" select='/rentalProperties/property/address/city/village'/>-->
<!--    <xsl:param name="addressvillage" select='//address/city/village'/>-->
    <xsl:param name="addressvillage" select='//address//village'/>

    <xsl:template match="/rentalProperties">
        <rentalProperties>
            <property>
                <xsl:attribute name="contact"><xsl:value-of select='$propertyContact'/></xsl:attribute>
                <xsl:attribute name="address"><xsl:value-of select='$propertyAddress'/></xsl:attribute>
                <type><xsl:value-of select="$propertytype"/></type>
                <type><xsl:value-of select="$propertyprice"/></type>
                <address>
                    <streetNo><xsl:value-of select="$addressstreetNo"/></streetNo>
                    <street><xsl:value-of select="$addressstreet"/></street>
                    <suburb><xsl:value-of select="$addresssuburb"/></suburb>
                    <state><xsl:value-of select="$addressstate"/></state>
                    <zipcode><xsl:value-of select="$addresszipcode"/></zipcode>
                    <city>
                        <village><xsl:value-of select="$addressvillage"/></village>
                    </city>
                </address>
                <numberOfBedrooms><xsl:value-of select="$propertynumberOfBedrooms"/></numberOfBedrooms>
                <numberOfBathrooms><xsl:value-of select="$propertynumberOfBathrooms"/></numberOfBathrooms>
                <garage><xsl:value-of select="$propertygarage"/></garage>
            </property>
        </rentalProperties>
    </xsl:template>
</xsl:stylesheet>