package com.example.sumhobby.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.SearchDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.PaymentService;
import com.example.sumhobby.service.UserService;

@RestController
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private ClassService classService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService payService;

	@GetMapping("/category")
	public ResponseEntity<?> retrieve() {
		List<ClassEntity> entities = classService.retrieve();
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/myclasses")
	public ResponseEntity<?> getMyClasses(@AuthenticationPrincipal String userTk) {
		UserEntity userEntity = userService.selectOne(userTk);
		List<ClassEntity> entities = payService.selectClassRefsByUserRef(userEntity);
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);		
	}

	@GetMapping("/top-rated")
	public ResponseEntity<?> getTopRatedClassesByCategory() {

		List<ClassEntity> topRatedClasses = classService.getTop5RatedClassesByCategory();

		List<ClassDTO> dtos = topRatedClasses.stream().map(ClassDTO::new).collect(Collectors.toList());

		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);

	}
	
	@PatchMapping
	public ResponseEntity<?> getClass(@RequestBody ClassDTO classDTO) {
		ClassEntity entity = classService.selectOne(classDTO.getClassNum());
		ClassDTO dto = new ClassDTO(entity);
		return ResponseEntity.ok().body(dto);
	}
	
	@PatchMapping("/search")
	public ResponseEntity<?> searchClasses(@RequestBody SearchDTO searchDTO) {
		List<ClassEntity> entities = classService.selectBySearchKey(searchDTO.getSearchKey());
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
}