package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.SubscribeEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.LectureRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LectureService {
	
	@Autowired
	private LectureRepository lecRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	public List<LectureEntity> retrive(){
		return lecRepository.findAll();
	}
	
	//구매한 사람인지 확인하는 service작성
//	public List<SubscribeEntity> getFindByAllSub(){
//		return lecRepository.findBy
//	}
}
