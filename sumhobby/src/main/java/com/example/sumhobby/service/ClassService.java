package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;
import com.example.sumhobby.util.Util;

@Service
public class ClassService {
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public ClassEntity dtoToEntity(ClassDTO dto) {
		ClassEntity entity = new ClassEntity();
		entity.setClassNum(dto.getClassNum());
		entity.setClassName(dto.getClassName());
		entity.setClassRate(dto.getClassRate());
		entity.setClassPrice(dto.getClassPrice());
		entity.setClassSetDate(Util.stringToTimestamp(dto.getClassSetDate()));
		entity.setClassLastDate(Util.stringToTimestamp(dto.getClassLastDate()));
		entity.setUserRef(userRepository.findById(dto.getUserTk()).get());
		
		return entity;
	}
	
	public ClassDTO entityToDto(ClassEntity entity) {
		ClassDTO dto = ClassDTO.builder()
				.classNum(entity.getClassNum())
				.className(entity.getClassName())
				.classRate(entity.getClassRate())
				.classPrice(entity.getClassPrice())
				.classSetDate(Util.timestampToString(entity.getClassSetDate()))
				.classLastDate(Util.timestampToString(entity.getClassLastDate()))
				.userTk(entity.getUserRef().getUserTk())
				.build();
		
		return dto;
	}

}