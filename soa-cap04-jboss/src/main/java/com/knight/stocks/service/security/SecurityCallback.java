package com.knight.stocks.service.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Uses Apache CXF
 *
 */
public class SecurityCallback implements CallbackHandler {

	/*
	 * (non-Javadoc)
	 * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
	 */
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback callback : callbacks) {
			if (callback instanceof WSPasswordCallback) {
				WSPasswordCallback pc = (WSPasswordCallback)callback;
				System.out.println(pc);
			}
		}
		
	}

}