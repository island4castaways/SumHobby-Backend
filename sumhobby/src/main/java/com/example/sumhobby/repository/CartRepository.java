package com.example.sumhobby.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.UserEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

	CartEntity findByClassRefAndUserRef(ClassEntity classEntity, UserEntity userEntity);
	
	List<CartEntity> findByUserRef(UserEntity userEntity);
}
