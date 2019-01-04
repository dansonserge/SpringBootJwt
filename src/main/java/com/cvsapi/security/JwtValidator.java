/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.security;

import org.springframework.stereotype.Component;

import com.cvsapi.bean.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

	private String secret = "easyInsurer";
	public JwtUser validate(String token) {
		
		JwtUser jwtUser = null;
		try {
			
			Claims body = Jwts.parser().setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
			jwtUser = new JwtUser();
			jwtUser.setUserName(body.getSubject());
			jwtUser.setId(Integer.parseInt((String) body.get("userId")));
			jwtUser.setRole((String) body.get("role"));
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return jwtUser;
	}

}
