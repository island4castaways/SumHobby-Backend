package com.example.sumhobby.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.repository.InquiryRepository;

@Service
public class InquiryService {
	
	@Autowired
	private InquiryRepository inqRepository;

}
