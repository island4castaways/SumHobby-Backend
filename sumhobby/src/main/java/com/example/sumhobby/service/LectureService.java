package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.repository.LectureRepository;

@Service
public class LectureService {
	
	@Autowired
	private LectureRepository lecRepository;

}
