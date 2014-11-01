package com.knight.user.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.knight.user.model.Image;
import com.knight.user.model.User;
import com.knight.user.model.Users;
import com.knight.user.model.rest.Link;

@Stateless
public class UsersService implements UsersServiceInterface {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Response listUsers(Date modifiedSince, Integer begin, Integer pageSize, UriInfo uriInfo) {
		List<User> users = em.createQuery("select u from User u", User.class)
				.setFirstResult(begin)
				.setMaxResults(pageSize)
				.getResultList();
		
		Long numberOfUsers = em.createQuery("select count(u) from User u", Long.class).getSingleResult();
		
		boolean updated = false;
		if (modifiedSince != null) {
			for (User user : users) {
				if (user.getUpdateDate().after(modifiedSince)) {
					updated = true;
					break;
				}
			}
		} else {
			updated = true;
		}
		
		if (!updated) {
			return Response.notModified().build();
		} else {
			for (User user : users) {
				Link link = createUserImageLink(user);
				user.addLink(link);
			}
			return Response.ok(
					new Users(users, 
							createUserLinks(uriInfo, pageSize, begin, numberOfUsers)))
							.build();
		}
		
	}
	
	private Link createUserImageLink(User user) {
		if (user.getImage() != null) {
			String href = UriBuilder.fromPath("users/{id}").build(user.getId()).toString();
			return new Link(href, "image", "image/*");
		}
		return null;
	}

	private Link[] createUserLinks(UriInfo uriInfo, Integer pageSize, Integer begin, Long numberOfUsers) {
		Collection<Link> links = new ArrayList<>();
		
		double numberOfUsersDouble = numberOfUsers;
		double pageSizeDouble = pageSize;
		
		Long numberOfPages = (long) Math.ceil(numberOfUsersDouble/pageSizeDouble);
		Long currentPage = new Long(begin/pageSize);
		
		Link linkFirstPage = new Link(UriBuilder.fromPath(uriInfo.getPath())
				.queryParam(PARAM_BEGIN, 0)
				.queryParam(PARAM_PAGE_SIZE, pageSize).build()
				.toString(), "firstPage");
		links.add(linkFirstPage);
		
		Link linkCurrentPage = new Link(UriBuilder.fromPath(uriInfo.getPath())
				.queryParam(PARAM_BEGIN, (currentPage+1)*pageSize)
				.queryParam(PARAM_PAGE_SIZE, pageSize).build()
				.toString(), "nextPage");
		links.add(linkCurrentPage);
		
		Link linkLastPage = new Link(UriBuilder.fromPath(uriInfo.getPath())
				.queryParam(PARAM_BEGIN, (numberOfPages-1)*pageSize)
				.queryParam(PARAM_PAGE_SIZE, pageSize).build()
				.toString(), "lastPage");
		links.add(linkLastPage);
		
		return links.toArray(new Link[] {});
	}
	
	/*
	 * The If-Modified-Since acts as a cache, only requesting the resource from the server if it wasn't
	 * updated since the date sent.
	 */
	@Override
	public Response find(Long id, Date modifiedSince) {
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
	@Override
	public Response create(@Context UriInfo uriInfo, User user) {
		em.persist(user);
		
		//Builds the url where the resource will be available
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		URI location = builder.path("/{id}").build(user.getId());
		
		return Response.created(location).build();
	}
	
	@Override
	public Response retrieveImage(Long id, Date modifiedSince) {
		User user = em.find(User.class, id);
		if (user != null) {
			Image image = user.getImage();
			if (image != null) {
				if (modifiedSince == null || image.getUpdateDate().after(modifiedSince)) {
					return Response.ok(image.getData(), image.getType())
							.header(FIELD_IMAGE_DESCRIPTION, image.getDescription()).build();
				}
				return Response.notModified().build();
			}
			
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Override
	public Response addImage(String description, Long userId, HttpServletRequest httpServletRequest, byte[] data) {
		/*
		 * To get the type of the image we could use the HeaderParam Content-Type,
		 * but it is also doable in another way (HttpServletResquet.getContentType)
		 */
		User user = em.find(User.class, userId);
		if (user != null) {
			Image image = new Image();
			image.setData(data);
			image.setDescription(description);
			image.setType(httpServletRequest.getContentType());
			user.setImage(image);
			em.merge(user);
			return Response.noContent().build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
		
	}

}