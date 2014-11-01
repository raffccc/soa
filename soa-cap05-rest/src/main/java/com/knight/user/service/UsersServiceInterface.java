package com.knight.user.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.knight.user.model.User;

/**
 * If you are using RESTEasy as a framework and your REST service
 * is written in Java, you can create an interface that will
 * have all your Rest annotations.
 * 
 * Advantage: Your service implementation will be cleaner, without the annotations. 
 * Disadvantage: High coupling with RESTEasy and Java implementation
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public interface UsersServiceInterface {

	String FIELD_IMAGE_DESCRIPTION = "Descricao";
	String PARAM_BEGIN = "begin";
	String PARAM_PAGE_SIZE= "pageSize";
	
	@GET
	public Response listUsers(
			@HeaderParam("If-Modified-Since") Date modifiedSince,
			@QueryParam(PARAM_BEGIN) @DefaultValue("0") Integer begin,
			@QueryParam(PARAM_PAGE_SIZE) @DefaultValue("2") Integer pageSize,
			@Context UriInfo uriInfo);
	
	@GET
	@Path("/{id}")
	public Response find(@PathParam("id") Long id, @HeaderParam("If-Modified-Since") Date modifiedSince);
	
	@POST
	public Response create(@Context UriInfo uriInfo, User user);
	
	@GET
	@Path("/{id}")
	//This overrides the @Produces of the class
	@Produces("image/*")
	public Response retrieveImage(@PathParam("id") Long id, @HeaderParam("If-Modified-Since") Date modifiedSince);
	
	//Put because the user must already exist, put is recommended for updates
	@PUT
	@Path("/{id}")
	//Receives an image
	@Consumes("image/*")
	public Response addImage(@HeaderParam(FIELD_IMAGE_DESCRIPTION) String description, 
			@PathParam("id") Long userId,
			@Context HttpServletRequest httpServletRequest,
			byte[] data);
	
}
