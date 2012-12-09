<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:import-schema
	namespace="http://www.w3.org/1999/xhtml"
	schema-location="http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd"/>

<xsl:template match="/">
	<xsl:result-document validation="lax">
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<title>Must have a title</title>
			</head>
			<body>
				<div>A valid XHTML file!</div>
			</body>
		</html>
	</xsl:result-document>
</xsl:template>

</xsl:stylesheet>