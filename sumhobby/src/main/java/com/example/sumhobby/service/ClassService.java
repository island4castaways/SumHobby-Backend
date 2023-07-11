package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.repository.ClassRepository;

@Service
public class ClassService {
	
	@Autowired
	private ClassRepository classRepository;
	
	public List<ClassEntity> selectAll() {
		return classRepository.findAll();
	}
	
	public ClassEntity create(final ClassEntity entity) {
		return classRepository.save(entity);
	}

}