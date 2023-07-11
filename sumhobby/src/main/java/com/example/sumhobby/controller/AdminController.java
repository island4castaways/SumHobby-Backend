package com.example.sumhobby.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.security.TokenProvider;
import com.example.sumhobby.service.AdminService;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.LectureService;
import com.example.sumhobby.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private LectureService lecService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
		UserEntity entity = userService.getByCredentials(userDTO.getUserId(), userDTO.getPassword());
		if(entity != null) {
			final String token = tokenProvider.create(entity);
			final UserDTO dto = UserDTO.builder()
					.userId(entity.getUserId())
					.userTk(entity.getUserTk())
					.userName(entity.getUserName())
					.token(token)
					.role(entity.getRole())
					.build();
			return ResponseEntity.ok().body(dto);
		} else {
			ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().error("Login Failed.").build();
			return ResponseEntity.badRequest().body(response);
		}
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
		if(entity.getRole().equals("일반") || entity.getRole().equals("강사 신청")) {
			entity.setRole("강사");			
		} else if(entity.getRole().equals("강사")) {
			entity.setRole("일반");
		}
		UserEntity updated = userService.update(entity);
		return getUsers();
	}
	
	@GetMapping("/classes")
	public ResponseEntity<?> getClasses() {
		List<ClassEntity> entities = classService.selectAll();
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/createClass")
	public ResponseEntity<?> createClass(@RequestBody ClassDTO classDTO) {
		try {
			ClassEntity entity = ClassEntity.builder()
					.className(classDTO.getClassName())
					.classDetail(classDTO.getClassDetail())
					.classCategory(classDTO.getClassCategory())
					.userRef(userService.selectOneByUserId(classDTO.getUserId()))
					.classPrice(classDTO.getClassPrice())
					.classSetDate(Timestamp.valueOf(LocalDateTime.now()))
					.classLastDate(Timestamp.valueOf(LocalDateTime.now()))
					.build();
			classService.create(entity);
			return getClasses();
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/lectures")
	public ResponseEntity<?> getLectures(@RequestBody ClassDTO classDTO) {
		List<LectureEntity> entities = lecService.selectAll();
		List<LectureDTO> dtos = entities.stream().map(LectureDTO::new).collect(Collectors.toList());
		ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

}