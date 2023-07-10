package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

	//userID�� ���� ����Ʈ ã��
//	List<ReviewEntity> findByUserId (UserEntity userId) ;
}
