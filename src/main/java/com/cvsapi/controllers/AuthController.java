/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.controllers;

import java.util.HashMap;
import java.util.Map;
import com.cvsapi.bean.AccessToken;
import com.cvsapi.bean.AuthUser;
import com.cvsapi.bean.JwtUser;
import com.cvsapi.config.SecurityUtility;
import com.cvsapi.security.JwtGenerator;
import com.cvsapi.services.AuthUserService;
import com.cvsapi.services.servicesImpl.AccessTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/auth")
public class AuthController {
	private JwtGenerator jwtGenerator;
	
	@Autowired
	AuthUserService userService;
	
	@Autowired
	AccessTokenServiceImpl tokenService;

	private static final Logger log = LoggerFactory.getLogger(AuthUserService.class);
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> signup(@RequestParam Map<String, String> mapper) {
    	
		String firstname = mapper.containsKey("firstname") ? mapper.get("firstname") : "";
		String lastname = mapper.containsKey("lastname") ? mapper.get("lastname") : "";
		String email = mapper.containsKey("email") ? mapper.get("email") : "";
		String password = mapper.containsKey("password") ? mapper.get("password") : "";
		String phone = mapper.containsKey("telephone") ? mapper.get("telephone") : "";
		String userType = mapper.containsKey("user_type") ? mapper.get("user_type") : "";
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		if(firstname.trim().isEmpty()) {
			response.put("detail", "The First Name is required");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(firstname.trim().isEmpty()) {
			response.put("detail", "The Last Name is required");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(email.trim().isEmpty()) {
			response.put("detail", "Must provide Email");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(password.trim().isEmpty()) {
			response.put("detail", "Must provide the Password");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(phone.trim().isEmpty()) {
			response.put("detail", "Phone is required");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(userType.trim().isEmpty()) {
			response.put("detail", "User type is required");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(userService.findByEmail(email) != null) {
			response.put("detail", "User with email "+ email +" already exist");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		AuthUser user = new AuthUser();
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setPassword(SecurityUtility.argon2Hashing(password));
    	user.setTelephone(phone);
    	user.setUserType(userType);
    	
    	AccessToken newToken = new AccessToken();
    	
    	AuthUser newUser = userService.addUser(user);
    	
    	if(newUser != null) {
    		
    		newToken = tokenService.findByAuthUser(user);
    		response.put("id", String.valueOf(user.getId()));
			response.put("token", newToken.getToken());
			response.put("user_type", user.getUserType());
			response.put("first_name", user.getFirstName());
			response.put("last_name", user.getLastName());
			log.info("SAVING LOG ::: {} | Account created successfully", user.getEmail());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    		
    	}else {
    		
    		response.put("detail", "Oups, having problem creating your account, please try again later.");
			log.info("SAVING LOG ::: {} | attempt to create account and failed", user.getEmail());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    	}
    	
    }
	
	
	   @RequestMapping(value = "/login", method = RequestMethod.POST)
	    public ResponseEntity<Map<String, Object>> token(@RequestParam Map<String, String> mapper) {

	    	Map<String, Object> response = new HashMap<String, Object>();
			
	        String email = mapper.containsKey("email") ? mapper.get("email") : "";
	        String password = mapper.containsKey("password") ? mapper.get("password") : "";

	        if (email.trim().isEmpty()) {
	            response.put("message", "Email is required!");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	        }
	        if (password.trim().isEmpty()) {
	            response.put("message", "Password is required");
	            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	        }
	        
	        AuthUser user = null;
	        
	                user = userService.findByEmail(email);
	                if (user == null) {
	                    response.put("detail", "User not found");
	                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	                }
	               
	                if (!SecurityUtility.argon2Verify(user.getPassword(), password)) {
	                    response.put("detail", "Wrong userName or Password");
	                    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	                }

	                JwtUser jwtUser = new JwtUser();
	                jwtUser.setUserName(user.getEmail());
	                jwtUser.setId(user.getId());
	                jwtUser.setRole("admin");
	                jwtGenerator = new JwtGenerator();
	                AccessToken accessToken = tokenService.findByAuthUser(user);
	                String token = jwtGenerator.generate(jwtUser);

	                response.put("id", String.valueOf(user.getId()));
	                response.put("token", token);
	                response.put("first_name", user.getFirstName());
	                response.put("last_name", user.getLastName());
	                response.put("user_type", user.getUserType());

	                accessToken.setToken(token);
	                tokenService.updateToken(accessToken);
	        

	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	    }
	    

}
