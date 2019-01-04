/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.services;

import com.cvsapi.bean.AccessToken;
import com.cvsapi.bean.AuthUser;

public interface AccessTokenService {
	
	public AccessToken createToken(AccessToken accessToken);
	public AccessToken updateToken(AccessToken accessToken);
	public AccessToken findByAuthUser(AuthUser user);

}
