<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:param name="RetStatus" />
	<xsl:param name="SysErrorCode" />
	<xsl:param name="SysErrorMessage" />
	<xsl:param name="ErrorMessage" />
	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="requestId">
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
		<xsl:copy-of select="." />
	</xsl:template>
</xsl:stylesheet>