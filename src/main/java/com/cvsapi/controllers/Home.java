/**
 * @author Serge Danson Ndekezi (www.sergedanson.com)
 */

package com.cvsapi.controllers;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void home() {
		System.out.println("we are testing");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getActiveolidays(@RequestParam Map<String, String> mapper,
			HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
