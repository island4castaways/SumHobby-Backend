package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository quesRepository;

}
