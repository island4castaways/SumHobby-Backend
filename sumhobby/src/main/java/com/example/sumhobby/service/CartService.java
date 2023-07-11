package com.example.sumhobby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.repository.CartRepository;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
//	public List<CartEntity> create(final CartEntity entity){
//		if(entity == null) {
//			log.warn("Entity cannot be null.");
//			throw new RuntimeException("Entity cannot be null");
//		}
//		
//		cartRepository.save(entity);
//		return cartRepository.findById(entity.getCartNum());
//	}
	
	public List<CartEntity> retrieve(){
		return cartRepository.findAll();
		//나중에 유저로 수정 예정 
	}
	
	public Optional<ClassEntity> classRetrieve(int classNum){
		return classRepository.findById(classNum);
	}
	
	
	

}
