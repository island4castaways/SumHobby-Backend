package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.LectureEntity;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {

}
