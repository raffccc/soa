package com.knight.user.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.knight.user.model.rest.Link;
import com.knight.user.model.rest.RESTEntity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Users implements RESTEntity {
	
	@XmlElement(name="user")
	private Collection<User> users;
	
	@XmlElement(name="link")
	private Collection<Link> links;
	
	public Users() {
	}
	
	public Users(Collection<User> users, Link... links) {
		this.users = users;
		this.links = new ArrayList<>(Arrays.asList(links));
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	public Collection<Link> getLinks() {
		return links;
	}

	@Override
	public void addLink(Link link) {
		if (links == null) {
			links = new ArrayList<>();
		}
		links.add(link);
	}

}