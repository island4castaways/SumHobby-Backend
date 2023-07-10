package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> selectAll() {
		return userRepository.findAll();
	}
	
	public UserEntity saveOne(final UserEntity entity) {
		return userRepository.save(entity);
	}
	
	public UserEntity selectOne(String userTk) {
		return userRepository.findById(userTk).get();
	}

}
