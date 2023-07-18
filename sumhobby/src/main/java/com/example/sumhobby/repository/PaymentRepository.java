package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.UserEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

	List<PaymentEntity> findALLByOrderId(String orderId);
	
	List<PaymentEntity> findByUserRef(UserEntity userEntity);
	
	@Query("SELECT p.classRef FROM PaymentEntity p WHERE p.userRef = :userEntity")
	List<ClassEntity> findAllClassRefsByUserRef(@Param("userEntity") UserEntity userEntity);
}

