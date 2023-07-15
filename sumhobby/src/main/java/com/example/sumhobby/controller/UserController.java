package com.example.sumhobby.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.security.TokenProvider;
import com.example.sumhobby.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	//userToken으로 userEntity 반환
	@GetMapping("/returnUser")
	public ResponseEntity<?> returnUser(Principal principal) {
		String token = principal.getName();
		UserEntity entity = userService.selectOne(token);
		UserDTO dto = new UserDTO(entity);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
		//패스워드 검증
		try {
			if(userDTO == null || userDTO.getPassword()== null) {
				throw new RuntimeException("Invalid Password value.");
			}
			// 요청을 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder()
					.userId(userDTO.getUserId())
					.password(pwEncoder.encode(userDTO.getPassword()))
					.email(userDTO.getEmail()) 
	                .userName(userDTO.getUserName())
	                .phone(userDTO.getPhone())
					.build();
			// 서비스를 이용해 리포지터리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder()	
					.userTk(registeredUser.getUserTk())//생성된 임의의 고유값 ID
					.userId(registeredUser.getUserId())
					.build();
			
			return ResponseEntity.ok().body(responseUserDTO);
		}catch (Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
		UserEntity user = userService.getByCredentials(
				userDTO.getUserId(), 
				userDTO.getPassword(), 
				pwEncoder);
		
		//검증
		if(user != null) {
			// 토큰 생성
			final String token = tokenProvider.create(user);
			final UserDTO responsUserDTO = UserDTO.builder()
					.userId(user.getUserId())
					.userTk(user.getUserTk())
					.token(token)
					.build();
			return ResponseEntity.ok().body(responsUserDTO);
		}else {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error("Login Failed.").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> Update(@AuthenticationPrincipal String userTk, UserDTO userDTO){
		UserEntity userEntity = userService.retrieveUser(userTk);
		userEntity = userService.modify(userEntity, userDTO);
		List<UserEntity> entities = new ArrayList<UserEntity>();
		entities.add(userEntity);
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/userinfo")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal String userTk) {
	    try {
	        UserEntity userEntity = userService.selectOne(userTk);
	        System.out.println(userEntity.toString());
//	        List<UserEntity> entities = new ArrayList<UserEntity>();
//	        entities.add(userEntity);
	        
//	        List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList()); 
//	        ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();
	        UserDTO dto = new UserDTO(userEntity);
	        return ResponseEntity.ok().body(dto);
	    } catch (Exception e) {
	        String errorMessage = e.getMessage();
	        ResponseDTO<String> response = ResponseDTO.<String>builder()
	                .error(errorMessage).build();
	        return ResponseEntity.ok().body(response);
	    }
	}




}
