package com.cvsapi.services.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.cvsapi.bean.AccessToken;
import com.cvsapi.bean.AuthUser;
import com.cvsapi.bean.JwtUser;
import com.cvsapi.dao.AccessTokenDAO;
import com.cvsapi.dao.AuthUserDAO;
import com.cvsapi.security.JwtGenerator;
import com.cvsapi.services.AuthUserService;



@Service("AuthUserService")
public class AuthUserServiceImpl implements AuthUserService {
	
	
	@Autowired
	private AccessTokenDAO accessTokenDao;
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private AuthUserDAO authUserDao;
	
	private static final Logger log = LoggerFactory.getLogger(AuthUserService.class);

	@Override
	public AuthUser addUser(AuthUser user) {
		AuthUser localUser = authUserDao.findByEmail(user.getEmail());
		if(localUser!=null) {
			log.info("User with email {} already exist", user.getEmail());
		}else {
			
			localUser = authUserDao.save(user);
			
			LocalDate LocalExpireDate = LocalDate.now().plusMonths(6);
			Date expireDate = Date.from(LocalExpireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			// GENERATE TOKEN BASED ON THE USER
			JwtUser jwtUser = new JwtUser();
			jwtUser.setUserName(localUser.getEmail());
			jwtUser.setId(localUser.getId());
			jwtUser.setRole(localUser.getUserType());
			jwtGenerator = new JwtGenerator();
			
			String token = jwtGenerator.generate(jwtUser);
			
			// STORE DETAILS ABOUT THE GENERATED TOKEN
			AccessToken accessToken = new AccessToken();
			accessToken.setToken(token);
			
			accessToken.setAppId(1);
			accessToken.setCreatedOn(new Date());			
			accessToken.setExpires(expireDate);
			accessToken.setUpdatedOn(new Date());
			accessToken.setAuthUser(localUser);
			accessTokenDao.save(accessToken);
			
		}
		return localUser;
	}

	@Override
	public AuthUser update(AuthUser user) {
		
		return null;
	}

	@Override
	public AuthUser findByEmail(String email) {
		
		return authUserDao.findByEmail(email);
		
	}

	@Override
	public AuthUser save(AuthUser user) {
		
		return null;
	}

	@Override
	public AuthUser findOne(int id) {
		
		return null;
	}

}
