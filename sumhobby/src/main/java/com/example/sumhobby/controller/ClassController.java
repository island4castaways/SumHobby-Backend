package com.example.sumhobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {

	@Autowired
	private ClassService service;
	
//	@GetMapping("/test")
//	public ResponseEntity<?> testClass(){
//		
//	}
}
