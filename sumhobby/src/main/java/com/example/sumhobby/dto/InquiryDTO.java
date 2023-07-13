package com.example.sumhobby.dto;

import com.example.sumhobby.entity.InquiryEntity;
import com.example.sumhobby.util.Util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {
	
	private int inqNum;
	private String inqContent, inqAnswer, inqDate, inqAnsDate, userId;
	
	public InquiryDTO(final InquiryEntity entity) {
		this.inqNum = entity.getInqNum();
		this.inqContent = entity.getInqContent();
		this.inqAnswer = entity.getInqAnswer();
		this.inqDate = Util.timestampToString(entity.getInqDate());
		this.inqAnsDate = Util.timestampToString(entity.getInqAnsDate());
		this.userId = entity.getUserRef().getUserId();
	}

}