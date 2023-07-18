package com.example.sumhobby.dto;

import com.example.sumhobby.entity.QuestionEntity;
import com.example.sumhobby.util.Util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
	
	private int quesNum, classNum, lecNum;
	private String quesContent, quesAnswer, quesDate, 
			quesAnsDate, userId;

	public QuestionDTO(final QuestionEntity entity) {
		this.quesNum = entity.getQuesNum();
		this.classNum = entity.getClassRef().getClassNum();
		this.lecNum = entity.getLecRef().getLecNum();
		this.quesContent = entity.getQuesContent();
		this.quesAnswer = entity.getQuesAnswer();
		this.quesDate = Util.timestampToString(entity.getQuesDate());
		this.quesAnsDate = Util.timestampToString(entity.getQuesAnsDate());
		this.userId = entity.getUserRef().getUserId();
	}
}
