package com.example.sumhobby;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sumhobby.dto.ClassDTO;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.LectureRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	UserRepository us;
	
	@Autowired
	ClassRepository cl;
	
	@Autowired
	LectureRepository le;
	
	@Test
	public void testInsertUsers() {
		IntStream.rangeClosed(0, 50).forEach(i -> {
			UserEntity entity = UserEntity.builder()
					.userId("testuser" + i)
					.password("testuser")
					.userName("tester" + i)
					.phone("01012345678")
					.email("testuser" + i + "@test.com")
					.build();
			us.save(entity);
		});
	}
	
	@Test
	public void testInsertClasses() {
		IntStream.rangeClosed(0, 49).forEach(i -> {
			ClassEntity entity = ClassEntity.builder()
					.className("testClass" + i)
					.classDetail("testClass" + i + " Detail")
					.classCategory("test")
					.classRate(0.0)
					.classPrice(10000 * i)
					.classSetDate(Timestamp.valueOf(LocalDateTime.now()))
					.classLastDate(Timestamp.valueOf(LocalDateTime.now()))
					.userRef(us.findByUserId("testuser" + i))
					.build();
			cl.save(entity);
		});
	}
	
	@Test
	public void testUtil() {
		List<ClassEntity> entities = cl.findAll();
		List<ClassDTO> dtos = entities.stream().map(ClassDTO::new).collect(Collectors.toList());
		dtos.stream().forEach(dto -> {
			log.info(dto.getClassSetDate());
		});
	}
	
	@Test
	public void testInsertLectures() {
		IntStream.rangeClosed(0, 19).forEach(i -> {
			LectureEntity entity = LectureEntity.builder()
					.classRef(cl.findById(1).get())
					.lecTitle("testLecture" + i)
					.lecDetail("testLecture" + i + " Detail")
					.lecUrl("#")
					.build();
			le.save(entity);
		});
	}
	
	@Test
	public void testInsertAdmin() {
		UserEntity entity = UserEntity.builder()
				.userId("sumhobbyadmin")
				.password("sumhobby230714")
				.userName("administor")
				.role("관리자")
				.build();
		us.save(entity);
	}

}