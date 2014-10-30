package com.knight.stocks.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import com.knight.stocks.model.Author;

@WebService
public class AuthorsService {
	
	public List<Author> listAuthors() {
		Author adrianoAlmeida = new Author("Adriano Almeida", new Date());
		Author pauloSilveira = new Author("Paulo Silveira", new Date());
		Author viniciusBaggio = new Author("Vinicius Baggio", new Date());
		return Arrays.asList(adrianoAlmeida, pauloSilveira, viniciusBaggio);
	}
	
	public static void main(String[] args) {
		//This method isn't capable of publish more than one service in the same port
		Endpoint.publish("http://localhost:8080/authors", new AuthorsService());
		System.out.println("Authors Service initialized!");
	}

}
