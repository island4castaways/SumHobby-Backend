package com.example.sumhobby.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> modifyUser(@RequestBody UserDTO userDTO) {
	    try {
	        UserEntity user = null; // 유저 서비스의 modify 메소드 호출
	        return ResponseEntity.ok().body(user);
	    } catch (Exception e) {
	        String errorMessage = e.getMessage();
	        ResponseDTO<String> response = ResponseDTO.<String>builder()
	                .error(errorMessage)
	                .build();
	        return ResponseEntity.badRequest().body(response);
	    }
	}
// 유저 정보 수정할 때 정보 가지고와야 해서 필요	
	@GetMapping("/getuserinfo")
	public ResponseEntity<?> getUserInfo(@PathVariable String userId) {
	    try {
	        UserDTO userDTO = userService.getUserInfo(userId);
	        return ResponseEntity.ok().body(userDTO);
	    } catch (Exception e) {
	        ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
	        return ResponseEntity.badRequest().body(responseDTO);
	    }
	}


	
//	@DeleteMapping("/deleteClass")
//	public ResponseEntity<?> deleteClass(@RequestBody ClassDTO classDTO) {
//		try {
//			ClassEntity entity = classService.selectOne(classDTO.getClassNum());
//			classService.deleteOne(entity);
//			return getClasses();
//		} catch (Exception e) {
//			String msg = e.getMessage();
//			ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
//					.error(msg).build();
//			return ResponseEntity.badRequest().body(response);
//		}
//	}
	


}
