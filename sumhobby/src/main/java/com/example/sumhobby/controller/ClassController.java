package com.example.sumhobby.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.service.ClassService;

@RestController
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private ClassService classService;
	
	@GetMapping("/category")
	public ResponseEntity<?> retrieve(){
		List<ClassEntity> entities = classService.retrieve();
		
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/top-rated")
	public ResponseEntity<?> getTopRatedClassesByCategory() {
		
		List<ClassEntity> topRatedClasses = classService.getTopRatedClassesByCategory();
		
		List<ClassDTO> dtos = topRatedClasses.stream().map(ClassDTO::new).collect(Collectors.toList());
		//List<ClassDTO> topRatedClasses = classService.getTopRatedClassesByCategory();
		
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
			
		}
	}
