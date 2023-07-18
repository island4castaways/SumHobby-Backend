package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.QuestionEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionService {

	@Autowired
	private QuestionRepository quesRepository;

	public QuestionEntity selectOne(final Integer quesNum) {
		return quesRepository.findById(quesNum).get();
	}

	public QuestionEntity create(QuestionEntity quesEntity) {
		if (quesEntity == null) {
			log.warn("Entity cannot be null");
			throw new RuntimeException("Entity cannot be null");
		}
		return quesRepository.save(quesEntity);
	}

	public List<QuestionEntity> selectByLectureRef(LectureEntity lecEntity) {
		return quesRepository.findByLecRef(lecEntity);
	}

	public List<QuestionEntity> matchUserTk(Integer lecNum, String userTk) {
		return quesRepository.matchUserTk(lecNum, userTk);
	}

	public void saveQuestion(QuestionEntity question) {
		quesRepository.save(question);
	}

}
