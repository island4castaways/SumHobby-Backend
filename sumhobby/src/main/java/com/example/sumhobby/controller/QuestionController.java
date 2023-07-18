package com.example.sumhobby.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.QuestionDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.QuestionEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.LectureService;
import com.example.sumhobby.service.QuestionService;
import com.example.sumhobby.service.UserService;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	LectureService lecService;

	@Autowired
	QuestionService queService;

	@Autowired
	UserService userService;

	@Autowired
	ClassService clasService;

	@PatchMapping
	public ResponseEntity<?> getComment(@RequestBody LectureDTO lecDTO) {
		LectureEntity entity = lecService.selectOne(lecDTO.getLecNum());

		List<QuestionEntity> entities = queService.selectByLectureRef(entity);

		List<QuestionDTO> createdQuestionDTO = entities.stream().map(QuestionDTO::new).collect(Collectors.toList());

		ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder().data(createdQuestionDTO).build();
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/addcomment")
	public ResponseEntity<?> createComment(@RequestBody QuestionDTO quesDTO) {
		QuestionEntity entity = QuestionEntity.builder().quesContent(quesDTO.getQuesContent())
				.quesDate(Timestamp.valueOf(LocalDateTime.now()))
				.userRef(userService.selectOneByUserId(quesDTO.getUserId()))
				.classRef(clasService.selectOne(quesDTO.getClassNum()))
				.lecRef(lecService.selectOne(quesDTO.getLecNum())).build();

		QuestionEntity createdQuestion = queService.create(entity);
		List<QuestionEntity> entities = queService.selectByLectureRef(lecService.selectOne(quesDTO.getLecNum()));
		List<QuestionDTO> createdQuestionDTO = entities.stream().map(QuestionDTO::new).collect(Collectors.toList());

		ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder().data(createdQuestionDTO).build();
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/addreply")
	public ResponseEntity<?> addReply(@RequestBody QuestionDTO quesDTO) {
		System.out.println("" + quesDTO.toString());
		QuestionEntity question = queService.selectOne(quesDTO.getQuesNum());
		question.setQuesAnswer(quesDTO.getQuesAnswer());
		question.setQuesAnsDate(Timestamp.valueOf(LocalDateTime.now()));
		queService.saveQuestion(question);

		List<QuestionEntity> entities = queService.selectByLectureRef(lecService.selectOne(quesDTO.getLecNum()));
		List<QuestionDTO> createdQuestionDTO = entities.stream().map(QuestionDTO::new).collect(Collectors.toList());
		ResponseDTO<QuestionDTO> response = ResponseDTO.<QuestionDTO>builder().data(createdQuestionDTO).build();
		return ResponseEntity.ok().body(response);
	}

}
