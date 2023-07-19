package com.example.sumhobby.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.ReviewService;
import com.example.sumhobby.service.UserService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService service;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/addreview")
	public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
		ClassEntity classEntity = classService.selectOne(reviewDTO.getClassNum());
		
		//review create
		ReviewEntity entity = ReviewEntity.builder()
				.revContent(reviewDTO.getRevContent())
				.revDate(Timestamp.valueOf(LocalDateTime.now()))
				.revRate(reviewDTO.getRevRate())
				.classRef(classEntity)
				.userRef(userService.selectOneByUserId(reviewDTO.getUserId()))
				.build();
        ReviewEntity createdReview = service.create(entity);
        
        //classRate update
        List<ReviewEntity> allReviews = service.selectByClassRef(classEntity);
        double rateSum = 0;
        for(ReviewEntity review: allReviews) {
        	rateSum =+ review.getRevRate();
        }
        double newRate = (int)(rateSum / allReviews.size() * 100) / 100.0;
        classEntity.setClassRate(newRate);
        classService.create(classEntity);
        
        //review response retrieve
        List<ReviewEntity> entities = service.selectByClassRef(classEntity);
        List<ReviewDTO> createdReviewDTO = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());

        ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(createdReviewDTO).build();
        return ResponseEntity.ok().body(response);
	}


	@PatchMapping("/showreview")
	public ResponseEntity<?> retrieveReviewList(@RequestBody ClassDTO classDTO) {

		ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());

		List<ReviewEntity> entities = service.selectByClassRef(classEntity);

		List<ReviewDTO> dtos = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());

		ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);
	}

}


