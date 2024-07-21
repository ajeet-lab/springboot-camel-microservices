<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="no"/>
	<xsl:param name="RetStatus" />
	<xsl:param name="SysErrorCode" />
	<xsl:param name="SysErrorMessage" />
	<xsl:param name="ErrorMessage"/>
	<xsl:template match="/">
		<Response>
			<retStatus>
				<xsl:value-of select="$RetStatus" />
			</retStatus>
			<errorMessage>
				<xsl:value-of select="$ErrorMessage" />
			</errorMessage>
			<sysErrorCode>
				<xsl:value-of select="$SysErrorCode" />
			</sysErrorCode>
			<sysErrorMessage>
				<xsl:value-of select="$SysErrorMessage" />
			</sysErrorMessage>
		</Response>
	</xsl:template>
</xsl:stylesheet>