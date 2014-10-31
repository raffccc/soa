package com.knight.stocks.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.Endpoint;

import com.knight.stocks.model.Author;

@WebService
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
