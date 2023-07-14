package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.ClassEntity;
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
	
	public List<LectureEntity> selectAllByClassRef(final ClassEntity classEntity) {
		return lecRepository.findByClassRef(classEntity);
	}
	
	public LectureEntity selectOne(final Integer lecNum) {
		return lecRepository.findById(lecNum).get();
	}
	
	public LectureEntity create(final LectureEntity entity) {
		return lecRepository.save(entity);
	}
	
	public void deleteOne(final LectureEntity entity) {
		lecRepository.delete(entity);
	}
		
	public List<LectureEntity> retrive(){
		return lecRepository.findAll();
	}
	
	//������ ������� Ȯ���ϴ� service�ۼ�
//	public List<SubscribeEntity> getFindByAllSub(){
//		return lecRepository.findBy
//	}
}
