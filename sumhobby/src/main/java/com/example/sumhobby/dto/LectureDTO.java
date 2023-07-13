package com.example.sumhobby.dto;

import com.example.sumhobby.entity.LectureEntity;
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
public class LectureDTO {
	
	private int lecNum,classNum;
	private String lecTitle, lecDetail, lecUrl;
	
	public LectureDTO(final LectureEntity entity) {
		this.lecNum = entity.getLecNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.lecTitle = entity.getLecTitle();
		this.lecDetail = entity.getLecDetail();
		this.lecUrl = entity.getLecUrl();
	}

	public LectureDTO(final LectureEntity entity) {
		this.lecNum = entity.getLecNum();
		this.lecTitle = entity.getLecTitle();
		this.lecDetail = entity.getLecDetail();
		this.lecUrl = entity.getLecUrl();
		this.classNum = entity.getClassRef().getClassNum();
	}
	
	public static LectureEntity toEntity(final LectureDTO dto,ClassRepository classRepository) {
		return LectureEntity.builder()
				.lecNum(dto.getLecNum())
				.lecTitle(dto.getLecTitle())
				.lecDetail(dto.getLecDetail())
				.lecUrl(dto.getLecUrl())
				.classRef(classRepository.findById(dto.classNum).get())
				.build();		
	}
}
