package com.example.sumhobby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService service;

	@GetMapping("/testcart")
	public ResponseEntity<?> testTodo() {
		String str = "Hello World";
		List<String> list = new ArrayList<String>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}


	@GetMapping
	public ResponseEntity<?> retrieveCartList() {

		List<CartEntity> entities = service.retrieve();

		List<CartDTO> dtos = entities.stream().map(CartDTO::new).collect(Collectors.toList());

		for (int i = 0; i < dtos.size(); i++) {
			ClassDTO classDTO = new ClassDTO(service.classRetrieve(dtos.get(i).getClassNum()).get());
			dtos.get(i).setClassName(classDTO.getClassName());
			dtos.get(i).setClassPrice(classDTO.getClassPrice());
			
			UserDTO userDTO = new UserDTO(service.userRetrieve(dtos.get(i).getUserTk()));
			dtos.get(i).setUserEmail(userDTO.getEmail());
			dtos.get(i).setUserName(userDTO.getUserName());
		}
		ResponseDTO<CartDTO> response = ResponseDTO.<CartDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCartList(@RequestBody CartDTO dto) {
		CartEntity entity = service.toEntity(dto);

		List<CartEntity> entities = service.delete(entity);
		
		List<CartDTO> dtos = entities.stream().map(CartDTO::new).collect(Collectors.toList());

		for (int i = 0; i < dtos.size(); i++) {
			ClassDTO classDTO = new ClassDTO(service.classRetrieve(dtos.get(i).getClassNum()).get());
			dtos.get(i).setClassName(classDTO.getClassName());
			dtos.get(i).setClassPrice(classDTO.getClassPrice());

		}

		ResponseDTO<CartDTO> response = ResponseDTO.<CartDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteCartList(@RequestBody CartDTO dto) {
		CartEntity entity = service.toEntity(dto);

		List<CartEntity> entities = service.delete(entity);
		
		List<CartDTO> dtos = entities.stream().map(CartDTO::new).collect(Collectors.toList());

		for (int i = 0; i < dtos.size(); i++) {
			ClassDTO classDTO = new ClassDTO(service.classRetrieve(dtos.get(i).getClassNum()).get());
			dtos.get(i).setClassName(classDTO.getClassName());
			dtos.get(i).setClassPrice(classDTO.getClassPrice());

		}

		ResponseDTO<CartDTO> response = ResponseDTO.<CartDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);
	}

}