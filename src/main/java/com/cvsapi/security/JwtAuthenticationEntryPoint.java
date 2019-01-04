/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
	}

}
