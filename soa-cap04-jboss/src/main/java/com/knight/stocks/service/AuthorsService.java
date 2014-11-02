package com.knight.stocks.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Endpoint;

import org.jboss.ws.api.annotation.EndpointConfig;
import org.jboss.ws.api.annotation.WebContext;

import com.knight.stocks.model.Author;

/*
 * The attributes inside @WebService and the annotation EntpointConfig include 
 * security in this service.
 * 
 * @WebService: Finds the contract defined manually
 * @EndpointConfig: Finds the callback definition
 * @WebContext: Adapts this service to be safe
 */
@WebService(portName = "AuthorsServicePort", 
			serviceName="AuthorsServiceService",
			targetNamespace="http://service.stocks.knight.com/",
			wsdlLocation="WEB-INF/wsdl/AuthorsService.wsdl")
@EndpointConfig(configFile="WEB-INF/jaxws-endpoint-config.xml",
				configName="Endpoint WS-Security")	
@WebContext(secureWSDLAccess=true,
			transportGuarantee="CONFIDENTIAL",
			urlPattern="AuthorsService")
@Stateless
public class AuthorsService {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Author> listAuthors() {
		return em.createQuery("select a from Author a", Author.class).getResultList();
	}
	
	public static void main(String[] args) {
		//This method isn't capable of publish more than one service in the same port
		Endpoint.publish("http://localhost:8080/authors", new AuthorsService());
		System.out.println("Authors Service initialized!");
	}

}
