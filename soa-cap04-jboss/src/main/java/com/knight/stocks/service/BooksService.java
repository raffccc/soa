package com.knight.stocks.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.soap.SOAPException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.knight.stocks.dao.BookDAO;
import com.knight.stocks.exception.UnauthorizedUserException;
import com.knight.stocks.model.Book;
import com.knight.stocks.model.User;

@WebService
public class BooksService {
	
	private BookDAO getBookDAO() {
		return new BookDAO();
	}
	
	/*
	 * @WebResult name attribute tells what is the name of
	 * the tag for each element returned by this method.
	 */
	@WebResult(name="book")
	public List<Book> listBooks() {
		return getBookDAO().listBooks();
	}
	
	/*
	 * If I only use the RequestWrapper annotation with className, both listBooks methods will 
	 * have the same element in the generated XSD schema. So we have to use localName or targetNamespace.
	 * 
	 * - If we use localName, we have to choose a name that doesn't exist in the XSD
	 * - If we use targetNamespace we must be careful if the listBooks element still doesn't exist in this namespace
	 */
	@RequestWrapper(className="knight.services.jaxws.ListBooksPaginated", localName="listBooksPaginated")
	@ResponseWrapper(className="knight.services.jaxws.ListBooksPaginatedResponse", localName="paginatedBooks")
	@WebResult(name="book")
	//Tells how is this method name seen by the caller
	@WebMethod(operationName="listBooksPaginated")
	public List<Book> listBooks(Integer pageNumber, Integer pageSize) {
		return getBookDAO().listBooks();
	}
	
	public void createBook(
			@WebParam(name="book") Book book, 
			@WebParam(name="user", header=true) User user) 
					throws SOAPException, UnauthorizedUserException {
		
		if (user.getLogin().equals("soa") && user.getPassword().equals("soa")) {
			getBookDAO().createBook(book);
		} else {
			throw new UnauthorizedUserException("Unauthorized!");
			
			/*
			 * Refining the exception
			 * 
			 * SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE means that the faultcode will be preceded by S:,
			 * that's the SOAP convention. The fault code must begin with: VersionMIsmatch, MustUnderstand,
			 * Client and Server followed by .<description>
			 */
//			SOAPFault soapFault = SOAPFactory.newInstance().createFault("Unauthorized!", 
//					new QName(SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE, "Client.authorization"));
//			soapFault.setFaultActor("http://knight.com/BooksService");
//			throw new SOAPFaultException(soapFault);
		}
	}
	
	public static void main(String[] args) {
		/*
		 * To import the java files use wsimport (located inside bin folder of java installation path).
		 * wsimport -s <folderName> http://localhost:8080/books
		 * This will place the java files inside <folderName>
		 */
		Endpoint.publish("http://localhost:8080/books", new BooksService());
		System.out.println("Service initialized!");
	}

}
