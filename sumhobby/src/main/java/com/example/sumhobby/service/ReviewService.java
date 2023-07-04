package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository revRepository;

}
