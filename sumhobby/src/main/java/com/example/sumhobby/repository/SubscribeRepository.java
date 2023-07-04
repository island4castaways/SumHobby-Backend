package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.sumhobby.entity.SubscribeEntity;

public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {

}
