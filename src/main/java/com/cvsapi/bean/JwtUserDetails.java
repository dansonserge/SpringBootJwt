/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.bean;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails{

	private Integer id;
	private String userName;
	private String token;
	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * @param id
	 * @param userName
	 * @param token
	 * @param authorities
	 */
	public JwtUserDetails(Integer id, String userName, String token,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.userName = userName;
		this.token = token;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}
	
	
}
