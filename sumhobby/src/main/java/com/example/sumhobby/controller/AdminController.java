package com.example.sumhobby.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.service.AdminService;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService admService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
	@PostMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers() {
		List<UserEntity> entities = userService.selectAll();
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/users")
	public ResponseEntity<?> approveTeacher(@RequestBody UserDTO userDTO) {
		UserEntity entity = userService.selectOne(userDTO.getUserTk());
		if(entity.getTeacher() == 0 || entity.getTeacher() == 1) {
			entity.setTeacher(2);			
		} else if(entity.getTeacher() == 2) {
			entity.setTeacher(0);
		}
		userService.saveOne(entity);
		return getUsers();
	}
	
//	@GetMapping("classes")
//	public ResponseEntity<?> getClasses() {
//		List<ClassEntity> entities = classService.;
//		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
//		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
//				.data(dtos).build();
//		return ResponseEntity.ok().body(response);
//	}

}