package com.example.sumhobby.dto;

import com.example.sumhobby.entity.CartEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
	
	private int cartNum, classNum;
	private String userTk;
	
	public CartDTO(final CartEntity entity) {
		this.cartNum = entity.getCartNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.userTk = entity.getUserRef().getUserTk();
	}
	
}
