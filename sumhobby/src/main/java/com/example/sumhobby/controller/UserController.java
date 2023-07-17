package com.example.sumhobby.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.InquiryDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.security.TokenProvider;
import com.example.sumhobby.service.InquiryService;
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

	@Autowired
	private InquiryService inqService;

	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

	// userToken으로 userEntity 반환
	@GetMapping("/returnUser")
	public ResponseEntity<?> returnUser(Principal principal) {
		String token = principal.getName();
		UserEntity entity = userService.selectOne(token);
		UserDTO dto = new UserDTO(entity);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		// 패스워드 검증
		try {
			if (userDTO == null || userDTO.getPassword() == null) {
				throw new RuntimeException("Invalid Password value.");
			}
			// 요청을 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder().userId(userDTO.getUserId())
					.password(pwEncoder.encode(userDTO.getPassword())).email(userDTO.getEmail())
					.userName(userDTO.getUserName()).phone(userDTO.getPhone()).build();
			// 서비스를 이용해 리포지터리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder().userTk(registeredUser.getUserTk())// 생성된 임의의 고유값 ID
					.userId(registeredUser.getUserId()).build();

			return ResponseEntity.ok().body(responseUserDTO);
		} catch (Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(userDTO.getUserId(), userDTO.getPassword(), pwEncoder);

		// 검증
		if (user != null) {
			// 토큰 생성
			final String token = tokenProvider.create(user);
			final UserDTO responsUserDTO = UserDTO.builder().userId(user.getUserId()).userTk(user.getUserTk())
					.token(token).build();
			return ResponseEntity.ok().body(responsUserDTO);
		} else {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error("Login Failed.").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PutMapping("/modifyuser")
	public ResponseEntity<?> modifyUserInfo(@RequestBody UserDTO userDTO) {
		try {
			UserEntity userEntity = userService.selectOne(userDTO.getUserId());
			userEntity.setPhone(userDTO.getPhone());
			userEntity.setEmail(userDTO.getEmail());
			userService.create(userEntity);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

//	@PutMapping("/modifypw")
//	public ResponseEntity<?> modifypw(@RequestBody UserDTO userDTO) {
//		try {
//			UserEntity userEntity = userService.selectOne(userDTO.getUserId());
//			userEntity.setPassword(userDTO.getPassword());			
//			userService.create(userEntity);
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			String msg = e.getMessage();		
//			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			return ResponseEntity.badRequest().body(responseDTO);
//		}
//	}
//	

	@PostMapping("/checkEmail")
	public ResponseEntity<?> checkDuplicateEmail(@RequestBody UserDTO userDTO) {
		if (userService.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.ok("Email already exists");
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping(path = "/checkPhone")
	public ResponseEntity<?> checkDuplicatePhone(@RequestBody UserDTO userDTO) {
		if (userService.existsByPhone(userDTO.getPhone())) {
			return ResponseEntity.ok("Phone number already exists");
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping("/userinfo")
	public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal String userTk) {
		try {
			UserEntity userEntity = userService.selectOne(userTk);
			System.out.println(userEntity.toString());
			UserDTO dto = new UserDTO(userEntity);
			return ResponseEntity.ok().body(dto);

		} catch (Exception e) {
			String errorMessage = e.getMessage();
			ResponseDTO<String> response = ResponseDTO.<String>builder().error(errorMessage).build();
			return ResponseEntity.ok().body(response);
		}
	}

	@PostMapping("/inquiry")
	public ResponseEntity<?> createInquiry(@RequestBody InquiryDTO inquiryDTO) {
	    try {
	        String inqContent = inquiryDTO.getInqContent();
	        
	        InquiryEntity entity = InquiryEntity.builder()
	                .inqContent(inqContent)
	                .inqDate(Timestamp.valueOf(LocalDateTime.now()))
	                .userRef(userService.selectOneByUserId(inquiryDTO.getUserId()))
	                .build();

	        inqService.create(entity);

	        UserEntity userEntity = userService.selectOneByUserId(inquiryDTO.getUserId());
	        return getInquiry(new UserDTO(userEntity));
	    } catch (Exception e) {
	        String msg = e.getMessage();
	        ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().error(msg).build();
	        return ResponseEntity.badRequest().body(response);
	    }
	}

	@PatchMapping("/inquiry")
	public ResponseEntity<?> getInquiry(@RequestBody UserDTO userDTO) {
		UserEntity userEntity = userService.selectOneByUserId(userDTO.getUserId());
		List<InquiryEntity> entities = inqService.selectByUserRef(userEntity);
		List<InquiryDTO> dtos = entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
		ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

}
