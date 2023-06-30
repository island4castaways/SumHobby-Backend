package com.example.sumhobby.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
	
	private int classNum, classPrice;
	private String className, classDetail, classCategory, classSetDate, classLastDate, userTk;
	private double classRate;

}
