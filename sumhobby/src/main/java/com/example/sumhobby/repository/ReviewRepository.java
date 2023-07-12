package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

	//userID�� ���� ����Ʈ ã��
//	List<ReviewEntity> findByUserId (UserEntity userId) ;
	
	@Query("SELECT r FROM ReviewEntity r WHERE r.classRef.classNum = :classId")
    List<ReviewEntity> findByClassId(Integer classId);
}
