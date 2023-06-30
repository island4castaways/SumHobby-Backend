package com.example.sumhobby.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "lecture")
public class LectureEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lecNum;
	
	@Column(nullable = false)
	private String lecTitle, lecDetail, lecUrl;
	
	@ManyToOne
	@JoinColumn(name = "class_num", referencedColumnName = "class_num")
	private ClassEntity classRef;
	
	@OneToMany(mappedBy = "lecture", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	private List<QuestionEntity> questions;

}
