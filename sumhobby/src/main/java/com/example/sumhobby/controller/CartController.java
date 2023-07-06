package com.example.sumhobby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService service;
	
	
	@GetMapping("/testcart")
	public ResponseEntity<?> testTodo(){
		String str = "Hello World";
		List<String> list = new ArrayList<String>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
//	@PostMapping
//	public ResponseEntity<?> createCart(@RequestBody CartDTO dto){
//		
//	}
	
	@GetMapping
	public ResponseEntity<?> retrieveCartList(){
		
		List<CartEntity> entities = service.retrieve();
		
		List<CartDTO> dtos = entities.stream().map(CartDTO::new).collect(Collectors.toList());
		
		List<ClassDTO> cdtos = new ArrayList<>();
		
		for(int i =0; i<dtos.size();i++) {
			int classNum = dtos.get(i).getClassNum();
			ClassDTO classdto = new ClassDTO(service.classRetrieve(classNum).get());
			cdtos.add(classdto);
		}
		
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder().data(cdtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	

}
