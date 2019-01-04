/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */


package com.cvsapi.config;

import java.util.Collections;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cvsapi.security.JwtAuthenticationEntryPoint;
import com.cvsapi.security.JwtAuthenticationProvider;
import com.cvsapi.security.JwtAuthenticationTokenFilter;
import com.cvsapi.security.JwtSuccessHandler;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityUtility extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationProvider authenticationprovider;
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(authenticationprovider));
	}
	
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilter() {
		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
		return filter;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("GET");
		config.addExposedHeader("Authorization");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/auth/**,/**"
	};
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf()
//		.disable().httpBasic()
//		.and().authorizeRequests()
//		.antMatchers(PUBLIC_MATCHERS).permitAll()
//		.anyRequest().authenticated();
		
//		http.csrf().disable()
//		.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated()
//		.and()
//		.exceptionHandling().authenticationEntryPoint(entryPoint)
//		.and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//		http.headers().cacheControl();
		
		http.csrf().disable().exceptionHandling()
			.authenticationEntryPoint(entryPoint).and()
			.anonymous().and()
			.servletApi().and()
			.headers().cacheControl().and()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyRequest().authenticated().and()
			//.addFilterAfter(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
			.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	public static String argon2Hashing(String str) {
		// Create instance
//		Argon2 argon2 = Argon2Factory.createAdvanced(12, 16);
    	Argon2 argon2 = Argon2Factory.create();

		// Read password from user
		char[] password = str.toCharArray();

		try {
			// Hash password
			String hash = argon2.hash(2, 512, 2, password);
			//String hash = argon2.hash(2, 65536, 1, password);

			// Verify password
			//if (argon2.verify(hash, password)) {
			if(argon2.verify(hash, password)) {
				return hash;
			} else {
				return null;
			}
		} finally {
			// Wipe confidential data
			argon2.wipeArray(password);
		}
	}
	public static boolean argon2Verify(String hash, String password) {
		Argon2 argon2 = Argon2Factory.create();
		if(argon2.verify(hash, password))
			return true;
		else
			return false;
	}
	@Bean
	public static String getRandomPassword() {
		String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while(salt.length()<64) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();
	}
}
