package com.knight.user.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.knight.user.model.rest.Link;
import com.knight.user.model.rest.RESTEntity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User extends ModelEntity implements RESTEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String login;
	private String password;
	
	//Orphan removal means that if an image loses its connection to an user, it will be deleted
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	@XmlTransient
	private Image image;
	
	@XmlElement(name="link")
	@Transient
	private Link imageLink;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name != null ? name : "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogin() {
		return login != null ? login : "";
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password != null ? password : "";
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void addLink(Link link) {
		if (link != null) {
			this.imageLink = link;
		}
	}
	
}