<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="/">
        <table border="1">
            <tr>
                <th>PLAYER</th>
            </tr>
            <xsl:apply-templates />
        </table>
    </xsl:template>

    <xsl:template match="player">
        <tr>
            <td><xsl:value-of select="."/></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>