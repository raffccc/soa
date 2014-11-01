package com.knight.user.service.test.integration;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class UsersServiceIT {

	public static String SERVICES_CONTEXT = 
			"http://localhost:8080/cap5rest/services";
	
	public static String USERS_CONTEXT =
			SERVICES_CONTEXT + "/users";
	
	private byte[] myPhoto;
	
	@Before
	public void setup() throws IOException {
		
	}
	
	@Test
	public void testRetrieveImages() throws Exception {
		
	}
	
}