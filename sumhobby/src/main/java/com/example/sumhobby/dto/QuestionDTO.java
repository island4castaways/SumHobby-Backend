package com.example.sumhobby.dto;

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
	private String quesContent, quesAnswer, quesDate, quesAnsDate, userTk;

}
