package com.example.sumhobby.dto;

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

}
