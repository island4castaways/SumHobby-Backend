package com.example.sumhobby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;
import com.example.sumhobby.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService service;
	
	@Autowired
	private ClassRepository classrep;
	
	@Autowired
	private UserRepository userrep;
	
	@PostMapping("/addreview")
	public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
		reviewDTO.setClassNum(classrep.findByClassName(reviewDTO.getClassName()).getClassNum());
		
		ReviewEntity entity = ReviewDTO.toEntity(reviewDTO, classrep, userrep);
		
        List<ReviewEntity> createdReview = service.create(entity);
        
        List<ReviewDTO> createdReviewDTO = createdReview.stream().map(ReviewDTO::new).collect(Collectors.toList());
        
        ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(createdReviewDTO).build();
        return ResponseEntity.ok().body(response);
	    
	}

	
	@GetMapping
    public ResponseEntity<?> retrieveReviewList() {
		
        List<ReviewEntity> entities = service.retrieve();
        
        List<ReviewDTO> dtos = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());
        
        for(int i = 0 ;i < dtos.size();i++) {
        	ClassDTO classDTO = new ClassDTO(service.classRetrieve(dtos.get(i).getClassNum()));
        	dtos.get(i).setClassName(classDTO.getClassName());
        }
        
        ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(dtos).build();
        
        return ResponseEntity.ok().body(response);
    }
	
	  @GetMapping("/class/{classId}")
	    public ResponseEntity<List<ReviewDTO>> getReviewsByClassId(@PathVariable("classId") Integer classId) {
	        List<ReviewEntity> reviews = service.findByClassId(classId);
	        List<ReviewDTO> reviewDTOs = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
	        return ResponseEntity.ok(reviewDTOs);
	    }

//	@GetMapping
//	public ResponseEntity<?> retrieveRevieList(){
//		List<ReviewEntity> entities = service.retrieve();
//		
//		List<ReviewDTO> rdtos = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());
//		
//		List<LectureDTO> lDtos = new ArrayList<>();
//		         
//		for(int i =0; i<rdtos.size();i++) {
//			int lecNum = rdtos.get(i).getRevNum();
//			Optional<LectureEntity> lecture = service.lectureRetrieve(lecNum);
//			if(lecture.isPresent()) {
//				LectureDTO lectureDTO = new LectureDTO(lecture.get());
//				lDtos.add(lectureDTO);
//			}
//		}
////		ResponseEntity<LectureDTO> response = ReviewDTO.<LectureDTO>
//		return ResponseEntity.ok().build();
//	}
	}


