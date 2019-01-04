/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.dao;

import org.springframework.data.repository.CrudRepository;
import com.cvsapi.bean.AuthUser;

public interface AuthUserDAO extends CrudRepository<AuthUser,Long> {

	public AuthUser findByEmail(String email);
	
}
