package com.example.sumhobby.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.UserEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	
	private int cartNum, classNum, classPrice;
	private String userTk, className, userEmail, userName;
	private boolean add;
	
	public CartDTO(final CartEntity entity) {
		this.cartNum = entity.getCartNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.userTk = entity.getUserRef().getUserTk();
	}
	
	public static CartEntity toEntity(final CartDTO dto, ClassRepository classRepository, UserRepository userRepository) {
		return CartEntity.builder()
				.cartNum(dto.getCartNum())
				.classRef(classRepository.findById(dto.classNum).get())
				.userRef(userRepository.findById(dto.userTk).get())
				.build();
	}
}