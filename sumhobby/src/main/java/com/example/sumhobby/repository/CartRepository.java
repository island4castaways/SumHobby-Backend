package com.example.sumhobby.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.sumhobby.entity.CartEntity;

public interface CartRepository extends CrudRepository<CartEntity, Integer> {

}
