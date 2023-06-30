package com.example.sumhobby.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.sumhobby.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

}
