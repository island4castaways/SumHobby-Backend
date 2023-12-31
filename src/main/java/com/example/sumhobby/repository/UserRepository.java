package com.example.sumhobby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	UserEntity findByUserId(String userId);
	Boolean existsByUserId(String userId);
	UserEntity findByUserIdAndPassword(String UserId, String Password);
	UserEntity findByEmail(String email);
	UserEntity findByUserIdAndEmail(String userId, String email);
	Optional<UserEntity> findByUserTk(String userTk);
	UserEntity findByPassword(String password);

	
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);


}
