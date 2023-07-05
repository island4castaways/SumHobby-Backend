package com.example.sumhobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sumhobby.dto.CartDTO;
import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.repository.CartRepository;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
}
