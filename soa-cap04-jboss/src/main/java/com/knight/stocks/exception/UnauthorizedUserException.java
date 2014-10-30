package com.knight.stocks.exception;

import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebFault;

@WebFault(targetNamespace="http://services.knight.com/exceptions/", name="UnauthorizedUser")
public class UnauthorizedUserException extends Exception {

	private static final long serialVersionUID = -3448849765204046783L;
	
	public UnauthorizedUserException() {
		super();
	}
	
	public UnauthorizedUserException(Throwable t) {
		super(t);
	}
	
	public UnauthorizedUserException(String message) {
		super(message);
	}
	
	public UnauthorizedUserException(String message, Throwable t) {
		super(message, t);
	}
	
	public UnauthorizedUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/*
	 * Method used by JAX-WS. This method must return a JAXB compatible
	 * object that will be used as the mapping of this exception.
	 * 
	 * So, instead of the response have a field like this:
	 * 	<UnauthorizedException ...>
	 * 		<message>...</message>
	 *  </UnauthorizedException>
	 * 
	 * The treatment made here changed this behavior to:
	 * 
	 * <UnauthorizedException message="..." />
	 */
	public UserFaultInfo getFaultInfo() {
		return new UserFaultInfo(getMessage());
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class UserFaultInfo {
		
		@XmlAttribute
		private String message;
		
		/*
		 * Needed to refine the date format in the XML
		 * It's usually a bad practice to adapt your classes to the conversor, it is better to
		 * use adapters and instead of using XMLGregorianCalendar here, use Date or Calendar.
		 * See an example in the Book class.
		 */
		private XMLGregorianCalendar date;
		
		public UserFaultInfo(String message) {
			this.message = message;
			try {
				//by default this date is represented like this 2014-10-26T11:30:21.570-02:00
				this.date = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
				
				this.date.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
				this.date.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			} catch (DatatypeConfigurationException e) {
				new RuntimeException(e);
			}
		}
		
	}
	
}
