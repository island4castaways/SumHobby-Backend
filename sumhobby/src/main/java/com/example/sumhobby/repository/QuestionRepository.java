package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;
import com.example.sumhobby.entity.QuestionEntity;
import com.example.sumhobby.entity.UserEntity;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {

	List<QuestionEntity> findByLecRef(LectureEntity lectureEntity);
	
	List<QuestionEntity> findByUserRef(UserEntity userEntity);

	 @Query("SELECT q FROM QuestionEntity q WHERE q.lecRef.lecNum = :lecNum AND q.userRef.userTk = :userTk")
	  List<QuestionEntity> matchUserTk(@Param("lecNum") Integer lecNum, @Param("userTk") String userTk);
	 
}
