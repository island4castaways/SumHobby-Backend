package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

	//userID로 리뷰 리스트 찾기
//	List<ReviewEntity> findByUserId (UserEntity userId) ;
}
