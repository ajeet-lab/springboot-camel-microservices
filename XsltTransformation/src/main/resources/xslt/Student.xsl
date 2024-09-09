<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Output settings -->
<xsl:output method="html" encoding="UTF-8" indent="yes" />
<xsl:template match="/class">
    <html>
        <head>
            <title>Student Data</title>
            <style>
                body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                }

                h1 {
                color: #336699;
                }

                table {
                width: 100%;
                border-collapse: collapse;
                }

                th, td {
                border: 1px solid #333;
                padding: 10px;
                }

                th {
                background-color: #336699;
                color: white;
                }

                td {
                background-color: #f9f9f9;
                }

            </style>
        </head>
        <body>
            <h2>Student List</h2>
            <table border="1">
                <tr class="bgGreen">
                    <th>First Name</th>
                    <th>Second Name</th>
                    <th>Age</th>
                </tr>
                <xsl:for-each select="student">
                    <tr>
                        <td><xsl:value-of select="firstName" /></td>
                        <td><xsl:value-of select="secondName" /></td>
                        <td><xsl:value-of select="age" /></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
    </html>
</xsl:template>

</xsl:stylesheet>