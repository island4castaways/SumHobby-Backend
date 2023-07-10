package com.example.sumhobby.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.ReviewEntity;
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
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClassRepository classRepository;

	public List<ReviewEntity> retrieve(){
		return revRepository.findAll();
	}
	
	public List<ReviewEntity> create(final ReviewEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		//ReviewEntity entity = new ReviewEntity();
		revRepository.save(entity);
		
		return revRepository.findAll();
	}
	
//	public Optional<LectureEntity> lectureRetrieve(int lectureNum){
//		return lectureRepository.findById(lectureNum);
//	}

	public ReviewEntity toEntity(ReviewDTO revDTO) {
		return ReviewDTO.toEntity(revDTO,classRepository,userRepository);
	}
	
}
