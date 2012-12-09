<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="text"/>

<xsl:template name="main">
	<xsl:text>Started named template with intial template "main"</xsl:text>
</xsl:template>

<xsl:template match="/">
	<xsl:text>Started matching root with no mode</xsl:text>
</xsl:template>

<xsl:template match="/" mode="startMode">
	<xsl:text>Started matching root with intial mode "startMode"</xsl:text>
</xsl:template>

</xsl:stylesheet>