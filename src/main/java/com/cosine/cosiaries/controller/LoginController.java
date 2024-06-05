package com.cosine.cosiaries.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cosine.cosiaries.model.login.UserUtils;
import com.cosine.cosiaries.utils.StringEntity;


@Controller
public class LoginController {
	
	@RequestMapping (value="/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping (value="/register", method = RequestMethod.GET)
	public String registerPage() {
		return "register";
	}
	
	@RequestMapping (value="/whoami", method = RequestMethod.GET)
	public ResponseEntity<StringEntity> whoami(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		return new ResponseEntity<>(new StringEntity(username), HttpStatus.OK);
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity login(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
		try {
			boolean f = UserUtils.validate(username, password);
			if (f) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				session.setMaxInactiveInterval(40 * 60);
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity register(HttpServletRequest request, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
		int x = UserUtils.createUser(username,password,email);
		if (x == UserUtils.SUCCESSFUL) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else if (x == UserUtils.SAMEUSER) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
