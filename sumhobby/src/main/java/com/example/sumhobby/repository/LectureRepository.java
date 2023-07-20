package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.LectureEntity;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {
	
	List<LectureEntity> findByClassRef(ClassEntity classEntity);

}
