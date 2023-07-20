package com.example.sumhobby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.LectureRepository;
import com.example.sumhobby.repository.ReviewRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository revRepository;
	
	public ReviewEntity selectOne(final Integer revNum){
		return revRepository.findById(revNum).get();
	}
	
	public List<ReviewEntity> selectByClassRef(ClassEntity classEntity) {
		return revRepository.findByClassRef(classEntity);
	}
	
	public ReviewEntity create(ReviewEntity reviewEntity) {
	    if (reviewEntity == null) {
	        log.warn("Entity cannot be null");
	        throw new RuntimeException("Entity cannot be null");
	    }
	    return revRepository.save(reviewEntity);
	}


	public List<ReviewEntity> findByClassId(Integer classId) {
		return revRepository.findByClassId(classId);
	}
	
	public void delete(ReviewEntity entity) {
		revRepository.delete(entity);
	}
	
	public ReviewEntity selectByUserRefAndClassRef(UserEntity userEntity, ClassEntity classEntity) {
		return revRepository.findByUserRefAndClassRef(userEntity, classEntity);
	}
	
}
