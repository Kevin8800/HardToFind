<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<html>
		<head>	
			<title>Your Oder</title>
		</head>
		<body>
			<xsl:apply-templates/>
		</body>
		</html>
	</xsl:template>
	<xsl:template match="order">
		<h2>Your Order number <xsl:value-of select="@id"/> submitted on <xsl:value-of select="@submitted"/></h2>
		<div>Account number: <xsl:value-of select="//@account"/><br/>
		Customer Name: <xsl:value-of select="./customer/name"/><br/>
		</div><br/>
		<table>
		<th width='70px'>Number</th>
		<th width='180px'>Name</th>
		<th width='30px'>Price</th>
		<th width='30px'>Quantity</th>
		<th width='30px'>Extended</th>
		<xsl:for-each select="items/item">
			<tr>
			<td>
			<xsl:value-of select="@number"/>
			</td>
			<td>
			<xsl:value-of select="./name"/>
			</td>
			<td>
			<xsl:value-of select="./price"/>
			</td>
			<td>
			<xsl:value-of select="./quantity"/>
			</td>
			<td>
			<xsl:value-of select="./extended"/>
			</td>
			</tr>
		</xsl:for-each>
		</table><br/>
		<div>Total: <xsl:value-of select="./total"/>  <br/>
		Shipping:  <xsl:value-of select="./shipping"/> <br/>
		HST:  <xsl:value-of select="./HST"/><br/>
		GrandTotal: <xsl:value-of select="./grandTotal"/><br/>
		</div>
	</xsl:template>
</xsl:stylesheet>
