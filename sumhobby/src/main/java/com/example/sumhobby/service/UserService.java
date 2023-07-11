package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> selectAll() {
		return userRepository.findAll();
	}
	
	public UserEntity selectOne(String userTk) {
		return userRepository.findById(userTk).get();
	}
	
	public UserEntity selectOneByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}
	
	public UserEntity update(final UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getUserId() == null) {
			throw new RuntimeException("Invalid Arguments");
		}
		final String userId = userEntity.getUserId();
		if(userRepository.existsByUserId(userId)) {
			log.warn("UserId already exits {}", userId);
			throw new RuntimeException("UserId already exits");
		}
		
		return userRepository.save(userEntity);
	}
	
	public UserEntity getByCredentials(final String userId, final String password) {
		return userRepository.findByUserIdAndPassword(userId, password);
	}

}