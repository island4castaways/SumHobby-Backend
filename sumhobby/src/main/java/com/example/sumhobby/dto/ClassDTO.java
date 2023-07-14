package com.example.sumhobby.dto;

import com.example.sumhobby.entity.CartEntity;
import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.repository.ClassRepository;
import com.example.sumhobby.repository.UserRepository;
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
	
	public static ClassEntity toEntity(final ClassDTO dto, ClassRepository classRepository, UserRepository userRepository) {
		return ClassEntity.builder()
				.classNum(dto.getClassNum())
				.classPrice(dto.getClassPrice())
				.className(dto.getClassName())
				.classDetail(dto.getClassDetail())
				.classCategory(dto.getClassCategory())
				.classRate(dto.getClassRate())
				.userRef(userRepository.findByUserId(dto.userId))
				.build();
	}

}
