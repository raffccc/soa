package com.knight.stocks.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.knight.stocks.model.Author;
import com.knight.stocks.model.Book;
import com.knight.stocks.model.EBook;

public class BookDAO {

	public List<Book> listBooks() {
		List<Book> books = new ArrayList<Book>();
		
		Book b1 = new Book();
		b1.setName("HP and the Globet of Fire");
		b1.setPublicationYear(2004);
		b1.setSummary("Book about a wizard");
		b1.setAuthors(Arrays.asList(new Author("J K Rowling", new Date()), new Author("Rafael Figueiredo", new Date())));
		books.add(b1);
		
		Book b2 = new EBook();
		b2.setName("Rich dad Poor dad");
		b2.setPublicationYear(1990);
		b2.setSummary("Book about financial education");
		b2.setAuthors(Arrays.asList(new Author("Robert Kiyosaki", new Date()), new Author("Rafael Figueiredo", new Date())));
		books.add(b2);
		
		return books;
	}

	public void createBook(Book book) {
		// TODO Auto-generated method stub
		
	}
	
}
