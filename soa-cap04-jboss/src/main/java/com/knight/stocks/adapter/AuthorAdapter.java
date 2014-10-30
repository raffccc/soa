package com.knight.stocks.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.knight.stocks.model.Author;

/**
 * I can use this class as a parameter to the XMLJavaTypeAdapter annotation,
 * telling JAXB how to convert from one type to another.
 */
public class AuthorAdapter extends XmlAdapter<String, Author> {

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@Override
	public String marshal(Author author) throws Exception {
		return author.getName();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@Override
	public Author unmarshal(String name) throws Exception {
		return new Author(name, null);
	}

}
