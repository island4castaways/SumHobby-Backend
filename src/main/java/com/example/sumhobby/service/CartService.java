package com.example.sumhobby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.UserEntity;
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
	
	public List<CartEntity> retrieve(String userTk){
		UserEntity entity = userRepository.findById(userTk).get();
		return cartRepository.findByUserRef(entity);
		//나중에 유저로 수정 예정 
	}
	
	public Optional<ClassEntity> classRetrieve(int classNum){
		
		return classRepository.findById(classNum);
	}
	
	public List<CartEntity> delete(final CartEntity entity){
		cartRepository.delete(entity);
		return retrieve(entity.getUserRef().getUserTk());
	}
	
	public CartEntity toEntity(CartDTO cartDTO) {
//		if(cartDTO.getCartNum() == 0) {
//			selectByClassRefAndUserRef(classRepository.findById(cartDTO.getClassNum()).get(), userRepository.findById(cartDTO.getUserTk()).get());
//		}
		return CartDTO.toEntity(cartDTO, classRepository, userRepository);
	}
	
	public UserEntity userRetrieve(String userTK){
		return userRepository.findById(userTK).get();
	}
	
	public CartEntity selectByClassRefAndUserRef(ClassEntity classEntity, UserEntity userEntity) {
		return cartRepository.findByClassRefAndUserRef(classEntity, userEntity);
	}
	
	public List<CartEntity> create(final CartEntity entity){
		cartRepository.save(entity);
		return retrieve(entity.getUserRef().getUserTk());
	}
	
}