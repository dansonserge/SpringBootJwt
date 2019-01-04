/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="authUser")
public class AuthUser implements Serializable {
	
	private static final long serialVersionUID =325468713L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String firstName;
	private String lastName;
	
	@Column(unique = true)
	@Email(message="not a valid email address")
	private String email;
	
	@Column(unique = true)
	private String telephone;
	
	private String password;
	@ColumnDefault(value = "0")
	
	private String userType;
	
	
	

	@OneToMany(mappedBy="authUser", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<AccessToken> accessToken;
	
	
	
	
	public List<AccessToken> getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(List<AccessToken> accessToken) {
		this.accessToken = accessToken;
	}
	
	
	public Integer getId() {
		return id;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public AuthUser(Integer id, String firstName, String lastName, String email, String telephone, String password,
			String userType) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone = telephone;
		this.password = password;
		this.userType = userType;
	}
	public AuthUser() {
		super();
	}
	
}
