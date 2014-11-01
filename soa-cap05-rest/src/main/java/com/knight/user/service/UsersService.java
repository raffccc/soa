package com.knight.user.service;

import java.net.URI;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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
	
	/*
	 * The If-Modified-Since acts as a cache, only requesting the resource from the server if it wasn't
	 * updated since the date sent.
	 */
	@GET
	@Path("/{id}")
	public Response find(@PathParam("id") Long id, @HeaderParam("If-Modified-Since") Date modifiedSince) {
		//The user can be directly returned by this method, no problem
		//return em.find(User.class, id);
		
		User user = em.find(User.class, id);
		if (user != null) {
			if (modifiedSince == null || user.getUpdateDate().after(modifiedSince)) {
				//Returns a HTML with status 200(ok) as Header, and the user as body
				return Response.ok(user).build();
			} else {
				Response.notModified().build();
			}
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	/*
	 * JAX-RS engine manages UriInfo interface for us, we just need to inject
	 * it on our service using @Context.
	 */
	@POST
	public Response create(@Context UriInfo uriInfo, User user) {
		em.persist(user);
		
		//Builds the url where the resource will be available
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		URI location = builder.path("/{id}").build(user.getId());
		
		return Response.created(location).build();
	}

}
