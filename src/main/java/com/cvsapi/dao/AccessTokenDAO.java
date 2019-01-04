/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.dao;

import org.springframework.data.repository.CrudRepository;

import com.cvsapi.bean.AccessToken;
import com.cvsapi.bean.AuthUser;

public interface AccessTokenDAO extends CrudRepository<AccessToken, Integer> {
	
	public AccessToken findByAuthUser(AuthUser user);
	
}
