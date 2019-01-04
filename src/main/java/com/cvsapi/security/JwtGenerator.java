/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.security;

import org.springframework.stereotype.Component;

import com.cvsapi.bean.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(jwtUser.getUserName());
		claims.put("userId", String.valueOf(jwtUser.getId()));
		claims.put("role", jwtUser.getRole());
		return Jwts.builder().setClaims(claims)
			.signWith(SignatureAlgorithm.HS512, "easyInsurer")
			.compact();
	}
}
