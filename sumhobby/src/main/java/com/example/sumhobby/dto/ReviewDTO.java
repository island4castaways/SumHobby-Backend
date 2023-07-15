package com.example.sumhobby.dto;

import java.sql.Timestamp;

import com.example.sumhobby.entity.ClassEntity;
import com.example.sumhobby.entity.ReviewEntity;
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
public class ReviewDTO {
	
	private int revNum, classNum;
	private double revRate;
	private String revContent, userId, className;
	private Timestamp revDate;
	
	public ReviewDTO(final ReviewEntity entity) {
		this.revNum = entity.getRevNum();
//		this.className = entity.getClassRef().getClassName();
		this.revRate = entity.getRevRate();
		this.revContent = entity.getRevContent();
		this.userId = entity.getUserRef().getUserId();
		//안되면 이거 주석
	}
	public static ReviewEntity toEntity(final ReviewDTO dto,ClassRepository classrep,UserRepository userrep) {
		return ReviewEntity.builder()
				.revNum(dto.getRevNum())
				.classRef(classrep.findById(dto.classNum).get())
				.userRef(userrep.findByUserId(dto.userId))
				.revDate(dto.getRevDate())
				.revRate(dto.getRevRate())
				.revContent(dto.getRevContent())
				.build();
	}
}
