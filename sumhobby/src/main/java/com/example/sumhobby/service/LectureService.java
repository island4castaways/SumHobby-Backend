package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.repository.LectureRepository;

@Service
public class LectureService {
	
	@Autowired
	private LectureRepository lecRepository;
	
	public List<LectureEntity> selectAll() {
		return lecRepository.findAll();
	}

}
