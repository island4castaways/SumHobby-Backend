package com.example.sumhobby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sumhobby.entity.SubscribeEntity;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {

}
