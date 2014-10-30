package com.knight.stocks.model;

import java.util.List;

/**
 * Even if we put this class name inside jaxb.index, it won't be mapped in the Schema of our wsdl, because
 * any reference to it is mane in our code (our code just uses Book). So, we must use @XmlSeeAlso in Book
 * class.
 */
public class EBook extends Book {
	
	private FileFormat format = FileFormat.PDF;
	
	public EBook() { }
	
	public EBook(Integer publicationYear, List<Author> authors, String publisher, String name, String summary) {
		super(publicationYear, authors, publisher, name, summary);
	}

	public FileFormat getFormat() {
		return format;
	}

	public void setFormat(FileFormat format) {
		this.format = format;
	}
	
}
