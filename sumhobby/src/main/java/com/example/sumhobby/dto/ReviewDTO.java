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
	private String revContent, userTk;
	private Timestamp revDate;
	
	public ReviewDTO(final ReviewEntity entity) {
		this.revNum = entity.getRevNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.revRate = entity.getRevRate();
		this.revContent = entity.getRevContent();
		this.userTk = entity.getUserRef().getUserTk();
	}
	public static ReviewEntity toEntity(final ReviewDTO dto,ClassRepository classRepository,UserRepository userRepository) {
		return ReviewEntity.builder()
				.revNum(dto.getRevNum())
				.classRef(classRepository.findById(dto.classNum).get())
				.userRef(userRepository.findById(dto.userTk).get())
				.revDate(dto.getRevDate())
				.revRate(dto.getRevRate())
				.revContent(dto.getRevContent())
				.build();
	}
}
