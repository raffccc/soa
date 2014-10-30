package com.knight.stocks.model;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

//Changes the name of this type in the schema from FileFormat to format
@XmlType(name="format")
public enum FileFormat {
	
	@XmlEnumValue("pdf")
	PDF,
	
	@XmlEnumValue("epub")
	EPUB, 
	
	@XmlEnumValue("mobi")
	MOBI;

}
