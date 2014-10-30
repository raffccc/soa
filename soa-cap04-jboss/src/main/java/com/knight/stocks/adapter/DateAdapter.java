package com.knight.stocks.adapter;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * I can use this class as a parameter to the XMLJavaTypeAdapter annotation,
 * telling JAXB how to convert from one type to another.
 */
public class DateAdapter extends XmlAdapter<XMLGregorianCalendar, Date> {

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public XMLGregorianCalendar marshal(Date date) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		
		XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		
		xmlCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return xmlCalendar;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Date unmarshal(XMLGregorianCalendar calendar) throws Exception {
		return calendar.toGregorianCalendar().getTime();
	}

}
