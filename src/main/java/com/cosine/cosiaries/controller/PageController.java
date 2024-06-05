package com.cosine.cosiaries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.cosine.cosiaries.wiki.*;

@Controller
public class PageController {
	/*
	@RequestMapping(value="/page/{pageName}", method = RequestMethod.GET)
	public String page() {
		return "index";
	}
	*/
	@RequestMapping(value="/createpage", method = RequestMethod.GET)
	public String createPage() {
		return "createpage";
	}
	
	@RequestMapping(value="/createpage", method = RequestMethod.POST)
	public ResponseEntity create(HttpServletRequest request, @RequestParam String title, @RequestParam String content) {
		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		System.out.println(username);
		if (username == null || username.contentEquals("")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			int result = Search.createPage(new Page(title,content,username));
			if (result == Search.SAMEPAGE) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			} else if (result == Search.SUCCESSFUL) {
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
		}
	}
}
