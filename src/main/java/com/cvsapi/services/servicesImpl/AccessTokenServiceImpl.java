package com.cvsapi.services.servicesImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvsapi.bean.AccessToken;
import com.cvsapi.bean.AuthUser;
import com.cvsapi.dao.AccessTokenDAO;
import com.cvsapi.services.AccessTokenService;
import com.cvsapi.services.AuthUserService;


@Service("AccessTokenService")
public class AccessTokenServiceImpl implements AccessTokenService {
	
	@Autowired
	private AccessTokenDAO accessTokenDao;
	private static final Logger log = LoggerFactory.getLogger(AuthUserService.class);
	
	
	@Transactional
	public AccessToken createToken(AccessToken accessToken) {
		return accessTokenDao.save(accessToken);
	}

	
	public AccessToken updateToken(AccessToken accessToken) {
		return accessTokenDao.save(accessToken);
	}
	
	public AccessToken findByAuthUser(AuthUser user) {
		return accessTokenDao.findByAuthUser(user);
	}

}
