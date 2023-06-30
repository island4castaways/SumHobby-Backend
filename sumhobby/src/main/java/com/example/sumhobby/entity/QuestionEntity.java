package com.example.sumhobby.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
public class QuestionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer quesNum;
	
	@Column(nullable = false)
	private String quesContent;
	
	private String quesAnswer;
	
	private Timestamp quesDate, quesAnsDate;
	
	@ManyToOne
	@JoinColumn(name = "user_tk", referencedColumnName = "user_tk")
	private UserEntity userRef;
	
	@ManyToOne
	@JoinColumn(name = "class_num", referencedColumnName = "class_num")
	private ClassEntity classRef;

	@ManyToOne
	@JoinColumn(name = "lec_num", referencedColumnName = "lec_num")
	private LectureEntity lecRef;

}
