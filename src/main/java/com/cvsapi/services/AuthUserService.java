/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.services;

import com.cvsapi.bean.AuthUser;

public interface AuthUserService {

    public AuthUser addUser(AuthUser user);

    public AuthUser update(AuthUser user);

    public AuthUser findByEmail(String email);

    public AuthUser save(AuthUser user);

    public AuthUser findOne(int id);

}
