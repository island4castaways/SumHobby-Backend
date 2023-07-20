package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.entity.UserEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
	
	List<InquiryEntity> findByUserRef(UserEntity userEntity);
	
}