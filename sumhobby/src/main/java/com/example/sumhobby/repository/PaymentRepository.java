package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumhobby.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

}

