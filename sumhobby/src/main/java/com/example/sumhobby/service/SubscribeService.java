package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.repository.SubscribeRepository;

@Service
public class SubscribeService {
	
	@Autowired
	private SubscribeRepository subsRepository;

}
