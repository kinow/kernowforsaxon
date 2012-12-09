
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/" name="main">
  Base URI: <xsl:value-of select="base-uri(document(''))"/>
</xsl:template>

</xsl:stylesheet>