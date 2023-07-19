package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.InquiryRepository;

@Service
public class InquiryService {

	@Autowired
	private InquiryRepository inqRepository;

	public List<InquiryEntity> selectAll() {
		return inqRepository.findAll();
	}

	public InquiryEntity selectOne(final Integer inqNum) {
		return inqRepository.findById(inqNum).get();
	}

	public InquiryEntity create(final InquiryEntity entity) {
		return inqRepository.save(entity);
	}

	public void delete(final InquiryEntity entity) {
		inqRepository.delete(entity);
	}
	
	public List<InquiryEntity> selectByUserRef(final UserEntity userEntity) {
		return inqRepository.findByUserRef(userEntity);
	}
	
}
