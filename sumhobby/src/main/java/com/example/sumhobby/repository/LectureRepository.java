package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.sumhobby.entity.LectureEntity;

public interface LectureRepository extends JpaRepository<LectureEntity, Integer> {

}
