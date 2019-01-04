/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.cvsapi.bean.JwtAuthenticationToken;

/**
 * @author BYISHIMO PATRICK (byishimo.patrick@gmail.com)
 *
 */
public class JwtAuthenticationTokenFilter  extends AbstractAuthenticationProcessingFilter{

	public JwtAuthenticationTokenFilter() {
		super("/api/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String header = request.getHeader("Authorization");
		if(header== null || !header.startsWith("Token "))
			throw new RuntimeException("Token is missing");
//			System.out.println("Token is missing");
		
		String authenticationToken = header.substring(6);
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}

	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	

}
