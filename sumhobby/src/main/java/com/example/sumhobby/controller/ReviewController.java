package com.example.sumhobby.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;
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
		ReviewEntity entity = ReviewEntity.builder()
				.revContent(reviewDTO.getRevContent())
				.revDate(Timestamp.valueOf(LocalDateTime.now()))
				.revRate(reviewDTO.getRevRate())
				.classRef(classService.selectOne(reviewDTO.getClassNum()))
				.userRef(userService.selectOneByUserId(reviewDTO.getUserId()))
				.build();
//		reviewDTO.setClassNum(classService.create(reviewDTO.getClassName()).getClassNum());
		
        ReviewEntity createdReview = service.create(entity);
        List<ReviewEntity> entities = service.selectByClassRef(classService.selectOne(reviewDTO.getClassNum()));
        List<ReviewDTO> createdReviewDTO = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());

        ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(createdReviewDTO).build();
        return ResponseEntity.ok().body(response);

	}


	@PatchMapping("/showreview")
	public ResponseEntity<?> retrieveReviewList(@RequestBody ClassDTO classDTO) {

		ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());

		List<ReviewEntity> entities = service.selectByClassRef(classEntity);

		List<ReviewDTO> dtos = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());

//		        for(int i = 0 ;i < dtos.size();i++) {
//		        	ClassDTO DTO = new ClassDTO(service.retrieve(dtos.get(i).getClassNum()));
//		        	dtos.get(i).setClassName(classDTO.getClassName());
//		        }

		ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(dtos).build();

		return ResponseEntity.ok().body(response);
	}


}


