package com.example.sumhobby.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.LectureService;

@RestController
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	LectureService lectureService;
	
	@Autowired
	ClassService classService;
	
	@PatchMapping
	public ResponseEntity<?> ListgetByClassNum(@RequestBody ClassDTO classDTO){
		
		ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());
		
		List<LectureEntity> entities = lectureService.selectAllByClassRef(classEntity);
		
		List<LectureDTO> dtos = entities.stream().map(LectureDTO::new).collect(Collectors.toList());
		
		ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
	
//	@GetMapping
//	public ResponseEntity<?> retrieveLectureList(){
//		
//		List<LectureEntity> entities = service.retrive();
//		
//		List<LectureDTO> dtos = entities.stream().map(LectureDTO::new).collect(Collectors.toList());
//		
//		ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder().data(dtos).build();
//		
//		return ResponseEntity.ok().body(response);
//	}
	//classNum 으로 lecture에 저장
	
}
