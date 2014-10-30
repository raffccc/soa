package com.knight.stocks.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.knight.stocks.adapter.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Author {
	
	private String name;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthDate;
	
	@XmlTransient
	private String ignoredField;
	
	public Author() { }
	
	public Author(String name, Date birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	/*
	 * By default, methods will only be accessible if they have the get and set pair,
	 * if we want to make this content accessible we must annotate it with XmlElement
	 */
	@XmlElementWrapper(name = "refs")
	@XmlElement(name = "ref")
	public List<URL> getRefs() throws MalformedURLException {
		return Arrays.asList(new URL("http://twitter.com"), 
				new URL("http://facebook.com"), 
				new URL("http://author.com"));
	}

}
