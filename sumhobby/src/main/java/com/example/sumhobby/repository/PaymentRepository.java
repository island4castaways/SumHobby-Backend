package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.UserEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

	List<PaymentEntity> findALLByOrderId(String orderId);
	
	PaymentEntity findByClassRefAndUserRef(ClassEntity classEntity, UserEntity userEntity);
}

