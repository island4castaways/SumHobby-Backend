package com.example.sumhobby.dto;

import com.example.sumhobby.entity.SubscribeEntity;
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
public class SubscribeDTO {
	
	private int subsNum, classNum;
	private String userTk;

	//userTK?,userID? ¹»·Î ÇÒÁö 
	public SubscribeDTO(final SubscribeEntity entity) {
		this.subsNum = entity.getSubsNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.userTk = entity.getUserRef().getUserTk();
	}
	
	public static SubscribeEntity toEntity(final SubscribeDTO dto,ClassRepository classRepository, UserRepository userRepository) {
		return SubscribeEntity.builder()
				.subsNum(dto.getSubsNum())
				.classRef(classRepository.findById(dto.classNum).get())
				.userRef(userRepository.findById(dto.userTk).get())
				.build();
	}
}
