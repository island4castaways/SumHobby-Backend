package com.example.sumhobby.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
	
	private String quesContent;
	
	private String quesAnswer;
	
	private Timestamp quesDate, quesAnsDate;
	
	@ManyToOne
	@JoinColumn(name = "userTk", referencedColumnName = "userTk")
	private UserEntity userRef;
//	
//	@ManyToOne
//	@JoinColumn(name = "userId", referencedColumnName = "userId")
//	private UserEntity userRef;
	
//	@OneToMany(mappedBy = "userRef")
//	@EqualsAndHashCode.Exclude
//	private List<UserEntity> users;
	
	@ManyToOne
	@JoinColumn(name = "classNum", referencedColumnName = "classNum")
	private ClassEntity classRef;

	@ManyToOne
	@JoinColumn(name = "lecNum", referencedColumnName = "lecNum")
	private LectureEntity lecRef;

}
