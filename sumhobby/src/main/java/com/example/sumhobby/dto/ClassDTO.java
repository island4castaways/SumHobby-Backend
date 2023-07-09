package com.example.sumhobby.dto;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.util.Util;

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
	private String className, classDetail, classCategory, classSetDate, classLastDate, userId;
	private double classRate;
	
	public ClassDTO(final ClassEntity entity) {
		this.classNum = entity.getClassNum();
		this.classPrice = entity.getClassPrice();
		this.className = entity.getClassName();
		this.classDetail = entity.getClassDetail();
		this.classCategory = entity.getClassCategory();
		this.classSetDate = Util.timestampToString(entity.getClassSetDate());
		this.classLastDate = Util.timestampToString(entity.getClassLastDate());
		this.userId = entity.getUserRef().getUserId();
		this.classRate = entity.getClassRate();
	}

}
