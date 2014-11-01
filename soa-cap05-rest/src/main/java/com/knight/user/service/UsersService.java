package com.knight.user.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.knight.user.model.User;
import com.knight.user.model.Users;

@Path("/users")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
@Stateless
public class UsersService {
	
	@PersistenceContext
	private EntityManager em;
	
	@GET
	public Users listUsers() {
		return new Users(em.createQuery("select u from User u", User.class).getResultList());
	}

}
