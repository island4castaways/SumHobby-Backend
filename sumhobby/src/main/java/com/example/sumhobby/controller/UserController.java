package com.example.sumhobby.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.PasswordDTO;
import com.example.sumhobby.dto.PaymentDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.security.TokenProvider;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.PaymentService;
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
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private PaymentService payService;

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
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
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
			UserEntity userEntity = userService.selectOne(userDTO.getUserTk());
			userEntity.setPhone(userDTO.getPhone());
			userEntity.setEmail(userDTO.getEmail());
			userService.modifyUser(userEntity); // update 메서드로 수정된 정보를 저장

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PutMapping("/modifypw")
	public ResponseEntity<?> modifyPw(@AuthenticationPrincipal String userTk,
			@RequestBody PasswordDTO password) {
		System.out.println(userTk);
		System.out.println(password.toString());
		try {
			String success = userService.pwmodify(userTk, password.getOriginalPW(), password.getNewPW(), pwEncoder);
			System.out.println(success);
			List<String> list = new ArrayList<String>();
			list.add(success);
			ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
			System.out.println(success);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			System.out.println("실패");
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	// 넘어간 것 같아서 유효성 검사 넣어둔건데... 디비보니까 안 넘ㅇ어갔더라고..허허
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
	
	@PutMapping("/changeRole")
	public ResponseEntity<?> changeRole(@RequestBody UserDTO userDTO) {
		UserEntity entity = userService.selectOne(userDTO.getUserTk());
		entity.setRole("강사 신청");
		userService.update(entity);
		UserDTO dto = new UserDTO(entity);
		return ResponseEntity.ok().body(dto);
	}
	
	@PatchMapping("/classes")
	public ResponseEntity<?> getClasses(@RequestBody UserDTO userDTO) {
		UserEntity userEntity = userService.selectOne(userDTO.getUserTk());
		List<ClassEntity> entities = classService.seletAllByUserRef(userEntity);
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PatchMapping("/payments")
	public ResponseEntity<?> getPayments(@RequestBody UserDTO userDTO) {
		UserEntity userEntity = userService.selectOne(userDTO.getUserTk());
		List<PaymentEntity> entities = payService.selectByUserRef(userEntity);
		List<PaymentDTO> dtos = entities.stream().map(PaymentDTO::new).collect(Collectors.toList());
		ResponseDTO<PaymentDTO> response = ResponseDTO.<PaymentDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}

}
