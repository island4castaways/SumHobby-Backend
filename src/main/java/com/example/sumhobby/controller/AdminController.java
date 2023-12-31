package com.example.sumhobby.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.dto.InquiryDTO;
import com.example.sumhobby.dto.LectureDTO;
import com.example.sumhobby.dto.PaymentDTO;
import com.example.sumhobby.dto.ResponseDTO;
import com.example.sumhobby.dto.ReviewDTO;
import com.example.sumhobby.dto.UserDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.PaymentEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.security.TokenProvider;
import com.example.sumhobby.service.ClassService;
import com.example.sumhobby.service.InquiryService;
import com.example.sumhobby.service.LectureService;
import com.example.sumhobby.service.PaymentService;
import com.example.sumhobby.service.ReviewService;
import com.example.sumhobby.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private LectureService lecService;
	
	@Autowired
	private InquiryService inqService;
	
	@Autowired
	private PaymentService payService;
	
	@Autowired
	private ReviewService revService;
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
		UserEntity entity = userService.getByCredentials(
				userDTO.getUserId(), userDTO.getPassword(), pwEncoder
		);
		if(entity != null) {
			final String token = tokenProvider.create(entity);
			final UserDTO dto = UserDTO.builder()
					.userId(entity.getUserId())
					.userTk(entity.getUserTk())
					.userName(entity.getUserName())
					.token(token)
					.role(entity.getRole())
					.build();
			return ResponseEntity.ok().body(dto);
		} else {
			ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().error("Login Failed.").build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers() {
		List<UserEntity> entities = userService.selectAll();
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping("/users")
	public ResponseEntity<?> approveTeacher(@RequestBody UserDTO userDTO) {
		UserEntity entity = userService.selectOne(userDTO.getUserTk());
		if(entity.getRole().equals("일반") || entity.getRole().equals("강사 신청")) {
			entity.setRole("강사");			
		} else if(entity.getRole().equals("강사")) {
			entity.setRole("일반");
		}
		userService.update(entity);
		return getUsers();
	}
	
	@GetMapping("/classes")
	public ResponseEntity<?> getClasses() {
		List<ClassEntity> entities = classService.retrieve();
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/createClass")
	public ResponseEntity<?> createClass(@RequestBody ClassDTO classDTO) {
		try {
			ClassEntity entity = ClassEntity.builder()
					.className(classDTO.getClassName())
					.classDetail(classDTO.getClassDetail())
					.classCategory(classDTO.getClassCategory())
					.classImg(classDTO.getClassImg())
					.userRef(userService.selectOneByUserId(classDTO.getUserId()))
					.classPrice(classDTO.getClassPrice())
					.classSetDate(Timestamp.valueOf(LocalDateTime.now()))
					.classLastDate(Timestamp.valueOf(LocalDateTime.now()))
					.build();
			classService.create(entity);
			return getClasses();
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/modifyClass")
	public ResponseEntity<?> modifyClass(@RequestBody ClassDTO classDTO) {
		try {
			ClassEntity entity = classService.selectOne(classDTO.getClassNum());
			entity.setClassName(classDTO.getClassName());
			entity.setClassDetail(classDTO.getClassDetail());
			entity.setClassCategory(classDTO.getClassCategory());
			entity.setUserRef(userService.selectOneByUserId(classDTO.getUserId()));
			entity.setClassPrice(classDTO.getClassPrice());
			entity.setClassLastDate(Timestamp.valueOf(LocalDateTime.now()));
			classService.create(entity);
			return getClasses();
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping("/deleteClass")
	public ResponseEntity<?> deleteClass(@RequestBody ClassDTO classDTO) {
		try {
			ClassEntity entity = classService.selectOne(classDTO.getClassNum());
			classService.deleteOne(entity);
			return getClasses();
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<ClassDTO> response = ResponseDTO.<ClassDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PatchMapping("/lectures")
	public ResponseEntity<?> getLectures(@RequestBody ClassDTO classDTO) {
		ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());
		List<LectureEntity> entities = lecService.selectAllByClassRef(classEntity);
		List<LectureDTO> dtos = entities.stream().map(LectureDTO::new).collect(Collectors.toList());
		ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/createLecture")
	public ResponseEntity<?> createLecture(@RequestBody LectureDTO lectureDTO) {
		try {
			ClassEntity classEntity = classService.selectOne(lectureDTO.getClassNum());
			LectureEntity entity = LectureEntity.builder()
					.lecTitle(lectureDTO.getLecTitle())
					.lecDetail(lectureDTO.getLecDetail())
					.lecUrl(lectureDTO.getLecUrl())
					.classRef(classEntity)
					.build();
			lecService.create(entity);
			classEntity.setClassLastDate(Timestamp.valueOf(LocalDateTime.now()));
			classService.create(classEntity);
			return getLectures(new ClassDTO(classEntity));
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/modifyLecture")
	public ResponseEntity<?> modifyLecture(@RequestBody LectureDTO lectureDTO) {
		try {
			LectureEntity entity = lecService.selectOne(lectureDTO.getLecNum());
			entity.setLecTitle(lectureDTO.getLecTitle());
			entity.setLecDetail(lectureDTO.getLecDetail());
			entity.setLecUrl(lectureDTO.getLecUrl());
			lecService.create(entity);
			ClassEntity classEntity = classService.selectOne(lectureDTO.getClassNum());
			classEntity.setClassLastDate(Timestamp.valueOf(LocalDateTime.now()));
			classService.create(classEntity);
			return getLectures(new ClassDTO(classEntity));
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping("deleteLecture")
	public ResponseEntity<?> deleteLecture(@RequestBody LectureDTO lectureDTO) {
		try {
			LectureEntity entity = lecService.selectOne(lectureDTO.getLecNum());
			lecService.deleteOne(entity);
			ClassEntity classEntity = classService.selectOne(lectureDTO.getClassNum());
			classEntity.setClassLastDate(Timestamp.valueOf(LocalDateTime.now()));
			classService.create(classEntity);
			return getLectures(new ClassDTO(classEntity));
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<LectureDTO> response = ResponseDTO.<LectureDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/inquiries")
	public ResponseEntity<?> getInquiries() {
		List<InquiryEntity> entities = inqService.selectAll();
		List<InquiryDTO> dtos = entities.stream().map(InquiryDTO::new).collect(Collectors.toList());
		ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PatchMapping("/inquiry")
	public ResponseEntity<?> getInquiry(@RequestBody InquiryDTO inquiryDTO) {
		InquiryEntity entity = inqService.selectOne(inquiryDTO.getInqNum());
		InquiryDTO dto = new InquiryDTO(entity);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping("/inqAnswer")
	public ResponseEntity<?> saveInqAnswer(@RequestBody InquiryDTO inquiryDTO) {
		try {
			InquiryEntity entity = inqService.selectOne(inquiryDTO.getInqNum());
			entity.setInqAnswer(inquiryDTO.getInqAnswer());
			entity.setInqAnsDate(Timestamp.valueOf(LocalDateTime.now()));
			inqService.create(entity);
			InquiryDTO dto = new InquiryDTO(entity);
			return getInquiry(dto);
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@DeleteMapping("/deleteInqAnswer")
	public ResponseEntity<?> deleteInqAnswer(@RequestBody InquiryDTO inquiryDTO) {
		try {
			InquiryEntity entity = inqService.selectOne(inquiryDTO.getInqNum());
			entity.setInqAnswer(null);
			entity.setInqAnsDate(Timestamp.valueOf(LocalDateTime.now()));
			inqService.create(entity);
			InquiryDTO dto = new InquiryDTO(entity);
			return getInquiry(dto);
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/payments")
	public ResponseEntity<?> getPayments() {
		List<PaymentEntity> entities = payService.selectAll();
		List<PaymentDTO> dtos = entities.stream().map(PaymentDTO::new).collect(Collectors.toList());
		ResponseDTO<PaymentDTO> response = ResponseDTO.<PaymentDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PatchMapping("/reviews")
	public ResponseEntity<?> getReviews(@RequestBody ClassDTO classDTO) {
		ClassEntity classEntity = classService.selectOne(classDTO.getClassNum());
		List<ReviewEntity> entities = revService.selectByClassRef(classEntity);
		List<ReviewDTO> dtos = entities.stream().map(ReviewDTO::new).collect(Collectors.toList());
		ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder()
				.data(dtos).build();
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/deleteReview")
	public ResponseEntity<?> deleteReview(@RequestBody ReviewDTO reviewDTO) {
		try {
			ReviewEntity entity = revService.selectOne(reviewDTO.getRevNum());
			revService.delete(entity);
			log.info("review for class " + reviewDTO.getClassNum() + " asked to be deleted.");
			ClassEntity classEntity = classService.selectOne(reviewDTO.getClassNum());
			log.info("" + classEntity.getClassNum());
			return getReviews(new ClassDTO(classEntity));
		} catch (Exception e) {
			String msg = e.getMessage();
			ResponseDTO<ReviewDTO> response = ResponseDTO.<ReviewDTO>builder()
					.error(msg).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

}