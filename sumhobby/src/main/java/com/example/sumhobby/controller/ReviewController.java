package com.example.sumhobby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

	@Autowired
	private ReviewService service;
	
//	@PostMapping
//	public ResponseEntity<?> createReview(@RequestBody ReviewDTO){
//		try {
//			
//		}
	
//	@PostMapping
//    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
//		ReviewEntity entity = ReviewDTO.toEntity();
//        List<ReviewEntity> createdReview = service.create(entity);
//        List<ReviewDTO> rdtos = createdReview.stream().map(ReviewDTO::new).collect(Collectors.toList());
//        
//        ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder().data(rdtos).build();
//        
//        return ResponseEntity.ok().body(response);
//    }
	
//	@GetMapping
//    public ResponseEntity<List<ReviewDTO>> retrieveReviewList() {
//        List<ReviewDTO> reviewList = service.retrieve();
//        return ResponseEntity.ok(reviewList);
//    }
//
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


