package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;
>>>>>>> refs/remotes/origin/PYK

import com.example.sumhobby.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	
	UserEntity findByUserId(String userId);
	Boolean existsByUserId(String userId);
	UserEntity findByUserIdAndPassword(String UserId, String Password);
	
	UserEntity findByEmail(String email);
	UserEntity findByUserIdAndEmail(String userId, String email);

}