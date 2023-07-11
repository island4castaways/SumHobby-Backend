package com.example.sumhobby.dto;

import com.example.sumhobby.entity.LectureEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureDTO {
	
	private int lecNum, classNum;
	private String lecTitle, lecDetail, lecUrl;
	
	public LectureDTO(final LectureEntity entity) {
		this.lecNum = entity.getLecNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.lecTitle = entity.getLecTitle();
		this.lecDetail = entity.getLecDetail();
		this.lecUrl = entity.getLecUrl();
	}

}
