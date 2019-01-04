

/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Serge danson (www.sergedanson.com)
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter{

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type");
//		try {
//			chain.doFilter(req, response);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
			
			try {
				chain.doFilter(req, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allowed-Methods", "POST, GET, DELETE");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Expose-Headers", "Authorization");
		}
	}
	public void init(FilterConfig filterConfig) {}
//	
	public void destroy() {}
}
