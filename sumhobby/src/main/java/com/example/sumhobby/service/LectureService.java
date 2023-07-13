package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.repository.LectureRepository;

@Service
public class LectureService {
	
	@Autowired
	private LectureRepository lecRepository;
	
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
	
}
