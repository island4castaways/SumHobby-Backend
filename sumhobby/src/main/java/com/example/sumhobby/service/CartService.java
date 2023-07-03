package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.repository.CartRepository;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
//	public CartEntity dtoToEntity(CartDTO dto) {
//		CartEntity entity = new CartEntity();
//		entity.setCartNum(dto.getCartNum());
//		entity.setUserRef(userRepository.findById(dto.getUserTk()).get());
//		entity.setClassRef(classRepository.findById(dto.getClassNum()).get());
//		
//		return entity;
//	}
//	
//	public CartDTO entityToDto(CartEntity entity) {
//		CartDTO dto = CartDTO.builder()
//				.cartNum(entity.getCartNum())
//				.userTk(entity.getUserRef().getUserTk())
//				.classNum(entity.getClassRef().getClassNum())
//				.build();
//		
//		return dto;
//	}
	
	

}
