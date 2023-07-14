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

}