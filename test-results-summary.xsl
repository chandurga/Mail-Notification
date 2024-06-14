<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="resultsfile"/>
    <xsl:output method="text"/>
    <xsl:template match="/">
        <xsl:for-each select="testsuite/testcase">
            Name: <xsl:value-of select="@classname"/>.<xsl:value-of select="@name"/>, Status:
            <xsl:choose>
                <xsl:when test="failure">failed</xsl:when>
                <xsl:when test="error">error</xsl:when>
                <xsl:when test="skipped">skipped</xsl:when>
                <xsl:otherwise>passed</xsl:otherwise>
            </xsl:choose>, Time: <xsl:value-of select="@time"/> seconds
            <xsl:text>&#10;</xsl:text>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
