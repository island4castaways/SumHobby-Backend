package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.sumhobby.dto.PaymentDTO;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.PaymentRepository;
import com.example.sumhobby.repository.PaymentRespRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	@Autowired
	private PaymentRespRepository payRespRepository;
	
	public PaymentEntity toEntity(PaymentDTO paymentDTO) {
		return PaymentDTO.toEntity(paymentDTO, classRepository, userRepository);
	}
	
	public List<PaymentEntity> selectAll() {
		return paymentRepository.findAll();
	}
	
	public List<PaymentEntity> create(final PaymentEntity entity){
		paymentRepository.save(entity);
		return paymentRepository.findAll();
	}

}
