package com.example.sumhobby.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.sumhobby.entity.QuestionEntity;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Integer> {

}
