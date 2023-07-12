package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.SubscribeEntity;
import com.example.sumhobby.entity.UserEntity;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {

	
	List<SubscribeEntity> findByUserRef(UserEntity userRef);
}
