package com.example.sumhobby.dto;

import java.sql.Timestamp;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;
import com.example.sumhobby.entity.UserEntity;
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
public class ReviewDTO {
	
	private int revNum, classNum;
	private double revRate;
	private String revContent, userId, className;
	private String revDate;
	
	public ReviewDTO(final ReviewEntity entity) {
		this.revNum = entity.getRevNum();
//		this.className = entity.getClassRef().getClassName();
		this.revRate = entity.getRevRate();
		this.revContent = entity.getRevContent();
		this.userId = entity.getUserRef().getUserId();
		this.classNum = entity.getClassRef().getClassNum();
		this.revDate = Util.timestampToString(entity.getRevDate());
	}
	
}
