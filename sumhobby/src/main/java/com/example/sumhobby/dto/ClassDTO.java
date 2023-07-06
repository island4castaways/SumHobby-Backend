package com.example.sumhobby.dto;

import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
	
	private int classNum, classPrice;
	private String className, classDetail, classCategory, classSetDate, classLastDate, userTk;
	private double classRate;
	
	public ClassDTO(final ClassEntity entity) {
		this.classNum = entity.getClassNum();
		this.classPrice = entity.getClassPrice();
		this.className = entity.getClassName();
		this.classDetail = entity.getClassDetail();
		this.classCategory = entity.getClassCategory();
//		this.classSetDate = entity.getClassSetDate().toString();
//		this.classLastDate = entity.getClassLastDate().toString();
		this.userTk = entity.getUserRef().getUserTk();
		this.classRate = entity.getClassRate();
	}

}
