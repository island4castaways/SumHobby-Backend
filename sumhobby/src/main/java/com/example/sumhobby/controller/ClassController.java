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
	public ResponseEntity<List<ClassDTO>> getTopRatedClassesByCategory() {
		List<ClassDTO> topRatedClasses = classService.getTopRatedClassesByCategory();
		if (topRatedClasses.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(topRatedClasses);
		}
	}
	

	
}
