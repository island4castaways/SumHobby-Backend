package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumhobby.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
	
}