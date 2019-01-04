/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.bean;


public class JwtUser {

	private Integer id;
	private String userName;
	private String role;
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
