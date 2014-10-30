package com.knight.stocks.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.knight.stocks.adapter.AuthorAdapter;
import com.knight.stocks.adapter.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ EBook.class })
public class Book {
	
	private String name;
	
	/*
	 * Groups every author in the parent tag <authors>.
	 * Each author gets in a tag called <author>
	 */
	@XmlElementWrapper(name="authors")
	@XmlElement(name="author")
	@XmlJavaTypeAdapter(value = AuthorAdapter.class)
	private List<Author> authors;
	
	private String publisher;
	private Integer publicationYear;
	private String summary;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date creationDate = new Date();
	
	public Book() {	}
	
	public Book(Integer publicationYear, List<Author> authors, String publisher, String name, String summary) {
		this.publicationYear = publicationYear;
		this.authors = authors;
		this.publisher = publisher;
		this.name = name;
		this.summary = summary;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Integer getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}