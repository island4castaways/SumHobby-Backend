package com.example.sumhobby.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.service.LectureService;

@RestController
@RequestMapping("/lecture")
public class LectureController {

	@Autowired
	LectureService service;
	
	@GetMapping
	public ResponseEntity<?> retrieveLectureList(){
		
		List<LectureEntity> entities = service.retrive();
		
		List<LectureDTO> dtos = entities.stream().map(LectureDTO::new).collect(Collectors.toList());
		
		ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok().body(response);
	}
}
