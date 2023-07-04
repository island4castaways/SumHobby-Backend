package com.example.sumhobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.service.AdminService;

@RestController("/admin")
public class AdminController {
	
	@Autowired
	private AdminService admService;
	
	@PostMapping("/login")
	public void login() {
		
	}

}
