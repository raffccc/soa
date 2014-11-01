package com.knight.user.service.test.integration;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.knight.user.service.UsersServiceInterface;

public class UsersServiceIT {

	public static String SERVICES_CONTEXT =	"http://localhost:8080/cap5rest/services";
	
	public static String USERS_CONTEXT = SERVICES_CONTEXT + "/users";
	
	private byte[] myPhoto;
	
	/*
	 * Using the Proxy Factory from RestEasy and an interface containing
	 * all the Rest services.
	 * 
	 * This promotes high coupling with resteasy and java service
	 */
	private UsersServiceInterface usersService;
	
	@Before
	public void setup() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(UsersServiceIT.class
				.getResourceAsStream("/saudate.png"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", baos);
		this.myPhoto = baos.toByteArray();
		
		this.usersService =	ProxyFactory.create(UsersServiceInterface.class, SERVICES_CONTEXT);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRetrieveImages() throws Exception {
		//This solution is dependent on the service being written in java
		ClientResponse<byte[]> clientResponse = (ClientResponse<byte[]>) this.usersService.retrieveImage(1l, null);
		
		//This is a generic way, this doesn't depend in which language the service was written.
//		ClientResponse<byte[]> clientResponse = new ClientRequest(
//				USERS_CONTEXT + "/{id}").pathParameters(1).accept("image/*")
//				.get(byte[].class);
		
		Assert.assertEquals(Status.OK.getStatusCode(), clientResponse.getStatus());
		Assert.assertArrayEquals(this.myPhoto, clientResponse.getEntity(byte[].class));
		
		String description = clientResponse.getHeaders().getFirst(UsersServiceInterface.FIELD_IMAGE_DESCRIPTION);
		Assert.assertEquals("Alexandre Saudate - 2012", description);
	}
	
}